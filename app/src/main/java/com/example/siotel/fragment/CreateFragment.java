 package com.example.siotel.fragment;

    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Paint;
    import android.graphics.pdf.PdfDocument;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.core.content.FileProvider;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.siotel.R;
    import com.example.siotel.SharedPrefManager;
    import com.example.siotel.adapters.CreateAdapter;
    import com.example.siotel.adapters.MeterSNOAdapter;
    import com.example.siotel.adapters.ReportAdapter;
    import com.example.siotel.api.PostRequestApi;
    import com.example.siotel.models.HouseholdsModel;
    import com.example.siotel.models.InvoiceCreationRequest;
    import com.example.siotel.models.InvoiceCreationResponse;
    import com.example.siotel.models.InvoiceRequest;
    import com.example.siotel.models.InvoiceResponse;
    import com.example.siotel.models.ReportRequest;
    import com.example.siotel.models.ReportResponse;
    import com.example.siotel.models.SaveEmail;
    import com.example.siotel.models.Token;
    import com.example.siotel.models.UsageEntry;
    import com.google.gson.Gson;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Locale;
    import java.util.Set;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class CreateFragment extends Fragment {

        private RecyclerView recyclerView, recyclerViewInvoice;
        private ReportAdapter adapter; // Changed to ReportAdapter
        private Spinner spinnerSite, spinnerYear;
        private AutoCompleteTextView autoCompleteMeterSNO;
        private EditText startDate, endDate;
        private Button btnGetReport, btnExportExcel, btnCreateInvoice, btnCreateInvoice1;
        private SharedPrefManager sharedPrefManager;
        private List<String> meterSNOList = new ArrayList<>();

        private List<String> siteNameList = new ArrayList<>();

        private ReportResponse currentReport;


        private CreateAdapter createAdapter;

        private InvoiceResponse latestInvoiceResponse;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_create, container, false);

            sharedPrefManager = new SharedPrefManager(requireContext());

            recyclerView = view.findViewById(R.id.recyclerViewRechargeHistory);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new ReportAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);

            recyclerViewInvoice = view.findViewById(R.id.recyclerViewInvoice);
            recyclerViewInvoice.setLayoutManager(new LinearLayoutManager(getContext()));

            // Initialize UI components
            spinnerSite = view.findViewById(R.id.spinner_select_site);
            spinnerYear = view.findViewById(R.id.spinnerTransactionType);
            autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO1);
            startDate = view.findViewById(R.id.edittext_start_date);
            endDate = view.findViewById(R.id.edittext_end_date);
            btnGetReport = view.findViewById(R.id.button_get_report);
            btnExportExcel = view.findViewById(R.id.btnExportExcel);
            btnCreateInvoice = view.findViewById(R.id.btnCreateInvoice);
            btnCreateInvoice1 = view.findViewById(R.id.btnCreateInvoice1);

            // Set up Spinners
            setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
    //        setupSpinner(spinnerSite, new String[]{"Grand Anukampa"});

            // Fetch data from API
            fetchMeterSNOs();
            fetchSiteName(); // Add this call

            // Date picker dialogs
            setupDatePicker(startDate);
            setupDatePicker(endDate);

            // Button click listeners
            btnGetReport.setOnClickListener(v -> generateReport());
            btnExportExcel.setOnClickListener(v -> exportToPDF());
            btnCreateInvoice.setOnClickListener(v -> generateInvoice());
            btnCreateInvoice1.setOnClickListener(v -> createInvoice());


            return view;
        }

        private void setupSpinner(Spinner spinner, String[] items) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
            spinner.setAdapter(adapter);
        }

        private void setupDatePicker(EditText editText) {
            editText.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    editText.setText(selectedDate);
                }, year, month, day);
                datePickerDialog.show();
            });
        }

        private void generateReport() {
            String meterSNO = autoCompleteMeterSNO.getText().toString();
            if (meterSNO.isEmpty()) {
                Toast.makeText(getContext(), "Please select a meter SNO", Toast.LENGTH_SHORT).show();
                return;
            }

            String startDateStr = startDate.getText().toString();
            String endDateStr = endDate.getText().toString();
            String financeYear = spinnerYear.getSelectedItem().toString();

            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(getContext(), "Please select start and end dates", Toast.LENGTH_SHORT).show();
                return;
            }

            String formattedStartDate = convertDateFormat(startDateStr);
            String formattedEndDate = convertDateFormat(endDateStr);

            if (formattedStartDate == null || formattedEndDate == null) {
                Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            ReportRequest request = new ReportRequest(formattedStartDate, formattedEndDate, financeYear);

            String token = sharedPrefManager.getAccessToken();
            if (token == null) {
                Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }
            String tokenStr = "Bearer " + token;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Call<ReportResponse> call = requestApi.getUsageReport(meterSNO, tokenStr, request);

            call.enqueue(new Callback<ReportResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReportResponse> call, @NonNull Response<ReportResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ReportResponse report = response.body();
                        currentReport = response.body();
                        List<ReportResponse> reportList = new ArrayList<>();
                        reportList.add(report);
                        adapter.updateData(reportList);
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch report", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReportResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error fetching report. Check internet connection.", Toast.LENGTH_SHORT).show();
                    Log.e("ReportFragment", "Error: " + t.getMessage());
                }
            });
        }

        private String convertDateFormat(String dateStr) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = inputFormat.parse(dateStr);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void exportToPDF() {
            if (currentReport == null) {
                Toast.makeText(getContext(), "Please generate the report first", Toast.LENGTH_SHORT).show();
                return;
            }

            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4 size
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            Paint titlePaint = new Paint();
            titlePaint.setColor(Color.BLACK);
            titlePaint.setTextSize(18);
            titlePaint.setFakeBoldText(true);

            Paint sectionHeaderPaint = new Paint();
            sectionHeaderPaint.setColor(Color.DKGRAY);
            sectionHeaderPaint.setTextSize(14);
            sectionHeaderPaint.setFakeBoldText(true);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(12);

            Paint linePaint = new Paint();
            linePaint.setColor(Color.LTGRAY);
            linePaint.setStrokeWidth(1);

            int y = 40;
            int leftMargin = 30;

            // Title
            canvas.drawText("📄 Usage Report", leftMargin, y, titlePaint);
            y += 20;
            canvas.drawLine(leftMargin, y, 565, y, linePaint);
            y += 20;

            // Meter Info Section
            canvas.drawText("Meter Information", leftMargin, y, sectionHeaderPaint);
            y += 20;
            canvas.drawText("Meter SNO: " + currentReport.getSno(), leftMargin, y, textPaint);
            y += 18;
            canvas.drawText("Start Date: " + currentReport.getActual_start_date(), leftMargin, y, textPaint);
            y += 18;
            canvas.drawText("End Date: " + currentReport.getActual_end_date(), leftMargin, y, textPaint);
            y += 20;
            canvas.drawLine(leftMargin, y, 565, y, linePaint);
            y += 20;

            // Summary Section
            canvas.drawText("Summary", leftMargin, y, sectionHeaderPaint);
            y += 20;
            canvas.drawText("EB KWH (Open): " + currentReport.getEb_kwh_open(), leftMargin, y, textPaint);
            canvas.drawText("EB KWH (Close): " + currentReport.getEb_kwh_close(), 300, y, textPaint);
            y += 18;
            canvas.drawText("DG KWH (Open): " + currentReport.getDg_kwh_open(), leftMargin, y, textPaint);
            canvas.drawText("DG KWH (Close): " + currentReport.getDg_kwh_close(), 300, y, textPaint);
            y += 18;
            canvas.drawText("EB Consumed: " + currentReport.getCon_eb_kwh(), leftMargin, y, textPaint);
            canvas.drawText("DG Consumed: " + currentReport.getCon_dg_kwh(), 300, y, textPaint);
            y += 18;
            canvas.drawText("EB TF: " + currentReport.getEb_tf(), leftMargin, y, textPaint);
            canvas.drawText("DG TF: " + currentReport.getDg_tf(), 300, y, textPaint);
            y += 18;
            canvas.drawText("DC TF: " + currentReport.getDc_tf(), leftMargin, y, textPaint);
            canvas.drawText("Activated Days: " + currentReport.getActivate_days(), 300, y, textPaint);
            y += 18;
            canvas.drawText("Amount (Open): " + currentReport.getAmount_open(), leftMargin, y, textPaint);
            canvas.drawText("Amount (Close): " + currentReport.getAmount_close(), 300, y, textPaint);
            y += 18;
            canvas.drawText("Net Amount: " + currentReport.getNet_amount(), leftMargin, y, textPaint);
            canvas.drawText("Recharge: " + currentReport.getTotal_Recharge(), 300, y, textPaint);
            y += 18;
            canvas.drawText("CAM Amount: " + currentReport.getTotal_cam_amount(), leftMargin, y, textPaint);
            canvas.drawText("Except CAM: " + currentReport.getTotal_except_cam_amount(), 300, y, textPaint);
            y += 18;
            canvas.drawText("Total Amount: " + currentReport.getTotal_amount(), leftMargin, y, textPaint);
            y += 20;
            canvas.drawLine(leftMargin, y, 565, y, linePaint);
            y += 20;

            // Usage Entries Section
            List<UsageEntry> entries = currentReport.getUsageEntries();
            if (entries != null && !entries.isEmpty()) {
                canvas.drawText("Usage Details", leftMargin, y, sectionHeaderPaint);
                y += 20;

                // Table Header
                textPaint.setFakeBoldText(true);
                canvas.drawText("Date", leftMargin, y, textPaint);
                canvas.drawText("Usage", 200, y, textPaint);
                textPaint.setFakeBoldText(false);
                y += 16;
                canvas.drawLine(leftMargin, y, 565, y, linePaint);
                y += 10;

                // Table Data
                for (UsageEntry entry : entries) {
                    if (y > 800) { // Paginate
                        document.finishPage(page);
                        page = document.startPage(pageInfo);
                        canvas = page.getCanvas();
                        y = 40;
                    }

                    canvas.drawText(entry.getDate(), leftMargin, y, textPaint);
                    canvas.drawText(entry.getUsage(), 200, y, textPaint);
                    y += 18;
                }
            }

            document.finishPage(page);

            File cacheDir = requireContext().getCacheDir();
            File pdfFile = new File(cacheDir, "report.pdf");

            try {
                document.writeTo(new FileOutputStream(pdfFile));
                document.close();
                sharePdfFile(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error creating PDF", Toast.LENGTH_SHORT).show();
            }
        }


        private void sharePdfFile(File pdfFile) {
            String authority = requireContext().getPackageName() + ".fileprovider";
            Uri uri = FileProvider.getUriForFile(requireContext(), authority, pdfFile);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Share PDF"));
        }

        private void fetchMeterSNOs() {
            Token token = sharedPrefManager.getUser();
            String tokenStr = "Bearer " + token.getToken();
            SaveEmail saveEmail = new SaveEmail(token.getEmail());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Call<List<HouseholdsModel>> call = requestApi.getAllMeter(tokenStr, saveEmail);

            call.enqueue(new Callback<List<HouseholdsModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        meterSNOList.clear(); // Clear existing data
                        for (HouseholdsModel model : response.body()) {
                            String meterSNO = model.getMetersno();
                            if (meterSNO != null) { // Filter out null values
                                meterSNOList.add(meterSNO);
                            }
                        }
                        updateMeterSNOAdapter();
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch meter SNOs", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<HouseholdsModel>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    Log.e("ReportFragment", "Error: " + t.getMessage());
                }
            });
        }

        private void updateMeterSNOAdapter() {
            Log.d("RechargeReport", "updateMeterSNOAdapter: Updating adapter with " + meterSNOList.size() + " items");
            MeterSNOAdapter meterAdapter = new MeterSNOAdapter(requireContext(), meterSNOList);
            Log.d("RechargeReport", "updateMeterSNOAdapter: MeterSNOAdapter created");

            autoCompleteMeterSNO.setAdapter(meterAdapter);
            autoCompleteMeterSNO.setThreshold(1);
            Log.d("RechargeReport", "updateMeterSNOAdapter: Adapter and threshold set");

            autoCompleteMeterSNO.setOnItemClickListener((parent, view, position, id) -> {
                String selectedMeter = (String) parent.getItemAtPosition(position);
                autoCompleteMeterSNO.setText(selectedMeter);
                Log.d("RechargeReport", "updateMeterSNOAdapter: Selected meter SNO = " + selectedMeter);
            });
            Log.d("RechargeReport", "updateMeterSNOAdapter: Item click listener attached");
        }

        private void fetchSiteName() {
            Token token = sharedPrefManager.getUser();
            String tokenStr = "Bearer " + token.getToken();
            SaveEmail saveEmail = new SaveEmail(token.getEmail());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Call<List<HouseholdsModel>> call = requestApi.getAllSites(tokenStr, saveEmail);

            call.enqueue(new Callback<List<HouseholdsModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        Log.d("ReportFragment", "Response JSON: " + json);
                        List<HouseholdsModel> households = response.body();
                        Log.d("ReportFragment", "Number of households: " + households.size());
                        Set<String> siteSet = new HashSet<>();
                        for (HouseholdsModel model : households) {
                            String siteName = model.getSiteName();
                            if (siteName != null) {
                                siteSet.add(siteName);
                                Log.d("ReportFragment", "Added site name: " + siteName);
                            } else {
                                Log.d("ReportFragment", "Found null site name");
                            }
                        }
                        siteNameList.clear();
                        siteNameList.addAll(siteSet);
                        Log.d("ReportFragment", "siteNameList size: " + siteNameList.size());

                        // Set up the spinner adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_item, siteNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSite.setAdapter(adapter);
                    } else {
                        Log.e("ReportFragment", "Failed to fetch site names: " + response.code());
                        Toast.makeText(getContext(), "Failed to fetch site names", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<HouseholdsModel>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    Log.e("ReportFragment", "Error: " + t.getMessage());
                }
            });

        }

        private void generateInvoice() {
            String meterSNO = autoCompleteMeterSNO.getText().toString().trim();
            if (meterSNO.isEmpty()) {
                Toast.makeText(getContext(), "Please select a meter SNO", Toast.LENGTH_SHORT).show();
                return;
            }

            // Extract only the second part if it contains "||"
            if (meterSNO.contains("||")) {
                String[] parts = meterSNO.split("\\|\\|");
                if (parts.length > 1) {
                    meterSNO = parts[1].trim(); // Take the second part and remove spaces
                }
            }

            // Get user-entered dates from EditText
            String startDateStr = startDate.getText().toString();
            String endDateStr = endDate.getText().toString();
            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(getContext(), "Please enter start and end dates", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert dates to the required format
            String formattedStartDate = convertDateFormat(startDateStr);
            String formattedEndDate = convertDateFormat(endDateStr);
            if (formattedStartDate == null || formattedEndDate == null) {
                Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ensure report is generated before proceeding
            if (currentReport == null) {
                Toast.makeText(getContext(), "Please generate the report first", Toast.LENGTH_SHORT).show();
                return;
            }

            InvoiceRequest invoiceRequest = new InvoiceRequest();
            invoiceRequest.setMeterSno(meterSNO);
            // Use user-entered dates instead of actual dates from currentReport
            invoiceRequest.setStartdate(formattedStartDate);
            invoiceRequest.setEnddate(formattedEndDate);
            invoiceRequest.setEb_kwh_open(currentReport.getEb_kwh_open());
            invoiceRequest.setEb_kwh_close(currentReport.getEb_kwh_close());
            invoiceRequest.setCon_eb_kwh(currentReport.getCon_eb_kwh());
            invoiceRequest.setDg_kwh_open(currentReport.getDg_kwh_open());
            invoiceRequest.setDg_kwh_close(currentReport.getDg_kwh_close());
            invoiceRequest.setCon_dg_kwh(currentReport.getCon_dg_kwh());
            invoiceRequest.setEb_tf(currentReport.getEb_tf()); // Example: Fetch from currentReport
            invoiceRequest.setDg_tf(currentReport.getDg_tf());
            invoiceRequest.setDc_tf(currentReport.getDc_tf());
            invoiceRequest.setAmount_open(currentReport.getAmount_open());
            invoiceRequest.setAmount_close(currentReport.getAmount_close());
            invoiceRequest.setActivate_days(currentReport.getActivate_days());
            invoiceRequest.setNet_amount(currentReport.getNet_amount());
            invoiceRequest.setTotal_Recharge(currentReport.getTotal_Recharge());
            invoiceRequest.setActual_start_date(currentReport.getActual_start_date());
            invoiceRequest.setActual_end_date(currentReport.getActual_end_date());
            invoiceRequest.setTotal_cam_amount(currentReport.getTotal_cam_amount());
            invoiceRequest.setTotal_except_cam_amount(currentReport.getTotal_except_cam_amount());
            invoiceRequest.setTotal_amount(currentReport.getTotal_amount());

            // Ensure token is available
            String token = sharedPrefManager.getAccessToken();
            if (token == null) {
                Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }
            String tokenStr = "Bearer " + token;

            // Create Retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Call<InvoiceResponse> call = requestApi.createInvoice(tokenStr, invoiceRequest);

            call.enqueue(new Callback<InvoiceResponse>() {
                @Override
                public void onResponse(@NonNull Call<InvoiceResponse> call, @NonNull Response<InvoiceResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Get the InvoiceResponse
                        InvoiceResponse invoiceResponse = response.body();
                        latestInvoiceResponse = response.body();
                        Log.d("CreateFragment", "Response: " + new Gson().toJson(invoiceResponse));

                        // Prepare a list for the adapter (single item for now)
                        List<InvoiceResponse> invoiceList = new ArrayList<>();
                        invoiceList.add(invoiceResponse);

                        // Assuming CreateAdapter is your adapter for InvoiceResponse
                        CreateAdapter adapter = new CreateAdapter(invoiceList);
                        recyclerViewInvoice.setAdapter(adapter);

                    } else {
                        Toast.makeText(getContext(), "Failed to create invoice", Toast.LENGTH_SHORT).show();
                        Log.e("CreateFragment", "Error Response: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<InvoiceResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error creating invoice. Check internet connection.", Toast.LENGTH_SHORT).show();
                    Log.e("CreateFragment", "Error: " + t.getMessage());
                }
            });
        }

        private void createInvoice() {
            // Get meter serial number from AutoCompleteTextView
            String originalInput = autoCompleteMeterSNO.getText().toString().trim();
            if (originalInput.isEmpty()) {
                Toast.makeText(getContext(), "Please select a meter SNO", Toast.LENGTH_SHORT).show();
                return;
            }

            // Extract values
            String meterSNO = originalInput;
            String hname = "";
            String uname = "";

            // Use hname and uname from latestInvoiceResponse if available
            if (latestInvoiceResponse != null && latestInvoiceResponse.getHname() != null) {
                hname = latestInvoiceResponse.getHname();
                uname = latestInvoiceResponse.getHname(); // Using hname for uname as well
            } else {
                // Fallback if response is not yet set
                if (originalInput.contains("||")) {
                    String[] parts = originalInput.split("\\|\\|");
                    if (parts.length > 1) {
                        hname = parts[0].trim();  // before ||
                        uname = parts[0].trim();
                        meterSNO = parts[1].trim(); // after ||
                    }
                } else {
                    hname = originalInput;
                    uname = originalInput;
                }
            }

            // Get start and end dates
            String startDateStr = startDate.getText().toString().trim();
            String endDateStr = endDate.getText().toString().trim();
            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                Toast.makeText(getContext(), "Please enter start and end dates", Toast.LENGTH_SHORT).show();
                return;
            }

            String formattedStartDate = convertDateFormat(startDateStr);
            String formattedEndDate = convertDateFormat(endDateStr);
            if (formattedStartDate == null || formattedEndDate == null) {
                Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentReport == null) {
                Toast.makeText(getContext(), "Please generate the report first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get email from token
            Token token = sharedPrefManager.getUser();
            String email = token.getEmail();
            if (email == null || email.isEmpty()) {
                Toast.makeText(getContext(), "Email not found. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }

            String sitename = spinnerSite.getSelectedItem().toString();
            if (sitename == null || sitename.isEmpty()) {
                Toast.makeText(getContext(), "Please select a site", Toast.LENGTH_SHORT).show();
                return;
            }

            // Static values
            String cemail = "demo1007@gmail.com";
            String caddress = "Grand Anukampa Sodala,Jaipur";
            double mc = 20.0;

            // Current and due date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String currentDate = sdf.format(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            String invoiceDue = sdf.format(calendar.getTime());

            // Build invoice request
            InvoiceCreationRequest request = new InvoiceCreationRequest();
            request.setEmail(email);
            request.setMeterSno(meterSNO);
            request.setStartdate(formattedStartDate);
            request.setEnddate(formattedEndDate);
            request.setHname(hname);
            request.setUname(uname);
            request.setCemail(cemail);
            request.setCaddress(caddress);
            request.setOpen_kwheb(currentReport.getEb_kwh_open());
            request.setClose_kwheb(currentReport.getEb_kwh_close());
            request.setCon_kwheb(currentReport.getCon_eb_kwh());
            request.setOpen_kwhdg(currentReport.getDg_kwh_open());
            request.setClose_kwhdg(currentReport.getDg_kwh_close());
            request.setCon_kwhdg(currentReport.getCon_dg_kwh());
            request.setEbt(currentReport.getEb_tf());
            request.setDgt(currentReport.getDg_tf());
            request.setMc(mc);
            request.setOpen_amount(currentReport.getAmount_open());
            request.setClose_amount(currentReport.getAmount_close());
            request.setActday(currentReport.getActivate_days());
            request.setNetamount(currentReport.getNet_amount());
            request.setSitename(sitename);
            request.setCurrent_date(currentDate);
            request.setInvoice_due(invoiceDue);

            // Retrofit API call
            String accessToken = sharedPrefManager.getAccessToken();
            if (accessToken == null) {
                Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }

            String tokenStr = "Bearer " + accessToken;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://meters.siotel.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
            Call<String> call = requestApi.createInvoiceApi(tokenStr, request);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMessage = "Unknown error";
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getContext(), "Failed to create invoice: " + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("CreateFragment", "Error Response: " + errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "Error creating invoice. Check internet connection.", Toast.LENGTH_SHORT).show();
                    Log.e("CreateFragment", "Error: " + t.getMessage());
                }
            });
        }

    }


//package com.example.siotel.fragment;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.pdf.PdfDocument;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.FileProvider;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.siotel.R;
//import com.example.siotel.SharedPrefManager;
//import com.example.siotel.adapters.CreateAdapter;
//import com.example.siotel.adapters.MeterSNOAdapter;
//import com.example.siotel.adapters.ReportAdapter;
//import com.example.siotel.api.PostRequestApi;
//import com.example.siotel.models.CreateModel;
//import com.example.siotel.models.HouseholdsModel;
//import com.example.siotel.models.InvoiceRequest;
//import com.example.siotel.models.ReportRequest;
//import com.example.siotel.models.ReportResponse;
//import com.example.siotel.models.SaveEmail;
//import com.example.siotel.models.Token;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//import java.util.Set;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class CreateFragment extends Fragment {
//
//    private RecyclerView recyclerView, recyclerViewInvoice;
//    private ReportAdapter adapter;
//    private Spinner spinnerSite, spinnerYear;
//    private AutoCompleteTextView autoCompleteMeterSNO;
//    private EditText startDate, endDate;
//    private Button btnGetReport, btnExportExcel, btnCreateInvoice;
//    private SharedPrefManager sharedPrefManager;
//    private List<String> meterSNOList = new ArrayList<>();
//    private List<String> siteNameList = new ArrayList<>();
//    private ReportResponse currentReport;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_create, container, false);
//
//        // Initialize RecyclerView for invoice
//        recyclerViewInvoice = view.findViewById(R.id.recyclerViewInvoice);
//        recyclerViewInvoice.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        sharedPrefManager = new SharedPrefManager(requireContext());
//
//        recyclerView = view.findViewById(R.id.recyclerViewRechargeHistory);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new ReportAdapter(new ArrayList<>());
//        recyclerView.setAdapter(adapter);
//
//        spinnerSite = view.findViewById(R.id.spinner_select_site);
//        spinnerYear = view.findViewById(R.id.spinnerTransactionType);
//        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO1);
//        startDate = view.findViewById(R.id.edittext_start_date);
//        endDate = view.findViewById(R.id.edittext_end_date);
//        btnGetReport = view.findViewById(R.id.button_get_report);
//        btnExportExcel = view.findViewById(R.id.btnExportExcel);
//        btnCreateInvoice = view.findViewById(R.id.btnCreateInvoice);
//
//        setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
//        fetchMeterSNOs();
//        fetchSiteName();
//        setupDatePicker(startDate);
//        setupDatePicker(endDate);
//
//        btnGetReport.setOnClickListener(v -> generateReport());
//        btnExportExcel.setOnClickListener(v -> exportToPDF());
//        btnCreateInvoice.setOnClickListener(v -> generateInvoice());
//
//        return view;
//    }
//
//    private void setupSpinner(Spinner spinner, String[] items) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
//        spinner.setAdapter(adapter);
//    }
//
//    private void setupDatePicker(EditText editText) {
//        editText.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
//                String selectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
//                editText.setText(selectedDate);
//            }, year, month, day);
//            datePickerDialog.show();
//        });
//    }
//
//    private void generateReport() {
//        String meterSNO = autoCompleteMeterSNO.getText().toString();
//        if (meterSNO.isEmpty()) {
//            Toast.makeText(getContext(), "Please select a meter SNO", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String startDateStr = startDate.getText().toString();
//        String endDateStr = endDate.getText().toString();
//        String financeYear = spinnerYear.getSelectedItem().toString();
//
//        if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
//            Toast.makeText(getContext(), "Please select start and end dates", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String formattedStartDate = convertDateFormat(startDateStr);
//        String formattedEndDate = convertDateFormat(endDateStr);
//
//        if (formattedStartDate == null || formattedEndDate == null) {
//            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        ReportRequest request = new ReportRequest(formattedStartDate, formattedEndDate, financeYear);
//
//        String token = sharedPrefManager.getAccessToken();
//        if (token == null) {
//            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String tokenStr = "Bearer " + token;
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<ReportResponse> call = requestApi.getUsageReport(meterSNO, tokenStr, request);
//
//        call.enqueue(new Callback<ReportResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<ReportResponse> call, @NonNull Response<ReportResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    currentReport = response.body();
//                    List<ReportResponse> reportList = new ArrayList<>();
//                    reportList.add(currentReport);
//                    adapter.updateData(reportList);
//                } else {
//                    Toast.makeText(getContext(), "Failed to fetch report", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ReportResponse> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), "Error fetching report. Check internet connection.", Toast.LENGTH_SHORT).show();
//                Log.e("ReportFragment", "Error: " + t.getMessage());
//            }
//        });
//    }
//
//    private String convertDateFormat(String dateStr) {
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//            Date date = inputFormat.parse(dateStr);
//            return outputFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private void exportToPDF() {
//        if (currentReport == null) {
//            Toast.makeText(getContext(), "Please generate the report first", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        PdfDocument document = new PdfDocument();
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(12);
//
//        canvas.drawText("Usage Report", 10, 10, paint);
//        canvas.drawText("Date", 10, 30, paint);
//        canvas.drawText("Usage", 100, 30, paint);
//
//        int y = 50;
//        String[] dates = {"2023-01-01", "2023-01-02", "2023-01-03"};
//        String[] usages = {"100", "150", "120"};
//        for (int i = 0; i < dates.length; i++) {
//            canvas.drawText(dates[i], 10, y, paint);
//            canvas.drawText(usages[i], 100, y, paint);
//            y += 20;
//            if (y > 800) {
//                document.finishPage(page);
//                page = document.startPage(pageInfo);
//                canvas = page.getCanvas();
//                y = 10;
//            }
//        }
//
//        document.finishPage(page);
//
//        File cacheDir = requireContext().getCacheDir();
//        File pdfFile = new File(cacheDir, "report.pdf");
//        try {
//            document.writeTo(new FileOutputStream(pdfFile));
//            document.close();
//            sharePdfFile(pdfFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getContext(), "Error creating PDF", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void sharePdfFile(File pdfFile) {
//        String authority = requireContext().getPackageName() + ".fileprovider";
//        Uri uri = FileProvider.getUriForFile(requireContext(), authority, pdfFile);
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(intent, "Share PDF"));
//    }
//
//    private void fetchMeterSNOs() {
//        Token token = sharedPrefManager.getUser();
//        String tokenStr = "Bearer " + token.getToken();
//        SaveEmail saveEmail = new SaveEmail(token.getEmail());
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<List<HouseholdsModel>> call = requestApi.getAllMeter(tokenStr, saveEmail);
//
//        call.enqueue(new Callback<List<HouseholdsModel>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    meterSNOList.clear();
//                    for (HouseholdsModel model : response.body()) {
//                        String meterSNO = model.getMetersno();
//                        if (meterSNO != null) {
//                            meterSNOList.add(meterSNO);
//                        }
//                    }
//                    updateMeterSNOAdapter();
//                } else {
//                    Toast.makeText(getContext(), "Failed to fetch meter SNOs", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<HouseholdsModel>> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//                Log.e("ReportFragment", "Error: " + t.getMessage());
//            }
//        });
//    }
//
//    private void updateMeterSNOAdapter() {
//        MeterSNOAdapter meterAdapter = new MeterSNOAdapter(requireContext(), meterSNOList);
//        autoCompleteMeterSNO.setAdapter(meterAdapter);
//        autoCompleteMeterSNO.setThreshold(1);
//
//        autoCompleteMeterSNO.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedMeter = (String) parent.getItemAtPosition(position);
//            autoCompleteMeterSNO.setText(selectedMeter);
//        });
//    }
//
//    private void fetchSiteName() {
//        Token token = sharedPrefManager.getUser();
//        String tokenStr = "Bearer " + token.getToken();
//        SaveEmail saveEmail = new SaveEmail(token.getEmail());
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<List<HouseholdsModel>> call = requestApi.getAllSites(tokenStr, saveEmail);
//
//        call.enqueue(new Callback<List<HouseholdsModel>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<HouseholdsModel>> call, @NonNull Response<List<HouseholdsModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Set<String> siteSet = new HashSet<>();
//                    for (HouseholdsModel model : response.body()) {
//                        String siteName = model.getSiteName();
//                        if (siteName != null) {
//                            siteSet.add(siteName);
//                        }
//                    }
//                    siteNameList.clear();
//                    siteNameList.addAll(siteSet);
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
//                            android.R.layout.simple_spinner_item, siteNameList);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerSite.setAdapter(adapter);
//                } else {
//                    Toast.makeText(getContext(), "Failed to fetch site names", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<HouseholdsModel>> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//                Log.e("ReportFragment", "Error: " + t.getMessage());
//            }
//        });
//    }
//
//    private void generateInvoice() {
//        String startDateStr = startDate.getText().toString();
//        String endDateStr = endDate.getText().toString();
//        String sno = autoCompleteMeterSNO.getText().toString();
//
//        if (startDateStr.isEmpty() || endDateStr.isEmpty() || sno.isEmpty()) {
//            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String formattedStartDate = convertDateFormat(startDateStr);
//        String formattedEndDate = convertDateFormat(endDateStr);
//
//        if (formattedStartDate == null || formattedEndDate == null) {
//            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Hardcoded values as per the provided JSON (replace with actual data from currentReport or calculations in a real app)
//        InvoiceRequest request = new InvoiceRequest(
//                formattedStartDate,           // "2025-03-01"
//                formattedEndDate,             // "2025-03-08"
//                sno,                          // "506F98000000C7EE"
//                currentReport.getEb_kwh_open(),
//                currentReport.getEb_kwh_close(),
//                currentReport.getCon_eb_kwh(),
//                currentReport.getDg_kwh_open(),
//                currentReport.getDg_kwh_close(),
//                currentReport.getCon_dg_kwh(),
//                currentReport.getEb_tf(),
//                currentReport.getDg_tf(),
//                currentReport.getDc_tf(),
//                currentReport.getAmount_open(),
//                currentReport.getAmount_close(),
//                currentReport.getActivate_days(),
//                currentReport.getNet_amount(),
//                currentReport.getTotal_Recharge(),
//                currentReport.getActual_start_date(),
//                currentReport.getActual_end_date(),
//                currentReport.getTotal_cam_amount(),
//                currentReport.getTotal_except_cam_amount(),
//                currentReport.getTotal_amount()
//        );
//
//        String token = sharedPrefManager.getAccessToken();
//        if (token == null) {
//            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String tokenStr = "Bearer " + token;
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<CreateModel> call = requestApi.createInvoice(tokenStr, request);
//
//        call.enqueue(new Callback<CreateModel>() {
//            @Override
//            public void onResponse(@NonNull Call<CreateModel> call, @NonNull Response<CreateModel> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    CreateModel invoice = response.body();
//                    List<CreateModel> invoiceList = new ArrayList<>();
//                    invoiceList.add(invoice);
//                    CreateAdapter createAdapter = new CreateAdapter(invoiceList);
//                    recyclerViewInvoice.setAdapter(createAdapter);
//                    Toast.makeText(getContext(), "Invoice generated successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Failed to create invoice", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<CreateModel> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), "Error creating invoice: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("CreateFragment", "Error: " + t.getMessage());
//            }
//        });
//    }
//}