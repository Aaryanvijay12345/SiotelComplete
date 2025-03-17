package com.example.siotel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.ReportResponse;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    private List<ReportResponse> reportList;

    public ReportAdapter(List<ReportResponse> reportList) {
        this.reportList = reportList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_household_details_rv2, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            // Set header titles
            holder.tvSno.setText("Meter Sno");
            holder.tvEbKwhOpen.setText("Opening EBKwh");
            holder.tvEbKwhClose.setText("Closing EBKwh");
            holder.tvConEbKwh.setText("EB Consumption");
            holder.tvDgKwhOpen.setText("OpeningDG");
            holder.tvDgKwhClose.setText("Closing DG");
            holder.tvConDgKwh.setText("DG Consumption");
            holder.tvEbTf.setText("EB Tarrif");
            holder.tvDgTf.setText("DG Tarrif");
            holder.tvDcTf.setText("Daily Charge");
            holder.tvAmountOpen.setText("Opening Amount");
            holder.tvAmountClose.setText("Closing Amount");
            holder.tvActivateDays.setText("Active Days");
            holder.tvNetAmount.setText("Net Amt");
            holder.tvTotalRecharge.setText("Total Recharge");
            holder.tvActualStartDate.setText("startDate");
            holder.tvActualEndDate.setText("endDate");
            holder.tvTotalCamAmount.setText("CAM Recharge");
            holder.tvTotalExceptCamAmount.setText("Electricity Recharge");
            holder.tvTotalAmount.setText("Total Amt.");
        } else {
            // Bind data rows
            ReportResponse report = reportList.get(position - 1); // Offset by 1 for header
            holder.tvSno.setText(report.getSno() != null ? report.getSno() : "N/A");
            holder.tvEbKwhOpen.setText(String.valueOf(report.getEb_kwh_open()));
            holder.tvEbKwhClose.setText(String.valueOf(report.getEb_kwh_close()));
            holder.tvConEbKwh.setText(String.valueOf(report.getCon_eb_kwh()));
            holder.tvDgKwhOpen.setText(String.valueOf(report.getDg_kwh_open()));
            holder.tvDgKwhClose.setText(String.valueOf(report.getDg_kwh_close()));
            holder.tvConDgKwh.setText(String.valueOf(report.getCon_dg_kwh()));
            holder.tvEbTf.setText(String.valueOf(report.getEb_tf()));
            holder.tvDgTf.setText(String.valueOf(report.getDg_tf()));
            holder.tvDcTf.setText(String.valueOf(report.getDc_tf()));
            holder.tvAmountOpen.setText(String.valueOf(report.getAmount_open()));
            holder.tvAmountClose.setText(String.valueOf(report.getAmount_close()));
            holder.tvActivateDays.setText(String.valueOf(report.getActivate_days()));
            holder.tvNetAmount.setText(String.valueOf(report.getNet_amount()));
            holder.tvTotalRecharge.setText(String.valueOf(report.getTotal_Recharge()));
            holder.tvActualStartDate.setText(report.getActual_start_date() != null ? report.getActual_start_date() : "N/A");
            holder.tvActualEndDate.setText(report.getActual_end_date() != null ? report.getActual_end_date() : "N/A");
            holder.tvTotalCamAmount.setText(String.valueOf(report.getTotal_cam_amount()));
            holder.tvTotalExceptCamAmount.setText(String.valueOf(report.getTotal_except_cam_amount()));
            holder.tvTotalAmount.setText(String.valueOf(report.getTotal_amount()));
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size() + 1; // Add 1 for the header row
    }

    public void updateData(List<ReportResponse> newData) {
        reportList.clear();
        if (newData != null) {
            reportList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tvSno, tvEbKwhOpen, tvEbKwhClose, tvConEbKwh, tvDgKwhOpen, tvDgKwhClose,
                tvConDgKwh, tvEbTf, tvDgTf, tvDcTf, tvAmountOpen, tvAmountClose, tvActivateDays,
                tvNetAmount, tvTotalRecharge, tvActualStartDate, tvActualEndDate, tvTotalCamAmount,
                tvTotalExceptCamAmount, tvTotalAmount;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSno = itemView.findViewById(R.id.meter_sno);
            tvEbKwhOpen = itemView.findViewById(R.id.Eb_kwh_open);
            tvEbKwhClose = itemView.findViewById(R.id.Eb_kwh_close);
            tvConEbKwh = itemView.findViewById(R.id.con_eb_kwh);
            tvDgKwhOpen = itemView.findViewById(R.id.dg_kwh_open);
            tvDgKwhClose = itemView.findViewById(R.id.dg_kwh_close);
            tvConDgKwh = itemView.findViewById(R.id.con_dg_kwh);
            tvEbTf = itemView.findViewById(R.id.eb_tf);
            tvDgTf = itemView.findViewById(R.id.dg_tf);
            tvDcTf = itemView.findViewById(R.id.dc_tf);
            tvAmountOpen = itemView.findViewById(R.id.amount_open);
            tvAmountClose = itemView.findViewById(R.id.amount_close);
            tvActivateDays = itemView.findViewById(R.id.activate_days);
            tvNetAmount = itemView.findViewById(R.id.net_amount);
            tvTotalRecharge = itemView.findViewById(R.id.total_Recharge);
            tvActualStartDate = itemView.findViewById(R.id.actual_start_date);
            tvActualEndDate = itemView.findViewById(R.id.actual_end_date);
            tvTotalCamAmount = itemView.findViewById(R.id.total_cam_amount);
            tvTotalExceptCamAmount = itemView.findViewById(R.id.total_except_cam_amount);
            tvTotalAmount = itemView.findViewById(R.id.total_amount);
        }
    }
}