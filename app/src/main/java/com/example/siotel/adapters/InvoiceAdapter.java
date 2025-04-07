package com.example.siotel.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.Invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private List<Invoice> invoiceList;
    private OnViewClickListener onViewClickListener;
    private OnDownloadClickListener onDownloadClickListener;

    private Context context;

    public interface OnViewClickListener {
        void onViewClick(Invoice invoice);
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(Invoice invoice);
    }

    public InvoiceAdapter(Context context, List<Invoice> invoiceList,
                          OnViewClickListener onViewClickListener,
                          OnDownloadClickListener onDownloadClickListener) {
        this.context = context;
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
            // Header row styling
            holder.invoiceIdTextView.setBackgroundColor(Color.LTGRAY);
            holder.customerTextView.setBackgroundColor(Color.LTGRAY);
            holder.billingAddressTextView.setBackgroundColor(Color.LTGRAY);
            holder.dateTextView.setBackgroundColor(Color.LTGRAY);
            holder.dueDateTextView.setBackgroundColor(Color.LTGRAY);
            holder.totalAmountTextView.setBackgroundColor(Color.LTGRAY);
            holder.statusTextView.setBackgroundColor(Color.LTGRAY);

            holder.invoiceIdTextView.setText("Invoice ID");
            holder.customerTextView.setText("Customer");
            holder.billingAddressTextView.setText("Billing Address");
            holder.dateTextView.setText("Invoice Date");
            holder.dueDateTextView.setText("Due Date");
            holder.totalAmountTextView.setText("Amount");
            holder.statusTextView.setText("Status");

            // Hide buttons in header
            holder.viewButton.setVisibility(View.GONE);
            holder.downloadButton.setVisibility(View.GONE);

        } else {
            Invoice invoice = invoiceList.get(position - 1);

            holder.invoiceIdTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.customerTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.billingAddressTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.dateTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.dueDateTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.totalAmountTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.statusTextView.setBackgroundColor(Color.TRANSPARENT);

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

            // Show buttons for data rows
            holder.viewButton.setVisibility(View.VISIBLE);
            holder.downloadButton.setVisibility(View.VISIBLE);

            holder.viewButton.setOnClickListener(v -> {
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(invoice);
                }
            });

            holder.downloadButton.setOnClickListener(v -> {
                if (invoice != null) {
                    generateInvoicePdf(invoice);
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
                dueDateTextView, totalAmountTextView, statusTextView;
        Button viewButton, downloadButton;

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
            downloadButton = itemView.findViewById(R.id.button_download);
        }
    }

    private void generateInvoicePdf(Invoice invoice) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        paint.setTextSize(12f);
        paint.setColor(Color.BLACK);

        int y = 25;
        canvas.drawText("Invoice ID: #" + invoice.getId(), 10, y, paint); y += 20;
        canvas.drawText("Customer: " + invoice.getCustomer(), 10, y, paint); y += 20;
        canvas.drawText("Billing Address: " + invoice.getBillingAddress(), 10, y, paint); y += 20;
        canvas.drawText("Invoice Date: " + invoice.getDate(), 10, y, paint); y += 20;
        canvas.drawText("Due Date: " + invoice.getDueDate(), 10, y, paint); y += 20;
        canvas.drawText("Total Amount: ₹" + String.format("%.2f", invoice.getTotalAmount()), 10, y, paint); y += 20;
        canvas.drawText("Status: " + (invoice.isStatus() ? "Paid" : "Unpaid"), 10, y, paint); y += 20;

        pdfDocument.finishPage(page);

        // Save to file
        String fileName = "Invoice_" + invoice.getId() + ".pdf";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);


        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "Invoice downloaded", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Download failed!", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }

}


