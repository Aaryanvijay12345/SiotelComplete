package com.example.siotel.fragment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
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
import com.example.siotel.adapters.HouseholdsDetailsAdapter;
import com.example.siotel.adapters.RechargeHistoryAdapter;
import com.example.siotel.adapters.MeterSNOAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.HouseholdsModel;
import com.example.siotel.models.RechargeHistoryItem;
import com.example.siotel.models.ReportRequest;
import com.example.siotel.models.SaveEmail;
import com.example.siotel.models.Token;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

public class RechargeReportFragment extends Fragment {

    private RecyclerView recyclerView;
    private RechargeHistoryAdapter adapter;
    private Spinner spinnerSite, spinnerYear;
    private AutoCompleteTextView autoCompleteMeterSNO;
    private EditText startDate, endDate;
    private Button btnGetReport;
    private SharedPrefManager sharedPrefManager;
    private List<String> meterSNOList = new ArrayList<>();

    private List<String> siteNameList = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge_report, container, false);
        sharedPrefManager = new SharedPrefManager(requireContext());


        recyclerView = view.findViewById(R.id.recyclerViewMeterInfo2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RechargeHistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        spinnerSite = view.findViewById(R.id.spinner_select_site2);
        spinnerYear = view.findViewById(R.id.spinner_financial_year2);
        autoCompleteMeterSNO = view.findViewById(R.id.autoCompleteMeterSNO2);
        startDate = view.findViewById(R.id.edittext_start_date2);
        endDate = view.findViewById(R.id.edittext_end_date2);
        btnGetReport = view.findViewById(R.id.button_get_report2);

        setupSpinner(spinnerYear, new String[]{"2024-2025", "2025-2026", "2026-2027"});
        fetchSiteName();

        fetchMeterSNOs();
        setupDatePicker(startDate);
        setupDatePicker(endDate);

        btnGetReport.setOnClickListener(v ->{
            Log.d(TAG, "onClick: Get Report button clicked");
            generateReport();} );

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



        Call<List<RechargeHistoryItem>> call = requestApi.getRechargeHistory(meterSNO, tokenStr, requestBody);


        call.enqueue(new Callback<List<RechargeHistoryItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<RechargeHistoryItem>> call, @NonNull Response<List<RechargeHistoryItem>> response) {
                Log.d(TAG, "Response Code: " + response.code());
                Log.d(TAG, "Response Message: " + response.message());
                if (response.isSuccessful() && response.body() != null) {
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
            public void onFailure(@NonNull Call<List<RechargeHistoryItem>> call, @NonNull Throwable t) {
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

    private void updateRecyclerView(List<RechargeHistoryItem> detailsList) {
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

}