//package com.example.siotel.fragment;
//
//import android.app.DatePickerDialog;
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
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.siotel.R;
//import com.example.siotel.SharedPrefManager;
//import com.example.siotel.adapters.HouseholdsDetailsAdapter;
//import com.example.siotel.api.PostRequestApi;
//import com.example.siotel.models.HouseholdsDetailsModel;
//import com.example.siotel.models.HouseholdsModel;
//import com.example.siotel.models.SaveEmail;
//import com.example.siotel.models.Token;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ReportFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private HouseholdsDetailsAdapter adapter;
//
//    private Spinner spinnerSite, spinnerYear;
//    private AutoCompleteTextView autoCompleteMeterSNO;
//    private EditText startDate, endDate;
//    private Button btnGetReport, btnExportExcel;
//    private SharedPrefManager sharedPrefManager;
//    private List<String> meterSNOList = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_report, container, false);
//
//        sharedPrefManager = new SharedPrefManager(requireContext());
//
//
//        recyclerView = view.findViewById(R.id.recyclerViewHouseholds);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new HouseholdsDetailsAdapter(new ArrayList<>());
//        recyclerView.setAdapter(adapter);
//
//
//        // Initialize UI components
//        spinnerSite = view.findViewById(R.id.spinnerSite);
//        spinnerYear = view.findViewById(R.id.spinnerYear);
//        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO);
//        startDate = view.findViewById(R.id.startDate);
//        endDate = view.findViewById(R.id.endDate);
//        btnGetReport = view.findViewById(R.id.btnGetReport);
//        btnExportExcel = view.findViewById(R.id.btnExportExcel);
//
//        // Set up Spinners
//        setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
//        setupSpinner(spinnerSite, new String[]{"Site A", "Site B", "Site C"});
//
//        // Fetch meter SNOs from API
//        fetchMeterSNOs();
//
//        // Date picker dialogs
//        setupDatePicker(startDate);
//        setupDatePicker(endDate);
//
//        // Button click listeners
//        btnGetReport.setOnClickListener(v -> generateReport());
//        btnExportExcel.setOnClickListener(v -> exportToExcel());
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
//        String token = sharedPrefManager.getAccessToken();
//        if (token == null) {
//            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String tokenStr = "Bearer " + token;
//        String url = "MeterdataApi/" + meterSNO; // Similar to HouseholdsDetailsFragment
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://meters.siotel.in")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
//        Call<List<HouseholdsDetailsModel>> call = requestApi.getMetersDtl(url, tokenStr);
//
//        call.enqueue(new Callback<List<HouseholdsDetailsModel>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<HouseholdsDetailsModel>> call, @NonNull Response<List<HouseholdsDetailsModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<HouseholdsDetailsModel> detailsList = response.body();
//                    updateRecyclerView(detailsList); // Update UI
//                } else {
//                    Toast.makeText(getContext(), "Failed to fetch household details", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//    public void onFailure(@NonNull Call<List<HouseholdsDetailsModel>> call, @NonNull Throwable t) {
//        Toast.makeText(getContext(), "Error fetching details. Check internet connection.", Toast.LENGTH_SHORT).show();
//        Log.e("ReportFragment", "Error: " + t.getMessage());
//    }
//});}
//
//
//    private void exportToExcel() {
//        // Implement functionality to generate and share an Excel file
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
//                    for (HouseholdsModel model : response.body()) {
//                        meterSNOList.add(model.getMetersno());
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
//        ArrayAdapter<String> meterAdapter = new ArrayAdapter<>(requireContext(),
//                android.R.layout.simple_dropdown_item_1line, meterSNOList);
//
//        autoCompleteMeterSNO.setAdapter(meterAdapter);
//        autoCompleteMeterSNO.setThreshold(1); // Show suggestions from the first character
//
//        autoCompleteMeterSNO.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedMeter = (String) parent.getItemAtPosition(position);
//            autoCompleteMeterSNO.setText(selectedMeter); // Set selected item in text box
//        });
//    }
//
//    private void updateRecyclerView(List<HouseholdsDetailsModel> detailsList) {
//        adapter.updateData(detailsList);
//    }
//
//
//}



package com.example.siotel.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import com.example.siotel.adapters.MeterSNOAdapter;
import com.example.siotel.adapters.ReportAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsModel;
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

