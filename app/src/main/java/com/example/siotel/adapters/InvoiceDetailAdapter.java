package com.example.siotel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.InvoiceDetail;

import java.util.List;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<InvoiceDetail> invoiceDetailList;

    public InvoiceDetailAdapter(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_detail_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_detail, parent, false);
            return new InvoiceDetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InvoiceDetailViewHolder) {
            InvoiceDetail detail = invoiceDetailList.get(position - 1); // Offset by 1 for header

            InvoiceDetailViewHolder itemHolder = (InvoiceDetailViewHolder) holder;
            itemHolder.idTextView.setText(String.valueOf(detail.getId()));
            itemHolder.customerTextView.setText(detail.getCustomer() != null ? detail.getCustomer() : "");
            itemHolder.customerEmailTextView.setText(detail.getCustomerEmail() != null ? detail.getCustomerEmail() : "");
            itemHolder.billingAddressTextView.setText(detail.getBillingAddress() != null ? detail.getBillingAddress() : "");
            itemHolder.dateTextView.setText(detail.getDate() != null ? detail.getDate() : "");
            itemHolder.dueDateTextView.setText(detail.getDueDate() != null ? detail.getDueDate() : "");
            itemHolder.messageTextView.setText(detail.getMessage() != null ? detail.getMessage() : "");
            itemHolder.statusTextView.setText(detail.isStatus() ? "Paid" : "Pending");
            itemHolder.adminEmailTextView.setText(detail.getAdminEmail() != null ? detail.getAdminEmail() : "");
            itemHolder.snoTextView.setText(detail.getSno() != null ? detail.getSno() : "");
            itemHolder.openKwhebTextView.setText(detail.getOpenKwheb() != null ? detail.getOpenKwheb() : "");
            itemHolder.closeKwhebTextView.setText(detail.getCloseKwheb() != null ? detail.getCloseKwheb() : "");
            itemHolder.conKwhebTextView.setText(detail.getConKwheb() != null ? detail.getConKwheb() : "");
            itemHolder.dgtTextView.setText(detail.getDgt() != null ? detail.getDgt() : "");
            itemHolder.mcTextView.setText(detail.getMc() != null ? detail.getMc() : "");
            itemHolder.actdayTextView.setText(detail.getActday() != null ? detail.getActday() : "");
            itemHolder.totalAmountTextView.setText(detail.getTotalAmount() != null ? "₹" + detail.getTotalAmount() : "₹0");
            itemHolder.ebtTextView.setText(detail.getEbt() != null ? detail.getEbt() : "");
            itemHolder.sitenameTextView.setText(detail.getSitename() != null ? detail.getSitename() : "");
            itemHolder.startDateTextView.setText(detail.getStartdate() != null ? detail.getStartdate() : "");
            itemHolder.endDateTextView.setText(detail.getEnddate() != null ? detail.getEnddate() : "");

        }
    }

    @Override
    public int getItemCount() {
        return invoiceDetailList.size() + 1; // +1 for header row
    }

    // ViewHolder for data rows
    static class InvoiceDetailViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, customerTextView, customerEmailTextView, billingAddressTextView, dateTextView,
                dueDateTextView, messageTextView, statusTextView, adminEmailTextView, snoTextView,
                openKwhebTextView, closeKwhebTextView, conKwhebTextView, dgtTextView, mcTextView,
                actdayTextView, totalAmountTextView, ebtTextView, sitenameTextView, startDateTextView, endDateTextView;

        public InvoiceDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.text_id);
            customerTextView = itemView.findViewById(R.id.text_customer);
            customerEmailTextView = itemView.findViewById(R.id.text_customer_email);
            billingAddressTextView = itemView.findViewById(R.id.text_billing_address);
            dateTextView = itemView.findViewById(R.id.text_date);
            dueDateTextView = itemView.findViewById(R.id.text_due_date);
            messageTextView = itemView.findViewById(R.id.text_message);
            statusTextView = itemView.findViewById(R.id.text_status);
            adminEmailTextView = itemView.findViewById(R.id.text_admin_email);
            snoTextView = itemView.findViewById(R.id.text_sno);
            openKwhebTextView = itemView.findViewById(R.id.text_open_kwheb);
            closeKwhebTextView = itemView.findViewById(R.id.text_close_kwheb);
            conKwhebTextView = itemView.findViewById(R.id.text_con_kwheb);
            dgtTextView = itemView.findViewById(R.id.text_dgt);
            mcTextView = itemView.findViewById(R.id.text_mc);
            actdayTextView = itemView.findViewById(R.id.text_actday);
            totalAmountTextView = itemView.findViewById(R.id.text_total_amount);
            ebtTextView = itemView.findViewById(R.id.text_ebt);
            sitenameTextView = itemView.findViewById(R.id.text_sitename);
            startDateTextView = itemView.findViewById(R.id.text_start_date);
            endDateTextView = itemView.findViewById(R.id.text_end_date);
        }
    }

    // ViewHolder for header row
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
