package com.example.siotel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siotel.R;
import com.example.siotel.fragment.HouseholdsDetailsFragment;
import com.example.siotel.fragment.RechargHistoryDetailsFragment;
import com.example.siotel.models.HouseholdsModel;

import java.util.List;

public class RechargeHistoryFragHousHoldsAdapter extends RecyclerView.Adapter<RechargeHistoryFragHousHoldsAdapter.DevicesViewHolder> {

    Context context;
    List<HouseholdsModel> arrayList;

    public RechargeHistoryFragHousHoldsAdapter(Context context, List<HouseholdsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RechargeHistoryFragHousHoldsAdapter.DevicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_households_recharge_history,parent,false);
      return  new DevicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RechargeHistoryFragHousHoldsAdapter.DevicesViewHolder holder, int position) {
        HouseholdsModel devicesModel=arrayList.get(position);

        holder.dhn.setText(devicesModel.getHouseholdname().toUpperCase());
        holder.mn.setText(devicesModel.getMetersno());
        holder.rechargeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(),"meter no"+devicesModel.getMetersno().toString(),Toast.LENGTH_SHORT).show();
                //  recyclerViewInterface.onItemClick(arrayList.get(position));
//                Bundle bundle=new Bundle();
//                transection.replace(R.id.fragmentContainerView, new MeterDetailsFragment());
//                transection.commit();

            /*    AppCompatActivity activity =(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new RechargHistoryDetailsFragment(devicesModel.getMetersno().toString()))
                        .addToBackStack(null).commit(); */
                AppCompatActivity  activity =(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new RechargHistoryDetailsFragment(devicesModel.getMetersno().toString()))
                        .addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DevicesViewHolder extends RecyclerView.ViewHolder {

        TextView dhn,mn;
        Button rechargeHistory;
        public DevicesViewHolder(@NonNull View itemView) {
            super(itemView);

            dhn = itemView.findViewById(R.id.householdname);
            mn = itemView.findViewById(R.id.metersno);
            rechargeHistory = itemView.findViewById(R.id.re_hi_bu);

        }
    }
}
