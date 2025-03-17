package com.example.siotel.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.siotel.R;
import com.example.siotel.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceListFragment extends Fragment {

    private TableLayout tableLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        Button btnDashboard = view.findViewById(R.id.btn_dashboard); // Find Dashboard button

        // Set click listener for Dashboard button
        btnDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish(); // Optional: Closes the current fragment's activity
        });

        // Sample invoice data
        List<Invoice> invoiceList = getSampleInvoices();
        // Populate table dynamically
        populateTable(invoiceList);

        return view;
    }

    private void populateTable(List<Invoice> invoiceList) {
        for (Invoice invoice : invoiceList) {
            TableRow row = new TableRow(requireContext());  // Use requireContext() in a Fragment
            row.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            row.setPadding(10, 10, 10, 10);
            row.setBackgroundColor(Color.WHITE);

            // Creating and adding TextViews for each column
            row.addView(createTextView(invoice.getInvoiceId()));
            row.addView(createTextView(invoice.getCustomer()));
            row.addView(createTextView(invoice.getAddress()));
            row.addView(createTextView(invoice.getInvoiceDate()));
            row.addView(createTextView(invoice.getDueDate()));
            row.addView(createTextView("₹" + invoice.getTotalAmount()));

            // Status TextView (Paid/Unpaid)
            TextView statusView = createTextView(invoice.getStatus());
            if ("Paid".equals(invoice.getStatus())) {
                statusView.setBackgroundColor(Color.GREEN);
                statusView.setTextColor(Color.WHITE);
            } else {
                statusView.setBackgroundColor(Color.YELLOW);
                statusView.setTextColor(Color.BLACK);
            }
            row.addView(statusView);

            // View Button
            Button viewButton = new Button(requireContext());
            viewButton.setText("View");
            viewButton.setBackgroundColor(Color.BLUE);
            viewButton.setTextColor(Color.WHITE);
            row.addView(viewButton);

            // Download TextView (Clickable)
            TextView downloadView = createTextView("Download");
            downloadView.setTextColor(Color.BLUE);
            row.addView(downloadView);

            // Add row to table
            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(14);
        return textView;
    }

    private List<Invoice> getSampleInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice("#27", "UG_01", "506F980000006FC5", "Aug. 1, 2022", "Aug. 25, 2022", "25939.35", "Paid"));
        invoices.add(new Invoice("#28", "UG_03", "506F980000006FC1", "Aug. 1, 2022", "Aug. 30, 2022", "5712.68", "Pay"));
        invoices.add(new Invoice("#30", "UG_01", "506F980000006FC5", "Aug. 3, 2022", "Aug. 18, 2022", "1642.65", "Paid"));
        invoices.add(new Invoice("#31", "UG_01", "506F980000006FC5", "Aug. 3, 2022", "Aug. 18, 2022", "797.65", "Paid"));
        invoices.add(new Invoice("#32", "LG_01", "506F980000006F9C", "Aug. 12, 2022", "Aug. 27, 2022", "7369.84", "Paid"));
        return invoices;
    }

    // Invoice Data Model
    static class Invoice {
        private final String invoiceId, customer, address, invoiceDate, dueDate, totalAmount, status;

        public Invoice(String invoiceId, String customer, String address, String invoiceDate, String dueDate, String totalAmount, String status) {
            this.invoiceId = invoiceId;
            this.customer = customer;
            this.address = address;
            this.invoiceDate = invoiceDate;
            this.dueDate = dueDate;
            this.totalAmount = totalAmount;
            this.status = status;
        }

        public String getInvoiceId() { return invoiceId; }
        public String getCustomer() { return customer; }
        public String getAddress() { return address; }
        public String getInvoiceDate() { return invoiceDate; }
        public String getDueDate() { return dueDate; }
        public String getTotalAmount() { return totalAmount; }
        public String getStatus() { return status; }
    }
}
