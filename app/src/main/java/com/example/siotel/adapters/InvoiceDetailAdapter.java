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
            itemHolder.idTextView.setText("ID: " + detail.getId());
            itemHolder.customerTextView.setText("Customer: " + detail.getCustomer());
            itemHolder.customerEmailTextView.setText("Email: " + detail.getCustomerEmail());
            itemHolder.billingAddressTextView.setText("Billing Address: " + detail.getBillingAddress());
            itemHolder.dateTextView.setText("Date: " + detail.getDate());
            itemHolder.dueDateTextView.setText("Due Date: " + detail.getDueDate());
            itemHolder.messageTextView.setText("Message: " + detail.getMessage());
            itemHolder.statusTextView.setText("Status: " + (detail.isStatus() ? "Paid" : "Pending"));
            itemHolder.adminEmailTextView.setText("Admin Email: " + detail.getAdminEmail());
            itemHolder.snoTextView.setText("SNO: " + detail.getSno());
            itemHolder.openKwhebTextView.setText("Open kWheb: " + detail.getOpenKwheb());
            itemHolder.closeKwhebTextView.setText("Close kWheb: " + detail.getCloseKwheb());
            itemHolder.conKwhebTextView.setText("Consumption kWheb: " + detail.getConKwheb());
            itemHolder.dgtTextView.setText("DGT: " + detail.getDgt());
            itemHolder.mcTextView.setText("MC: " + detail.getMc());
            itemHolder.actdayTextView.setText("Active Days: " + detail.getActday());
            itemHolder.totalAmountTextView.setText("Total Amount: ₹" + detail.getTotalAmount());
            itemHolder.ebtTextView.setText("EBT: " + detail.getEbt());
            itemHolder.sitenameTextView.setText("Site Name: " + detail.getSitename());
            itemHolder.startDateTextView.setText("Start Date: " + detail.getStartdate());
            itemHolder.endDateTextView.setText("End Date: " + detail.getEnddate());
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
