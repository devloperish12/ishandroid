package com.indiansmarthub.ish.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.indiansmarthub.ish.R;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.adapter.AccountAdapter;
import com.indiansmarthub.ish.javaclass.RecyclerItemClickListener;
import com.indiansmarthub.ish.pojo.AccountPojo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class Account extends Fragment {
    RecyclerView mRV_account1;
    View view;
    ArrayList<AccountPojo> arrayList = new ArrayList<>();
    private SharedPreferences prefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        prefManager = getActivity().getSharedPreferences("ISH", getActivity().MODE_PRIVATE);
        init();

        if (!prefManager.getString("cust_id", "").isEmpty()) {

            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.franchies), "My Franchise"));
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.wallet_account), "My Wallet"));
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.wishlist_red), "My Wishlist"));
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.order_account), "My Orders"));
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.accountinfo), "My Account Info"));
          //  arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.order_tracking), "Order Tracking"));
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.support), "Support"));


        } else {
            arrayList.add(new AccountPojo(getResources().getDrawable(R.drawable.user), "Login"));
        }



        for (int i=0; i<arrayList.size(); i++){
            Log.e("tag_arr", arrayList.get(i).getName());
        }

        callAdapter();

        return view;
    }

    private void init() {
        mRV_account1 = view.findViewById(R.id.rv_account1);
    }

    private void callAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        AccountAdapter accountAdapter = new AccountAdapter(getActivity(), arrayList);
        mRV_account1.setLayoutManager(linearLayoutManager);
        mRV_account1.setAdapter(accountAdapter);

        mRV_account1.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRV_account1 ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (position==0){
                            if (!prefManager.getString("cust_id", "").isEmpty()) {

                            Frenchies frenchies = new Frenchies();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.frame_container,frenchies);
                            transaction.commit();
                            }
                            else {

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                       else if (position==1){
                            WalletList walletList = new WalletList();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.frame_container,walletList);
                            transaction.commit();
                        }

                        else if (position==2){
                            WishList wishList = new WishList();
                            FragmentManager manager1 = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction1 = manager1.beginTransaction();
                            transaction1.replace(R.id.frame_container,wishList);
                            transaction1.commit();
                        }

                        else if (position==3){
                            OrdersHistory ordersHistory = new OrdersHistory();
                            FragmentManager manager2 = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction2 = manager2.beginTransaction();
                            transaction2.replace(R.id.frame_container,ordersHistory);
                            transaction2.commit();
                        }

                        else if (position==4){
                            AccountInfo accountInfo = new AccountInfo();
                            FragmentManager manager_ = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction_ = manager_.beginTransaction();
                            transaction_.replace(R.id.frame_container,accountInfo);
                            transaction_.commit();
                        }

                        /*else if (position==5){
                            OrderTracking orderTracking = new OrderTracking();
                            FragmentManager manager_t = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction_t = manager_t.beginTransaction();
                            transaction_t.replace(R.id.frame_container,orderTracking);
                            transaction_t.commit();
                        }*/

                        else if (position==5){
                            Support support = new Support();
                            FragmentManager manager_t = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction_t = manager_t.beginTransaction();
                            transaction_t.replace(R.id.frame_container,support);
                            transaction_t.commit();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
}
