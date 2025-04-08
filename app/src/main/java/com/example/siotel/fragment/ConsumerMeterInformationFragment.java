package com.example.siotel.fragment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
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
import com.example.siotel.adapters.HouseholdsDetailsAdapter;
import com.example.siotel.adapters.ConsumerMeterInfromationAdapter;
import com.example.siotel.adapters.MeterSNOAdapter;
import com.example.siotel.api.PostRequestApi;
//import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.ConsumerMeterInformationModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.ReportResponse;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

public class ConsumerMeterInformationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ConsumerMeterInfromationAdapter adapter;
    private Spinner spinnerSite, spinnerYear;
    private AutoCompleteTextView autoCompleteMeterSNO;
    private EditText startDate, endDate;
    private Button btnGetReport, btnDownload;
    private SharedPrefManager sharedPrefManager;
    private List<String> meterSNOList = new ArrayList<>();

    private List<String> siteNameList = new ArrayList<>();
    private List<ConsumerMeterInformationModel> reportList;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_meter_information, container, false);
        sharedPrefManager = new SharedPrefManager(requireContext());


        recyclerView = view.findViewById(R.id.recyclerViewMeterInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ConsumerMeterInfromationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        spinnerSite = view.findViewById(R.id.spinner_select_site);
        spinnerYear = view.findViewById(R.id.spinner_financial_year);
        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO1);
        startDate = view.findViewById(R.id.edittext_start_date);
        endDate = view.findViewById(R.id.edittext_end_date);
        btnGetReport = view.findViewById(R.id.button_get_report);
        btnDownload = view.findViewById(R.id.btnExportExcel1);

        setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
        fetchSiteName();

        fetchMeterSNOs();
        setupDatePicker(startDate);
        setupDatePicker(endDate);

        btnGetReport.setOnClickListener(v ->{
            Log.d(TAG, "onClick: Get Report button clicked");
            generateReport();} );

        btnDownload.setOnClickListener(v -> exportToPDF());

        return view;
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("RestrictedApi")
    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view, year, month, day) -> {
                String selectedDate = day + "-" + (month + 1) + "-" + year;
                editText.setText(selectedDate);
                Log.d(TAG, "Selected date: " + selectedDate);

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    @SuppressLint("RestrictedApi")
    private void generateReport() {
        String meterSNO = autoCompleteMeterSNO.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String financeYear = spinnerYear.getSelectedItem().toString();

        Log.d(TAG, "Generating report for: " + meterSNO + " | " + start + " - " + end + " | " + financeYear);

        if (meterSNO.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String formattedStart = formatDateToYYYYMMDD(start);
        String formattedEnd = formatDateToYYYYMMDD(end);

        if (formattedStart == null || formattedEnd == null) {
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = sharedPrefManager.getAccessToken();
        if (token == null) {
            Toast.makeText(getContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "Formatted dates: " + formattedStart + " to " + formattedEnd);
        Log.d(TAG, "Using token: " + token);
        String tokenStr = "Bearer " + token;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                "{\"startdate\":\"" + formattedStart + "\", \"enddate\":\"" + formattedEnd + "\", \"Finance_Year\":\"" + financeYear + "\"}"
        );


        Call<List<ConsumerMeterInformationModel>> call = requestApi.getMetersDtl1(meterSNO, tokenStr, requestBody);




        call.enqueue(new Callback<List<ConsumerMeterInformationModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ConsumerMeterInformationModel>> call, @NonNull Response<List<ConsumerMeterInformationModel>> response) {
                Log.d(TAG, "Response Code: " + response.code());
                Log.d(TAG, "Response Message: " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    reportList = response.body();
                    Log.d(TAG, "Data fetched successfully: " + response.body().toString());
                    adapter.updateData(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e(TAG, "Failed to fetch meter data: " + response.code() + " - " + response.message() + " | Error Body: " + errorBody);
                        Toast.makeText(getContext(), "Failed to fetch meter data: " + response.message(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ConsumerMeterInformationModel>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error fetching data. Check internet connection.", Toast.LENGTH_SHORT).show();
                Log.e("ConsumerMeterFragment", "Error: " + t.getMessage());
                Log.e(TAG, "Error fetching data: " + t.getMessage());
            }
        });
    }

    private String formatDateToYYYYMMDD(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            return outputFormat.format(inputFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
                    for (HouseholdsModel model : response.body()) {
                        meterSNOList.add(model.getMetersno());
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

    private void updateRecyclerView(List<ConsumerMeterInformationModel> detailsList) {
        adapter.updateData(detailsList);
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

    private void exportToPDF() {
        if (reportList == null || reportList.isEmpty()) {
            Toast.makeText(getContext(), "Please generate the report first", Toast.LENGTH_SHORT).show();
            return;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4 size
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Paints
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(16);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextAlign(Paint.Align.CENTER);

        Paint datePaint = new Paint();
        datePaint.setColor(Color.DKGRAY);
        datePaint.setTextSize(10);
        datePaint.setTextAlign(Paint.Align.CENTER);

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStrokeWidth(1);

        Paint headerPaint = new Paint();
        headerPaint.setColor(Color.WHITE);
        headerPaint.setTextSize(10);
        headerPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        headerPaint.setTextAlign(Paint.Align.CENTER);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(9);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint headerFillPaint = new Paint();
        headerFillPaint.setStyle(Paint.Style.FILL);
        headerFillPaint.setColor(Color.rgb(63, 81, 181)); // Indigo

        Paint rowAltPaint = new Paint();
        rowAltPaint.setStyle(Paint.Style.FILL);
        rowAltPaint.setColor(Color.rgb(240, 240, 240)); // light gray

        String[] headers = {"MeterSno", "Amount", "Cum_eb_kwh", "Date"};
        float startX = 40;
        float startY = 80;
        float colWidth = 130;
        int numCols = headers.length;
        float rowHeight = 30;

        float pageHeight = pageInfo.getPageHeight();
        float usableHeight = pageHeight - startY - 100;
        int rowsPerPage = (int) (usableHeight / rowHeight) - 1;

        int numDataRows = reportList.size();
        int currentRow = 0;
        int pageNumber = 1;

        while (currentRow < numDataRows) {
            if (currentRow > 0) {
                document.finishPage(page);
                pageInfo = new PdfDocument.PageInfo.Builder(595, 842, ++pageNumber).create();
                page = document.startPage(pageInfo);
                canvas = page.getCanvas();
            }

            // Title
            canvas.drawText("Consumer Meter Report", pageInfo.getPageWidth() / 2, 30, titlePaint);

            // Date/Time
            String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());
            canvas.drawText("Generated on: " + timestamp, pageInfo.getPageWidth() / 2, 50, datePaint);

            // Header background
            canvas.drawRect(startX, startY, startX + numCols * colWidth, startY + rowHeight, headerFillPaint);

            // Header text
            for (int i = 0; i < numCols; i++) {
                float x = startX + i * colWidth + colWidth / 2;
                canvas.drawText(headers[i], x, startY + 20, headerPaint);
            }

            int rowsToDraw = Math.min(rowsPerPage, numDataRows - currentRow);
            float tableHeight = (rowsToDraw + 1) * rowHeight;

            canvas.drawRect(startX, startY, startX + numCols * colWidth, startY + tableHeight, borderPaint);

            for (int row = 0; row < rowsToDraw; row++) {
                float topY = startY + (row + 1) * rowHeight;
                if (row % 2 == 0) {
                    canvas.drawRect(startX, topY, startX + numCols * colWidth, topY + rowHeight, rowAltPaint);
                }

                ConsumerMeterInformationModel report = reportList.get(currentRow + row);
                String[] data = {
                        report.getMeterSN() != null ? report.getMeterSN() : "N/A",
                        String.valueOf(report.getBalance_amount()),
                        String.valueOf(report.getCum_eb_kwh()),
                        report.getDate() != null ? report.getDate() : "N/A"
                };

                for (int col = 0; col < numCols; col++) {
                    float x = startX + col * colWidth + colWidth / 2;
                    float y = topY + 20;
                    canvas.drawText(data[col], x, y, textPaint);
                }

                // Draw horizontal line after row
                canvas.drawLine(startX, topY + rowHeight, startX + numCols * colWidth, topY + rowHeight, borderPaint);
            }

            // Draw vertical lines
            for (int i = 1; i < numCols; i++) {
                float x = startX + i * colWidth;
                canvas.drawLine(x, startY, x, startY + tableHeight, borderPaint);
            }

            // Footer with page number
            Paint footerPaint = new Paint();
            footerPaint.setColor(Color.DKGRAY);
            footerPaint.setTextSize(9);
            footerPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Page " + pageNumber, pageInfo.getPageWidth() - 40, pageInfo.getPageHeight() - 20, footerPaint);

            currentRow += rowsToDraw;
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

}