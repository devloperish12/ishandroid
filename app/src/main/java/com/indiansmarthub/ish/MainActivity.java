package com.indiansmarthub.ish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indiansmarthub.ish.activity.LoginActivity;
import com.indiansmarthub.ish.activity.Splash;
import com.indiansmarthub.ish.fragments.Account;
import com.indiansmarthub.ish.fragments.Blogs;
import com.indiansmarthub.ish.fragments.Cart;
import com.indiansmarthub.ish.fragments.Home;
import com.indiansmarthub.ish.fragments.SearchFragment;
import com.indiansmarthub.ish.fragments.Support;
import com.indiansmarthub.ish.fragments.WishList;
import com.indiansmarthub.ish.javaclass.BottomMenuHelper;
import com.indiansmarthub.ish.model.Addtocart;
import com.indiansmarthub.ish.sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView mBottomNavigationView;
    private Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseHandler databaseHandler;
    SharedPreferences.Editor editor;
    private SharedPreferences prefManager = null;
    private String strcartflag = null;
    private String bottomicon;
   public boolean iscart=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        prefManager = this.getSharedPreferences("ISH", this.MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationIcon(null);
      //  Log.e("tag_custid", prefManager.getString("cust_id", ""));
        databaseHandler = new DatabaseHandler(MainActivity.this);
        // call home fragment

        Intent intent = getIntent();
        try {
            if (intent != null) {
                strcartflag = intent.getStringExtra("cartflag");
                bottomicon = intent.getStringExtra("bottomicon");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // bottom naviagtion bar
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // ArrayList<Addtocart> viewCarts;
        //viewCarts=databaseHandler.getCart();
       // int cartval=viewCarts.size();
        try {
            int cart=Integer.parseInt(intent.getStringExtra("cart"));
            int wishlist=Integer.parseInt(intent.getStringExtra("wish"));

            if (cart > 0) {
                BottomMenuHelper.showBadge(MainActivity.this, mBottomNavigationView, R.id.navigation_cart, "" + cart);
            }
            if(wishlist>0)
            {
                BottomMenuHelper.showBadge(MainActivity.this, mBottomNavigationView, R.id.navigation_wishList, "" + wishlist);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        if (strcartflag!= null && strcartflag.equalsIgnoreCase("cartflag")){
//            Cart cart = new Cart();
//            FragmentManager manager_ = getSupportFragmentManager();
//            FragmentTransaction transaction_ = manager_.beginTransaction();
//            transaction_.replace(R.id.frame_container,cart);
//            transaction_.commit();
//            try {
//                mBottomNavigationView.setSelectedItemId(R.id.navigation_cart);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
       /* else if (strcartflag!= null && strcartflag.equalsIgnoreCase("cartflag_dbcheck")){
            Cart cart = new Cart();
            FragmentManager manager_ = getSupportFragmentManager();
            FragmentTransaction transaction_ = manager_.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("cartflag", "cartflag_dbcheck");
            cart.setArguments(bundle);
            transaction_.replace(R.id.frame_container,cart);
            transaction_.commit();
        }*/
        try
        {
            iscart=intent.getBooleanExtra("iscart",false);
            if(iscart)
            {

                Cart cart = new Cart();
                FragmentManager manager2 = getSupportFragmentManager();
                FragmentTransaction transaction2 = manager2.beginTransaction();
                transaction2.replace(R.id.frame_container,cart);
                transaction2.commit();
                mBottomNavigationView.setSelectedItemId(R.id.navigation_cart);
            }

        else {
            Home home = new Home();
            FragmentManager manager_ = getSupportFragmentManager();
            FragmentTransaction transaction_ = manager_.beginTransaction();
            transaction_.replace(R.id.frame_container,home);
            transaction_.commit();
            try {
                mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            boolean iswish=intent.getBooleanExtra("isWish",false);
            if(iswish)
            {
                addTocart();
                WishList wishList = new WishList();
                FragmentManager manager1 = getSupportFragmentManager();
                FragmentTransaction transaction1 = manager1.beginTransaction();
                transaction1.replace(R.id.frame_container,wishList);
                transaction1.commit();
                mBottomNavigationView.setSelectedItemId(R.id.navigation_wishList);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();

            if (!prefManager.getString("cust_id", "").isEmpty()) {

                menu.findItem(R.id.action_logout).setVisible(true);
              //  menu.findItem(R.id.action_login).setVisible(false);



            } else {
                menu.findItem(R.id.action_logout).setVisible(false);
             //   menu.findItem(R.id.action_login).setVisible(true);

            }


        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Intent i= new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);
        }
        else if (id == R.id.action_logout){

            editor = prefManager.edit();
            editor.clear();
            editor.commit();


            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
       /* else if (id == R.id.action_login){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }*/




        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            // Handle the camera action
        }
        else if (id == R.id.nav_jagsun) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jagsun.in/"));
            startActivity(browserIntent);

        }
        else if (id == R.id.nav_blogs) {
            Blogs blogs = new Blogs();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container,blogs);
            transaction.commit();
        }
        else if (id == R.id.nav_support) {
            Support support = new Support();
            FragmentManager manager_s = getSupportFragmentManager();
            FragmentTransaction transaction_s = manager_s.beginTransaction();
            transaction_s.replace(R.id.frame_container,support);
            transaction_s.commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addTocart();
                    Home home = new Home();
                    FragmentManager manager_ = getSupportFragmentManager();
                    FragmentTransaction transaction_ = manager_.beginTransaction();
                    transaction_.replace(R.id.frame_container,home);
                    transaction_.commit();
                    return true;

                case R.id.navigation_search:
                    SearchFragment searchFragment = new SearchFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame_container,searchFragment);
                    transaction.commit();
                    return true;

                case R.id.navigation_wishList:
                    addTocart();
                    WishList wishList = new WishList();
                    FragmentManager manager1 = getSupportFragmentManager();
                    FragmentTransaction transaction1 = manager1.beginTransaction();
                    transaction1.replace(R.id.frame_container,wishList);
                    transaction1.commit();
                    return true;

                case R.id.navigation_cart:

                    /*Intent intent = new Intent(MainActivity.this, PayUMoneyActivity.class);
                    startActivity(intent);*/
try {
    addTocart();
    Cart cart = new Cart();
    FragmentManager manager2 = getSupportFragmentManager();
    FragmentTransaction transaction2 = manager2.beginTransaction();
    transaction2.replace(R.id.frame_container, cart);
    transaction2.commit();
}catch (Exception e)
{
    e.printStackTrace();
}
                    return true;
                case R.id.navigation_account:
                    Account account = new Account();
                    FragmentManager manager3 = getSupportFragmentManager();
                    FragmentTransaction transaction3 = manager3.beginTransaction();
                    transaction3.replace(R.id.frame_container,account);
                    transaction3.commit();
                    return true;
            }
            return false;
        }
    };
    public void addTocart() {
        final String token = prefManager.getString("cust_id", "");
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String status = object.getString("success");
                    JSONArray getCarts = object.getJSONArray("cart");
                    // JSONArray jsonArray=object.getJSONArray("details");
                    if (!status.equals("")) {
                        if(getCarts.length()>0) {
                            BottomMenuHelper.showBadge(MainActivity.this, mBottomNavigationView, R.id.navigation_cart, "" + getCarts.length());
                        }
                       // cart = "" + getCarts.length();
                        getWishList();
                    } else {

                        Toast.makeText(MainActivity.this, "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        //   dialog.dismiss();
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                //dialog.dismiss();
                //dialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(stringRequest);
    }

    public void getWishList() {
        final String token = prefManager.getString("cust_id", "");
        // final ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Proccessing....Please wait");

        final String url = "http://52.66.136.244/api/v1/wishlist";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("success");
                    //JSONObject statusjson=new JSONObject(status);
                    // JSONArray jsonArray=object.getJSONArray("details");
                    //int lenght=0;
                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("whislist");
                        if(jsonArray.length()>0) {
                            // wishlist = "" + jsonArray.length();
                            BottomMenuHelper.showBadge(MainActivity.this, mBottomNavigationView, R.id.navigation_wishList, "" + jsonArray.length());
                        }
                        else
                        {
                           // BottomMenuHelper.showBadge(MainActivity.this, mBottomNavigationView, R.id.navigation_wishList, "");

                        }

                    } else {

                        // Toast.makeText(getActivity(), "alreay in wishlist", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
                    }
                    // dialog.dismiss();
                    ///dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // dialog.dismiss();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", "" + error.getCause());
                // dialog.dismiss();
                //dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }


}
