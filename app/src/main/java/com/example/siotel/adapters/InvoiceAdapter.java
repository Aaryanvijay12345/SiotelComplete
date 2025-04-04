package com.example.siotel.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.Invoice;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private List<Invoice> invoiceList;
    private OnViewClickListener onViewClickListener;
    private OnDownloadClickListener onDownloadClickListener;

    public interface OnViewClickListener {
        void onViewClick(Invoice invoice);
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(Invoice invoice);
    }

    public InvoiceAdapter(List<Invoice> invoiceList, OnViewClickListener onViewClickListener, OnDownloadClickListener onDownloadClickListener) {
        this.invoiceList = invoiceList;
        this.onViewClickListener = onViewClickListener;
        this.onDownloadClickListener = onDownloadClickListener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        if (position == 0) {
            // Set header row with bold styling and background color
            holder.invoiceIdTextView.setBackgroundColor(Color.LTGRAY);
            holder.customerTextView.setBackgroundColor(Color.LTGRAY);
            holder.billingAddressTextView.setBackgroundColor(Color.LTGRAY);
            holder.dateTextView.setBackgroundColor(Color.LTGRAY);
            holder.dueDateTextView.setBackgroundColor(Color.LTGRAY);
            holder.totalAmountTextView.setBackgroundColor(Color.LTGRAY);
            holder.statusTextView.setBackgroundColor(Color.LTGRAY);
            holder.viewButton.setBackgroundColor(Color.LTGRAY);
            holder.downloadTextView.setBackgroundColor(Color.LTGRAY);

            holder.invoiceIdTextView.setText("Invoice ID");
            holder.customerTextView.setText("Customer");
            holder.billingAddressTextView.setText("Billing Address");
            holder.dateTextView.setText("Invoice Date");
            holder.dueDateTextView.setText("Due Date");
            holder.totalAmountTextView.setText("Amount");
            holder.statusTextView.setText("Status");
            holder.viewButton.setText("View");
            holder.downloadTextView.setText("Download");
        } else {
            Invoice invoice = invoiceList.get(position - 1);

            holder.invoiceIdTextView.setText("#" + invoice.getId());
            holder.customerTextView.setText(invoice.getCustomer());
            holder.billingAddressTextView.setText(invoice.getBillingAddress());
            holder.dateTextView.setText(invoice.getDate());
            holder.dueDateTextView.setText(invoice.getDueDate());
            holder.totalAmountTextView.setText(String.format("%.2f", invoice.getTotalAmount()));

            if (invoice.isStatus()) {
                holder.statusTextView.setText("Paid");
                holder.statusTextView.setTextColor(Color.GREEN);
            } else {
                holder.statusTextView.setText("PAY");
                holder.statusTextView.setTextColor(Color.parseColor("#FFD700"));
            }

            holder.viewButton.setOnClickListener(v -> {
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(invoice);
                }
            });

            holder.downloadTextView.setOnClickListener(v -> {
                if (onDownloadClickListener != null) {
                    onDownloadClickListener.onDownloadClick(invoice);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return invoiceList != null ? invoiceList.size() + 1 : 1; // Include header row
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView invoiceIdTextView, customerTextView, billingAddressTextView, dateTextView,
                dueDateTextView, totalAmountTextView, statusTextView, downloadTextView;
        Button viewButton;

        InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            invoiceIdTextView = itemView.findViewById(R.id.text_invoice_id);
            customerTextView = itemView.findViewById(R.id.text_customer);
            billingAddressTextView = itemView.findViewById(R.id.text_billing_address);
            dateTextView = itemView.findViewById(R.id.text_date);
            dueDateTextView = itemView.findViewById(R.id.text_due_date);
            totalAmountTextView = itemView.findViewById(R.id.text_total_amount);
            statusTextView = itemView.findViewById(R.id.text_status);
            viewButton = itemView.findViewById(R.id.button_view);
            downloadTextView = itemView.findViewById(R.id.button_download);
        }
    }
}
