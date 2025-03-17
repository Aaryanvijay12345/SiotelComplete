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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        sharedPrefManager = new SharedPrefManager(requireContext());

        recyclerView = view.findViewById(R.id.recyclerViewHouseholds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize UI components
        spinnerSite = view.findViewById(R.id.spinnerSite);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO);
        startDate = view.findViewById(R.id.startDate);
        endDate = view.findViewById(R.id.endDate);
        btnGetReport = view.findViewById(R.id.btnGetReport);
        btnExportExcel = view.findViewById(R.id.btnExportExcel);

        // Set up Spinners
        setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
        setupSpinner(spinnerSite, new String[]{"Grand Anukampa"});

        // Fetch meter SNOs from API
        fetchMeterSNOs();

        // Date picker dialogs
        setupDatePicker(startDate);
        setupDatePicker(endDate);

        // Button click listeners
        btnGetReport.setOnClickListener(v -> generateReport());
        btnExportExcel.setOnClickListener(v -> exportToExcel());

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

    private void exportToExcel() {
        // Implement functionality to generate and share an Excel file
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
}