package com.example.siotel.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.models.RechargeHistoryItem;

import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RechargeHistoryItem> dataList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RechargeHistoryAdapter(List<RechargeHistoryItem> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<RechargeHistoryItem> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_history, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_history, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            // Set header titles
            headerHolder.textViewHouse.setText("House");
            headerHolder.textViewDevid.setText("Device ID");
            headerHolder.textViewDate.setText("Date");
            headerHolder.textViewAmount.setText("Amount");

            // Set background color for the header
            headerHolder.textViewHouse.setBackgroundColor(Color.argb(40, 40, 40, 40));
            headerHolder.textViewDevid.setBackgroundColor(Color.argb(40, 40, 40, 40));
            headerHolder.textViewDate.setBackgroundColor(Color.argb(40, 40, 40, 40));
            headerHolder.textViewAmount.setBackgroundColor(Color.argb(40, 40, 40, 40));
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            // Adjust position by -1 since header takes position 0
            RechargeHistoryItem item = dataList.get(position - 1);
            itemHolder.textViewHouse.setText(item.getHouse());
            itemHolder.textViewDevid.setText(item.getDevid());
            itemHolder.textViewDate.setText(item.getDate());
            itemHolder.textViewAmount.setText(item.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        // Add 1 to account for the header
        return dataList.size() + 1;
    }

    // Header ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHouse, textViewDevid, textViewDate, textViewAmount;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textViewHouse = itemView.findViewById(R.id.textViewHouse);
            textViewDevid = itemView.findViewById(R.id.textViewDevid);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }

    // Item ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHouse, textViewDevid, textViewDate, textViewAmount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewHouse = itemView.findViewById(R.id.textViewHouse);
            textViewDevid = itemView.findViewById(R.id.textViewDevid);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }
}
