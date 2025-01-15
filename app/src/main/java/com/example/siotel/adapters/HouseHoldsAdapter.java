package com.example.siotel.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.siotel.activity.RecActivity;
import com.example.siotel.activity.RechargeActivity;
import com.example.siotel.fragment.HouseholdsDetailsFragment;
import com.example.siotel.fragment.RechargeFragment;
import com.example.siotel.interfaces.HouseHoldId;
import com.example.siotel.models.HhModel;
import com.example.siotel.models.HouseholdsModel;

import java.util.List;

public class HouseHoldsAdapter extends RecyclerView.Adapter<HouseHoldsAdapter.DevicesViewHolder>  {


    Context context;
    List<HouseholdsModel> arrayList;



    public HouseHoldsAdapter(Context context, List<HouseholdsModel> arrayList) {
        this.context=context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public HouseHoldsAdapter.DevicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_devices_rv,parent,false);
        DevicesViewHolder devicesViewHolder=new DevicesViewHolder(view);
        return  devicesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HouseHoldsAdapter.DevicesViewHolder holder, int position) {
        HouseholdsModel devicesModel=arrayList.get(position);
        holder.dhn.setText(devicesModel.getHouseholdname().toUpperCase());
        holder.mn.setText(devicesModel.getMetersno());
        String date=devicesModel.getDate();
        String []arr=date.split("T");
        holder.date.setText(arr[0]);
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(),"meter no"+devicesModel.getMetersno().toString(),Toast.LENGTH_SHORT).show();
               //  recyclerViewInterface.onItemClick(arrayList.get(position));
//                Bundle bundle=new Bundle();
//                transection.replace(R.id.fragmentContainerView, new MeterDetailsFragment());
//                transection.commit();

                AppCompatActivity  activity =(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new HouseholdsDetailsFragment(devicesModel.getMetersno().toString()))
                        .addToBackStack(null).commit();
            }
        });

        holder.recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity =(AppCompatActivity)view.getContext();
//               activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new RechargeFragment(devicesModel.getMetersno()))
//                      .addToBackStack(null).commit();
                Intent intent=new Intent(activity, RechargeActivity.class);
                intent.putExtra("Hid",devicesModel.getMetersno());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class DevicesViewHolder extends RecyclerView.ViewHolder {

        TextView dhn,mn,date;
        Button details,recharge;
        public DevicesViewHolder(@NonNull View itemView) {
            super(itemView);
            dhn = itemView.findViewById(R.id.householdname);
            mn = itemView.findViewById(R.id.metersno);
            date = itemView.findViewById(R.id.dt);
            details=itemView.findViewById(R.id.household_details_but);
            recharge=itemView.findViewById(R.id.household_recharge_but);


        }
    }
}
