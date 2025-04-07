
package com.example.siotel.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.adapters.InvoiceAdapter;
import com.example.siotel.adapters.InvoiceDetailAdapter;
import com.example.siotel.api.PostRequestApi;
import com.example.siotel.models.EmailRequest;
import com.example.siotel.models.Invoice;
import com.example.siotel.models.InvoiceDetail;
import com.example.siotel.models.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InvoiceListFragment extends Fragment {

    private RecyclerView recyclerView, recyclerView1;
    private ProgressBar progressBar;
    private InvoiceAdapter invoiceAdapter;
    private SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView1 = view.findViewById(R.id.recycler_view1);
        if (recyclerView1 == null) {
            Log.e("InvoiceListFragment", "RecyclerView1 is NULL! Check XML layout ID.");
        } else {
            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        }


        // Initialize SharedPrefManager
        sharedPrefManager = new SharedPrefManager(getActivity());

        // Fetch and display invoice data
        fetchInvoiceList();

        return view;
    }

    private void fetchInvoiceList() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Token token = sharedPrefManager.getUser();
        if (token == null || token.getEmail() == null) {
            Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        String tokenStr = token.getToken();
        if (tokenStr == null || tokenStr.isEmpty()) {
            Toast.makeText(getActivity(), "Authentication token is missing", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        tokenStr = "Bearer " + tokenStr;

        String email = token.getEmail();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);

        // Create request body
        EmailRequest emailRequest = new EmailRequest(email);

        Call<List<Invoice>> call = requestApi.getInvoiceList(tokenStr, emailRequest);

        call.enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                Log.d("InvoiceListFragment", "Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Invoice> invoices = response.body();
                    if (!invoices.isEmpty()) {
                        invoiceAdapter = new InvoiceAdapter(
                                requireContext(),
                                invoices,
                                invoice -> fetchInvoiceDetails(invoice.getId()),
                                invoice -> Toast.makeText(getActivity(), "Downloading Invoice #" + invoice.getId(), Toast.LENGTH_SHORT).show()
                        );



                        recyclerView.setAdapter(invoiceAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), "No invoices found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    handleErrorResponse(response);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Log.e("InvoiceListFragment", "API Call Failed: " + t.getMessage());
                Toast.makeText(getActivity(), "Failed to load invoices: " + t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
        // Handles detailed error messages
    private void handleErrorResponse(Response<?> response) {
        String errorMessage = "Failed to load invoices: HTTP " + response.code();
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error details";
            Log.e("InvoiceListFragment", "Error Body: " + errorBody);
            errorMessage += " - " + errorBody;
        } catch (Exception e) {
            Log.e("InvoiceListFragment", "Error reading error body", e);
        }
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void fetchInvoiceDetails(int invoiceId) {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://meters.siotel.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestApi requestApi = retrofit.create(PostRequestApi.class);
        Call<InvoiceDetail> call = requestApi.getInvoiceDetail(invoiceId);

        call.enqueue(new Callback<InvoiceDetail>() {
            @Override
            public void onResponse(Call<InvoiceDetail> call, Response<InvoiceDetail> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<InvoiceDetail> detailsList = new ArrayList<>();
                    detailsList.add(response.body());

                    InvoiceDetailAdapter detailAdapter = new InvoiceDetailAdapter(detailsList);
                    recyclerView1.setAdapter(detailAdapter);
                    recyclerView1.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch invoice details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvoiceDetail> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}