package com.indiansmarthub.ish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.CustomerAddressActivity;
import com.indiansmarthub.ish.model.CustomerAddress;
import com.indiansmarthub.ish.model.GeneralModel;
import com.indiansmarthub.ish.retrofit.NetworkClient;
import com.indiansmarthub.ish.retrofit.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddressAdapter extends RecyclerView.Adapter<CustomerAddressAdapter.MyViewHolder> {
    Context context;
    ArrayList<CustomerAddress> customerAddressArrayList;
    public CustomerAddressAdapter(Context context, ArrayList<CustomerAddress> customerAddressArrayList) {
        this.context = context;
        this.customerAddressArrayList = customerAddressArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_address, parent, false);
        return new CustomerAddressAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.mName.setText(customerAddressArrayList.get(position).getFirstname() + " " +customerAddressArrayList.get(position).getLastname());
        holder.mStreet.setText(customerAddressArrayList.get(position).getStreet());
        holder.mRegion.setText(customerAddressArrayList.get(position).getStreet());
        holder.mCityPstel.setText(customerAddressArrayList.get(position).getCity()+" "+customerAddressArrayList.get(position).getPostcode());
        holder.mCountry.setText(customerAddressArrayList.get(position).getCountryId());
        holder.mMobile.setText(customerAddressArrayList.get(position).getTelephone());



    }


    @Override
    public int getItemCount() {
        return customerAddressArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mStreet, mRegion,mCityPstel, mCountry, mMobile;
        ImageView mAddtessdelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mStreet = itemView.findViewById(R.id.street);
            mRegion = itemView.findViewById(R.id.region);
            mCityPstel = itemView.findViewById(R.id.city_postal);
            mCountry = itemView.findViewById(R.id.country);
            mMobile = itemView.findViewById(R.id.mobile);
            mAddtessdelete = itemView.findViewById(R.id.ivdeleteaddress);
        }
    }

}