//
//package com.example.siotel.adapters;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.pdf.PdfDocument;
//import android.os.Environment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.siotel.R;
//import com.example.siotel.models.Invoice;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
//
//    private List<Invoice> invoiceList;
//    private OnViewClickListener onViewClickListener;
//    private Context context;
//
//    private OnDownloadClickListener onDownloadClickListener;
//
//    public interface OnViewClickListener {
//        void onViewClick(Invoice invoice);
//    }
//
//    public interface OnDownloadClickListener {
//        void onDownloadClick(Invoice invoice);
//    }
//
//
//
//
//    public InvoiceAdapter(Context context, List<Invoice> invoiceList,
//                          OnViewClickListener onViewClickListener,
//                          OnDownloadClickListener onDownloadClickListener) {
//        this.context = context;
//        this.invoiceList = invoiceList;
//        this.onViewClickListener = onViewClickListener;
//        this.onDownloadClickListener = onDownloadClickListener;
//    }
//
//    @NonNull
//    @Override
//    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
//        return new InvoiceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
//        if (position == 0) {
//            holder.invoiceIdTextView.setBackgroundColor(Color.LTGRAY);
//            holder.customerTextView.setBackgroundColor(Color.LTGRAY);
//            holder.billingAddressTextView.setBackgroundColor(Color.LTGRAY);
//            holder.dateTextView.setBackgroundColor(Color.LTGRAY);
//            holder.dueDateTextView.setBackgroundColor(Color.LTGRAY);
//            holder.totalAmountTextView.setBackgroundColor(Color.LTGRAY);
//            holder.statusTextView.setBackgroundColor(Color.LTGRAY);
//
//            holder.invoiceIdTextView.setText("Invoice ID");
//            holder.customerTextView.setText("Customer");
//            holder.billingAddressTextView.setText("Billing Address");
//            holder.dateTextView.setText("Invoice Date");
//            holder.dueDateTextView.setText("Due Date");
//            holder.totalAmountTextView.setText("Amount");
//            holder.statusTextView.setText("Status");
//
//            holder.viewButton.setVisibility(View.GONE);
//            holder.downloadButton.setVisibility(View.GONE);
//        } else {
//            Invoice invoice = invoiceList.get(position - 1);
//
//            holder.invoiceIdTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.customerTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.billingAddressTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.dateTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.dueDateTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.totalAmountTextView.setBackgroundColor(Color.TRANSPARENT);
//            holder.statusTextView.setBackgroundColor(Color.TRANSPARENT);
//
//            holder.invoiceIdTextView.setText("#" + invoice.getId());
//            holder.customerTextView.setText(invoice.getCustomer());
//            holder.billingAddressTextView.setText(invoice.getBillingAddress());
//            holder.dateTextView.setText(invoice.getDate());
//            holder.dueDateTextView.setText(invoice.getDueDate());
//            holder.totalAmountTextView.setText(String.format("%.2f", invoice.getTotalAmount()));
//
//            if (invoice.isStatus()) {
//                holder.statusTextView.setText("Paid");
//                holder.statusTextView.setTextColor(Color.GREEN);
//            } else {
//                holder.statusTextView.setText("PAY");
//                holder.statusTextView.setTextColor(Color.parseColor("#FFD700"));
//            }
//
//            holder.viewButton.setVisibility(View.VISIBLE);
//            holder.downloadButton.setVisibility(View.VISIBLE);
//
//            holder.viewButton.setOnClickListener(v -> {
//                if (onViewClickListener != null) {
//                    onViewClickListener.onViewClick(invoice);
//                }
//            });
//
//            holder.downloadButton.setOnClickListener(v -> generatePdf(invoice));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return invoiceList != null ? invoiceList.size() + 1 : 1;
//    }
//
//    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
//        TextView invoiceIdTextView, customerTextView, billingAddressTextView, dateTextView,
//                dueDateTextView, totalAmountTextView, statusTextView;
//        Button viewButton, downloadButton;
//
//        InvoiceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            invoiceIdTextView = itemView.findViewById(R.id.text_invoice_id);
//            customerTextView = itemView.findViewById(R.id.text_customer);
//            billingAddressTextView = itemView.findViewById(R.id.text_billing_address);
//            dateTextView = itemView.findViewById(R.id.text_date);
//            dueDateTextView = itemView.findViewById(R.id.text_due_date);
//            totalAmountTextView = itemView.findViewById(R.id.text_total_amount);
//            statusTextView = itemView.findViewById(R.id.text_status);
//            viewButton = itemView.findViewById(R.id.button_view);
//            downloadButton = itemView.findViewById(R.id.button_download);
//        }
//    }
//
//    private void generatePdf(Invoice invoice) {
//        PdfDocument pdfDocument = new PdfDocument();
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 600, 1).create();
//        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(14);
//
//        int y = 50;
//        canvas.drawText("Invoice #" + invoice.getId(), 20, y, paint); y += 25;
//        canvas.drawText("Customer: " + invoice.getCustomer(), 20, y, paint); y += 25;
//        canvas.drawText("Billing Address: " + invoice.getBillingAddress(), 20, y, paint); y += 25;
//        canvas.drawText("Invoice Date: " + invoice.getDate(), 20, y, paint); y += 25;
//        canvas.drawText("Due Date: " + invoice.getDueDate(), 20, y, paint); y += 25;
//        canvas.drawText("Amount: ₹" + String.format("%.2f", invoice.getTotalAmount()), 20, y, paint); y += 25;
//        canvas.drawText("Status: " + (invoice.isStatus() ? "Paid" : "Pending"), 20, y, paint);
//
//        pdfDocument.finishPage(page);
//
//        String fileName = "Invoice_" + invoice.getId() + ".pdf";
//        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Invoices");
//
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        File file = new File(directory, fileName);
//
//        try {
//            pdfDocument.writeTo(new FileOutputStream(file));
//            Toast.makeText(context, "PDF saved to Downloads/Invoices", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        pdfDocument.close();
//    }
//}
