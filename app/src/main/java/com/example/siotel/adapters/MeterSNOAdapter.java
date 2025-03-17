package com.example.siotel.adapters;



import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class MeterSNOAdapter extends ArrayAdapter<String> {
    private List<String> originalList;

    public MeterSNOAdapter(Context context, List<String> list) {
        super(context, android.R.layout.simple_dropdown_item_1line, list);
        originalList = new ArrayList<>(list); // Keep a copy of the full list
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<String> suggestions = new ArrayList<>();

                // If no input, show all items
                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(originalList);
                } else {
                    // Filter items that contain the typed string (case-insensitive)
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (String item : originalList) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear(); // Clear the adapter's current data
                if (results.count > 0) {
                    addAll((List<String>) results.values); // Add filtered items
                }
                notifyDataSetChanged(); // Notify the adapter to update the dropdown
            }
        };
    }
}