public class ReportFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter adapter; // Changed to ReportAdapter
    private Spinner spinnerSite, spinnerYear;
    private AutoCompleteTextView autoCompleteMeterSNO;
    private EditText startDate, endDate;
    private Button btnGetReport, btnExportExcel;
    private SharedPrefManager sharedPrefManager;
    private List<String> meterSNOList = new ArrayList<>();

    private List<String> siteNameList = new ArrayList<>();

    private ReportResponse currentReport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        sharedPrefManager = new SharedPrefManager(requireContext());

        recyclerView = view.findViewById(R.id.recyclerViewRechargeHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize UI components
        spinnerSite = view.findViewById(R.id.spinner_select_site);
        spinnerYear = view.findViewById(R.id.spinnerTransactionType);
        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO1);
        startDate = view.findViewById(R.id.edittext_start_date);
        endDate = view.findViewById(R.id.edittext_end_date);
        btnGetReport = view.findViewById(R.id.button_get_report);
        btnExportExcel = view.findViewById(R.id.btnExportExcel);

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
                    currentReport = report;
                    Log.d("ReportFragment", "Report fetched: " + report.toString());
                    List<UsageEntry> entries = report.getUsageEntries();
                    if (entries != null) {
                        Log.d("ReportFragment", "Usage entries count: " + entries.size());
                        for (UsageEntry entry : entries) {
                            Log.d("ReportFragment", "Entry - Date: " + entry.getDate() + ", Usage: " + entry.getUsage());
                        }
                    } else {
                        Log.d("ReportFragment", "Usage entries is null");
                    }
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
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Common styling
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.DKGRAY);
        borderPaint.setStrokeWidth(1.5f);

        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.parseColor("#f1f1f1"));

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(18);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint headerPaint = new Paint();
        headerPaint.setColor(Color.BLACK);
        headerPaint.setTextSize(9);
        headerPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(8);

        float startX = 30;
        float currentY = 40;

        // Title
        String title = "Monthly Electricity Usage Report";
        float titleX = (pageInfo.getPageWidth() - titlePaint.measureText(title)) / 2;
        canvas.drawText(title, titleX, currentY, titlePaint);

        currentY += 30;

        // Meter SNo section
        float meterBoxWidth = 535;
        float meterBoxHeight = 40;

        canvas.drawRect(startX, currentY, startX + meterBoxWidth, currentY + meterBoxHeight, fillPaint);
        canvas.drawRect(startX, currentY, startX + meterBoxWidth, currentY + meterBoxHeight, borderPaint);

        Paint labelPaint = new Paint(headerPaint);
        labelPaint.setTextSize(10);

        String meterLabel = "Meter Serial Number:";
        canvas.drawText(meterLabel, startX + 10, currentY + 25, labelPaint);

        String meterSno = currentReport.getSno() != null ? currentReport.getSno() : "N/A";
        canvas.drawText(meterSno, startX + 180, currentY + 25, textPaint);

        currentY += meterBoxHeight + 20;

        // Table headers
        String[] headers = {
                "EB Open", "EB Close", "EB Cons", "DG Open", "DG Close", "DG Cons",
                "EB Tf", "DG Tf", "Daily Chg", "Amt Open", "Amt Close", "Act Days",
                "Net Amt", "Tot Rech", "Start", "End", "CAM Rech", "Elec Rech", "Tot Amt"
        };

        String[] data = {
                String.valueOf(currentReport.getEb_kwh_open()),
                String.valueOf(currentReport.getEb_kwh_close()),
                String.valueOf(currentReport.getCon_eb_kwh()),
                String.valueOf(currentReport.getDg_kwh_open()),
                String.valueOf(currentReport.getDg_kwh_close()),
                String.valueOf(currentReport.getCon_dg_kwh()),
                String.valueOf(currentReport.getEb_tf()),
                String.valueOf(currentReport.getDg_tf()),
                String.valueOf(currentReport.getDc_tf()),
                String.valueOf(currentReport.getAmount_open()),
                String.valueOf(currentReport.getAmount_close()),
                String.valueOf(currentReport.getActivate_days()),
                String.valueOf(currentReport.getNet_amount()),
                String.valueOf(currentReport.getTotal_Recharge()),
                currentReport.getActual_start_date() != null ? currentReport.getActual_start_date() : "N/A",
                currentReport.getActual_end_date() != null ? currentReport.getActual_end_date() : "N/A",
                String.valueOf(currentReport.getTotal_cam_amount()),
                String.valueOf(currentReport.getTotal_except_cam_amount()),
                String.valueOf(currentReport.getTotal_amount())
        };

        int cols = 10;
        int rows = 2;
        float rowHeight = 30;

        for (int r = 0; r < rows; r++) {
            float rowStartY = currentY + r * (2 * rowHeight + 10);
            float currentX = startX;

            canvas.drawRect(startX, rowStartY, startX + 535, rowStartY + rowHeight, fillPaint);

            for (int c = 0; c < cols; c++) {
                int index = r * cols + c;
                if (index >= headers.length) break;

                // Set width dynamically for "Start" and "End"
                float colWidth = (index == 14 || index == 15) ? 80f : 52f;

                // Border
                canvas.drawRect(currentX, rowStartY, currentX + colWidth, rowStartY + 2 * rowHeight, borderPaint);

                // Header text
                canvas.drawText(headers[index], currentX + 5, rowStartY + 20, headerPaint);

                // Data text
                canvas.drawText(data[index], currentX + 5, rowStartY + rowHeight + 20, textPaint);

                currentX += colWidth;
            }
        }

        // Footer - optional note
        currentY += rows * (2 * rowHeight + 10);
        currentY += 30;
        Paint footerPaint = new Paint();
        footerPaint.setColor(Color.GRAY);
        footerPaint.setTextSize(8);
        String footerNote = "Made With ❤️ from SSTPL";
        canvas.drawText(footerNote, startX, currentY, footerPaint);

        document.finishPage(page);

        File pdfFile = new File(requireContext().getCacheDir(), "report.pdf");
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
}