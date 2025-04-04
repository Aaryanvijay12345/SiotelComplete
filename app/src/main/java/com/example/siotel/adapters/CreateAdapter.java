package com.example.siotel.adapters;//package com.example.siotel.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.siotel.R;
//import com.example.siotel.models.CreateModel;
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {
//    private List<CreateModel> createModelList;
//
//    public CreateAdapter(List<CreateModel> createModelList) {
//        this.createModelList = createModelList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CreateModel model = createModelList.get(position);
//
//        holder.sno.setText(model.getSno());
//        holder.hname.setText(model.getHname());
//        holder.uname.setText(model.getUname());
//        holder.cemail.setText(model.getCemail());
//        holder.caddress.setText(model.getCaddress());
//        holder.open_kwheb.setText(String.valueOf(model.getOpen_kwheb()));
//        holder.close_kwheb.setText(String.valueOf(model.getClose_kwheb()));
//        holder.con_kwheb.setText(String.valueOf(model.getCon_kwheb()));
//        holder.open_kwhdg.setText(String.valueOf(model.getOpen_kwhdg()));
//        holder.close_kwhdg.setText(String.valueOf(model.getClose_kwhdg()));
//        holder.con_kwhdg.setText(String.valueOf(model.getCon_kwhdg()));
//        holder.ebt.setText(String.valueOf(model.getEbt()));
//        holder.dgt.setText(String.valueOf(model.getDgt()));
//        holder.mc.setText(String.valueOf(model.getMc()));
//        holder.startdate.setText(model.getStartdate());
//        holder.enddate.setText(model.getEnddate());
//        holder.open_amount.setText(String.valueOf(model.getOpen_amount()));
//        holder.close_amount.setText(String.valueOf(model.getClose_amount()));
//        holder.actday.setText(String.valueOf(model.getActday()));
//        holder.Netamount.setText(String.valueOf(model.getNetamount()));
//        holder.sitename.setText(model.getSitename());
//        holder.current_date.setText(model.getCurrent_date());
//        holder.invoice_due.setText(model.getInvoice_due());
//    }
//
//    @Override
//    public int getItemCount() {
//        return createModelList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView sno, hname, uname, cemail, caddress, open_kwheb, close_kwheb, con_kwheb,
//                open_kwhdg, close_kwhdg, con_kwhdg, ebt, dgt, mc, startdate, enddate,
//                open_amount, close_amount, actday, Netamount, sitename, current_date, invoice_due;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            sno = itemView.findViewById(R.id.text_sno);
//            hname = itemView.findViewById(R.id.text_hname);
//            uname = itemView.findViewById(R.id.text_uname);
//            cemail = itemView.findViewById(R.id.text_cemail);
//            caddress = itemView.findViewById(R.id.text_caddress);
//            open_kwheb = itemView.findViewById(R.id.text_open_kwheb);
//            close_kwheb = itemView.findViewById(R.id.text_close_kwheb);
//            con_kwheb = itemView.findViewById(R.id.text_con_kwheb);
//            open_kwhdg = itemView.findViewById(R.id.text_open_kwhdg);
//            close_kwhdg = itemView.findViewById(R.id.text_close_kwhdg);
//            con_kwhdg = itemView.findViewById(R.id.text_con_kwhdg);
//            ebt = itemView.findViewById(R.id.text_ebt);
//            dgt = itemView.findViewById(R.id.text_dgt);
//            mc = itemView.findViewById(R.id.text_mc);
//            startdate = itemView.findViewById(R.id.text_startdate);
//            enddate = itemView.findViewById(R.id.text_enddate);
//            open_amount = itemView.findViewById(R.id.text_open_amount);
//            close_amount = itemView.findViewById(R.id.text_close_amount);
//            actday = itemView.findViewById(R.id.text_actday);
//            Netamount = itemView.findViewById(R.id.text_Netamount);
//            sitename = itemView.findViewById(R.id.text_sitename);
//            current_date = itemView.findViewById(R.id.text_current_date);
//            invoice_due = itemView.findViewById(R.id.text_invoice_due);
//        }
//    }
//
//}


//package com.example.siotel.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.siotel.R;
//import com.example.siotel.models.CreateModel;
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {
//    private List<CreateModel> createModelList;
//
//    public CreateAdapter(List<CreateModel> createModelList) {
//        this.createModelList = createModelList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CreateModel model = createModelList.get(position);
//
//        holder.sno.setText(model.getSno());
//        holder.hname.setText(model.getHname());
//        holder.uname.setText(model.getUname());
//        holder.cemail.setText(model.getCemail());
//        holder.caddress.setText(model.getCaddress());
//        holder.open_kwheb.setText(String.valueOf(model.getEb_kwh_open()));
//        holder.close_kwheb.setText(String.valueOf(model.getEb_kwh_close()));
//        holder.con_kwheb.setText(String.valueOf(model.getCon_eb_kwh()));
//        holder.open_kwhdg.setText(String.valueOf(model.getDg_kwh_open()));
//        holder.close_kwhdg.setText(String.valueOf(model.getDg_kwh_close()));
//        holder.con_kwhdg.setText(String.valueOf(model.getCon_dg_kwh()));
//        holder.ebt.setText(String.valueOf(model.getEb_tf()));
//        holder.dgt.setText(String.valueOf(model.getDg_tf()));
//        holder.mc.setText(String.valueOf(model.getDc_tf()));
//        holder.startdate.setText(model.getStartdate());
//        holder.enddate.setText(model.getEnddate());
//        holder.open_amount.setText(String.valueOf(model.getAmount_open()));
//        holder.close_amount.setText(String.valueOf(model.getAmount_close()));
//        holder.actday.setText(String.valueOf(model.getActivate_days()));
//        holder.netamount.setText(String.valueOf(model.getNet_amount()));
//        holder.sitename.setText(model.getSitename());
//        holder.current_date.setText(model.getCurrentDate());
//        holder.invoice_due.setText(model.getInvoiceDue());
//    }
//
//    @Override
//    public int getItemCount() {
//        return createModelList.size();
//    }
//
//    public void updateData(List<CreateModel> newData) {
//        createModelList.clear();
//        createModelList.addAll(newData);
//        notifyDataSetChanged();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView sno, hname, uname, cemail, caddress, open_kwheb, close_kwheb, con_kwheb,
//                open_kwhdg, close_kwhdg, con_kwhdg, ebt, dgt, mc, startdate, enddate,
//                open_amount, close_amount, actday, netamount, sitename, current_date, invoice_due;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            sno = itemView.findViewById(R.id.text_sno);
//            hname = itemView.findViewById(R.id.text_hname);
//            uname = itemView.findViewById(R.id.text_uname);
//            cemail = itemView.findViewById(R.id.text_cemail);
//            caddress = itemView.findViewById(R.id.text_caddress);
//            open_kwheb = itemView.findViewById(R.id.text_open_kwheb);
//            close_kwheb = itemView.findViewById(R.id.text_close_kwheb);
//            con_kwheb = itemView.findViewById(R.id.text_con_kwheb);
//            open_kwhdg = itemView.findViewById(R.id.text_open_kwhdg);
//            close_kwhdg = itemView.findViewById(R.id.text_close_kwhdg);
//            con_kwhdg = itemView.findViewById(R.id.text_con_kwhdg);
//            ebt = itemView.findViewById(R.id.text_ebt);
//            dgt = itemView.findViewById(R.id.text_dgt);
//            mc = itemView.findViewById(R.id.text_mc);
//            startdate = itemView.findViewById(R.id.text_startdate);
//            enddate = itemView.findViewById(R.id.text_enddate);
//            open_amount = itemView.findViewById(R.id.text_open_amount);
//            close_amount = itemView.findViewById(R.id.text_close_amount);
//            actday = itemView.findViewById(R.id.text_actday);
//            netamount = itemView.findViewById(R.id.text_netamount);
//            sitename = itemView.findViewById(R.id.text_sitename);
//            current_date = itemView.findViewById(R.id.text_current_date);
//            invoice_due = itemView.findViewById(R.id.text_invoice_due);
//        }
//    }
//}


//package com.example.siotel.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.siotel.R;
//import com.example.siotel.models.CreateModel;
//
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {
//
//    private List<CreateModel> createList;
//
//    public CreateAdapter(List<CreateModel> createList) {
//        this.createList = createList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CreateModel model = createList.get(position);
//        holder.textViewSno.setText("SNO: " + model.getSno());
//        holder.textViewHname.setText("House Name: " + model.getHname());
//        holder.textViewNetAmount.setText("Net Amount: " + String.format("%.2f", model.getNetamount()));
//        holder.textViewStartDate.setText("Start Date: " + model.getStartdate());
//        holder.textViewEndDate.setText("End Date: " + model.getEnddate());
//    }
//
//    @Override
//    public int getItemCount() {
//        return createList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewSno, textViewHname, textViewNetAmount, textViewStartDate, textViewEndDate;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewSno = itemView.findViewById(R.id.textViewSno);
//            textViewHname = itemView.findViewById(R.id.textViewHname);
//            textViewNetAmount = itemView.findViewById(R.id.textViewNetAmount);
//            textViewStartDate = itemView.findViewById(R.id.textViewStartDate);
//            textViewEndDate = itemView.findViewById(R.id.textViewEndDate);
//        }
//    }
//}



//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.siotel.R;
//import com.example.siotel.models.InvoiceResponse;
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.InvoiceViewHolder> {
//
//    private List<InvoiceResponse> invoiceList;
//
//    public CreateAdapter(List<InvoiceResponse> invoiceList) {
//        this.invoiceList = invoiceList;
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
//        InvoiceResponse invoice = invoiceList.get(position);
//
//        // Binding data to UI elements
//        holder.textViewInvoiceNumber.setText(invoice.getSno()); // Serial number
//        holder.textViewTotalAmount.setText(String.format("%.2f", invoice.getNetamount())); // Net amount
//
//        // Additional details binding
//        holder.textViewSiteName.setText(invoice.getSitename()); // Site name
//        holder.textViewStartDate.setText(invoice.getStartdate()); // Start date
//        holder.textViewEndDate.setText(invoice.getEnddate()); // End date
//        holder.textViewInvoiceDue.setText(invoice.getInvoice_due()); // Due date
//        holder.textViewCurrentDate.setText(invoice.getCurrent_date()); // Current date
//        holder.textViewUserName.setText(invoice.getUname()); // User name
//        holder.textViewEmail.setText(invoice.getCemail()); // Email
//        holder.textViewAddress.setText(invoice.getCaddress()); // Address
//    }
//
//    @Override
//    public int getItemCount() {
//        return invoiceList.size();
//    }
//
//    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewInvoiceNumber, textViewTotalAmount, textViewSiteName, textViewStartDate,
//                textViewEndDate, textViewInvoiceDue, textViewCurrentDate, textViewUserName,
//                textViewEmail, textViewAddress;
//
//        public InvoiceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewInvoiceNumber = itemView.findViewById(R.id.textViewInvoiceNumber);
//            textViewTotalAmount = itemView.findViewById(R.id.textViewTotalAmount);
//            textViewSiteName = itemView.findViewById(R.id.textViewSiteName);
//            textViewStartDate = itemView.findViewById(R.id.textViewStartDate);
//            textViewEndDate = itemView.findViewById(R.id.textViewEndDate);
//            textViewInvoiceDue = itemView.findViewById(R.id.textViewInvoiceDue);
//            textViewCurrentDate = itemView.findViewById(R.id.textViewCurrentDate);
//            textViewUserName = itemView.findViewById(R.id.textViewUserName);
//            textViewEmail = itemView.findViewById(R.id.textViewEmail);
//            textViewAddress = itemView.findViewById(R.id.textViewAddress);
//        }
//    }
//}




//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.siotel.R;
//import com.example.siotel.models.InvoiceResponse;
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.InvoiceViewHolder> {
//
//    private List<InvoiceResponse> invoiceList;
//
//    public CreateAdapter(List<InvoiceResponse> invoiceList) {
//        this.invoiceList = invoiceList;
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
//        InvoiceResponse invoice = invoiceList.get(position);
//        holder.meterSnoTextView.setText("Meter SNO: " + invoice.getSno());
//        holder.startDateTextView.setText("Start Date: " + invoice.getStartdate());
//        holder.endDateTextView.setText("End Date: " + invoice.getEnddate());
//        holder.netAmountTextView.setText("Net Amount: " + String.format("%.2f", invoice.getNetamount()));
//        // Add more fields as needed based on InvoiceResponse structure
//    }
//
//    @Override
//    public int getItemCount() {
//        return invoiceList.size();
//    }
//
//    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
//        TextView meterSnoTextView, startDateTextView, endDateTextView, netAmountTextView;
//
//        public InvoiceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            meterSnoTextView = itemView.findViewById(R.id.text_meter_sno);
//            startDateTextView = itemView.findViewById(R.id.text_start_date);
//            endDateTextView = itemView.findViewById(R.id.text_end_date);
//            netAmountTextView = itemView.findViewById(R.id.text_net_amount);
//        }
//    }
//}

//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.siotel.R;
//import com.example.siotel.models.InvoiceResponse;
//import java.util.List;
//
//public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.InvoiceViewHolder> {
//
//    private List<InvoiceResponse> invoiceList;
//
//    public CreateAdapter(List<InvoiceResponse> invoiceList) {
//        this.invoiceList = invoiceList;
//    }
//
//    @NonNull
//    @Override
//    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create, parent, false);
//        return new InvoiceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
//        InvoiceResponse invoice = invoiceList.get(position);
//
//        // Handle null values safely
//        holder.meterSnoTextView.setText("Meter SNO: " + (invoice.getSno() != null ? invoice.getSno() : "N/A"));
//        holder.startDateTextView.setText("Start Date: " + (invoice.getStartdate() != null ? invoice.getStartdate() : "N/A"));
//        holder.endDateTextView.setText("End Date: " + (invoice.getEnddate() != null ? invoice.getEnddate() : "N/A"));
//        holder.netAmountTextView.setText("Net Amount: " + String.format("%.2f", invoice.getNetamount() != 0 ? invoice.getNetamount() : 0.00));
//    }
//
//    @Override
//    public int getItemCount() {
//        return invoiceList.size();
//    }
//
//    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
//        TextView meterSnoTextView, startDateTextView, endDateTextView, netAmountTextView;
//
//        public InvoiceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            meterSnoTextView = itemView.findViewById(R.id.text_meter_sno);
//            startDateTextView = itemView.findViewById(R.id.text_start_date);
//            endDateTextView = itemView.findViewById(R.id.text_end_date);
//            netAmountTextView = itemView.findViewById(R.id.text_net_amount);
//        }
//    }
//}

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siotel.R;
import com.example.siotel.models.InvoiceResponse;
import java.util.List;

public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.InvoiceViewHolder> {

    private List<InvoiceResponse> invoiceList;

    public CreateAdapter(List<InvoiceResponse> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        InvoiceResponse invoice = invoiceList.get(position);

        holder.meterSno.setText(invoice.getSno());
        holder.openingEBKwh.setText(String.valueOf(invoice.getOpen_kwheb()));
        holder.closingEBKwh.setText(String.valueOf(invoice.getClose_kwheb()));
        holder.ebConsumption.setText(String.valueOf(invoice.getCon_kwheb()));
        holder.openingDG.setText(String.valueOf(invoice.getOpen_kwhdg()));
        holder.closingDG.setText(String.valueOf(invoice.getClose_kwhdg()));
        holder.dgConsumption.setText(String.valueOf(invoice.getCon_kwhdg()));
        holder.ebTariff.setText(String.valueOf(invoice.getEbt()));
        holder.dgTariff.setText(String.valueOf(invoice.getDgt()));
        holder.activeDays.setText(String.valueOf(invoice.getActday()));
        holder.netAmount.setText(String.format("%.2f", invoice.getNetamount()));
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView meterSno, openingEBKwh, closingEBKwh, ebConsumption, openingDG, closingDG, dgConsumption,
                ebTariff, dgTariff, dailyCharge, activeDays, netAmount;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            meterSno = itemView.findViewById(R.id.text_meter_sno);
            openingEBKwh = itemView.findViewById(R.id.text_opening_ebkwh);
            closingEBKwh = itemView.findViewById(R.id.text_closing_ebkwh);
            ebConsumption = itemView.findViewById(R.id.text_eb_consumption);
            openingDG = itemView.findViewById(R.id.text_opening_dg);
            closingDG = itemView.findViewById(R.id.text_closing_dg);
            dgConsumption = itemView.findViewById(R.id.text_dg_consumption);
            ebTariff = itemView.findViewById(R.id.text_eb_tariff);
            dgTariff = itemView.findViewById(R.id.text_dg_tariff);
            dailyCharge = itemView.findViewById(R.id.text_daily_charge);
            activeDays = itemView.findViewById(R.id.text_active_days);
            netAmount = itemView.findViewById(R.id.text_net_amount);
        }
    }
}
