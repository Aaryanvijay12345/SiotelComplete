package com.example.siotel.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.ConsumerMeterInformationModel;
import com.example.siotel.models.HouseholdsDetailsModel;
import com.example.siotel.models.StringToDoubleStorage;

import java.util.ArrayList;
import java.util.List;

public class ConsumerMeterInfromationAdapter extends RecyclerView.Adapter<ConsumerMeterInfromationAdapter.MDViewHolder> {



    Context context;
    List<ConsumerMeterInformationModel> arrayList;

    public ConsumerMeterInfromationAdapter(List<ConsumerMeterInformationModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ConsumerMeterInfromationAdapter.MDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_household_details_rv1,parent,false);
        //DevicesAdapter.DevicesViewHolder devicesViewHolder=new DevicesAdapter.DevicesViewHolder(view);
        MDViewHolder mdViewHolder=new MDViewHolder(view);
        return  mdViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MDViewHolder holder, int position) {
        if (position == 0) {
            // Header row
            holder.metersn.setText("Meter Sno.");
            holder.balance_amount.setText("Amount"); // Adjusted to match query
            holder.cum_eb_kwh.setText("Cum_eb_kwh");
            holder.date.setText("Date");
        } else {
            // Data rows, offset by 1
            ConsumerMeterInformationModel meterDetails = arrayList.get(position - 1);
            holder.metersn.setText(meterDetails.getMeterSN() != null ? meterDetails.getMeterSN() : "");
            holder.balance_amount.setText(String.valueOf(meterDetails.getBalance_amount()));
            holder.cum_eb_kwh.setText(String.valueOf(meterDetails.getCum_eb_kwh()));
            String dateStr = meterDetails.getDate();
            if (dateStr != null && dateStr.contains("T")) {
                String[] arr = dateStr.split("T");
                holder.date.setText(arr[0] + " " + arr[1]);
            } else {
                holder.date.setText(dateStr != null ? dateStr : "");
            }
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size()+1 ; // Extra row for the empty first row
    }
    public class MDViewHolder extends RecyclerView.ViewHolder {
        TextView metersn, balance_amount, cum_eb_kwh, date;

        public MDViewHolder(@NonNull View itemView) {
            super(itemView);
            metersn = itemView.findViewById(R.id.meter_sn);
            balance_amount = itemView.findViewById(R.id.balance_amount);
            cum_eb_kwh = itemView.findViewById(R.id.cum_eb_kwh);
            date = itemView.findViewById(R.id.date_time);
        }
    }



    public void updateData(List<ConsumerMeterInformationModel> newData) {
        arrayList.clear();
        if (newData != null) {
            arrayList.addAll(newData);
        }
        notifyDataSetChanged();
    }

}