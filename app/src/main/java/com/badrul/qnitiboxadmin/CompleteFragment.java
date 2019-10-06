package com.badrul.qnitiboxadmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompleteFragment extends Fragment implements OrderAdapter.OnItemClicked{

    List<Order> orderList;
    ImageButton logout;


    //the recyclerview
    RecyclerView recyclerView;
    // ImageButton logout;

    String userEmail_Shared;
    String userID;
    String image;
    TextView userNama_tx;
    TextView userCredit_tx;
    ImageView imgGone;
    TextView txtGone;
    //int curCheckPosition = 0;
    String adminLocation;
    String currentDate;
    TextView showtodayQTT;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_complete, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, getActivity().getApplicationContext().MODE_PRIVATE);
        String userNama = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");
        adminLocation = sharedPreferences.getString(Config.ADMIN_LOCATION,"Not Available");

        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        //logout =myView.findViewById(R.id.logoutBtn);
        userNama_tx = myView.findViewById(R.id.userNama1);
        userCredit_tx = myView.findViewById(R.id.userCredit1);
        recyclerView = myView.findViewById(R.id.recylcerView);
        logout = myView.findViewById(R.id.logout);
        imgGone = myView.findViewById(R.id.imageViewGone);
        txtGone = myView.findViewById(R.id.textViewGone);
        showtodayQTT = myView.findViewById(R.id.showtodayqtt);


        //userNama_tx.setText(userNama);
        userCredit_tx.setText(userNama);

        // Set the layout manager to your recyclerview and reverse the position
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //initializing the productlist

        orderList = new ArrayList<>();


        //this method will fetch and parse json
        //to display it in recyclerview
        todayProcessQTT();
        loadOrder();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating an alert dialog to confirm logout
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to logout?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                //Getting out sharedpreferences
                                SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                //Getting editor
                                SharedPreferences.Editor editor = preferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(Config.ID_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.clear();
                                editor.commit();

                                //Starting login activity
                                Intent intent = new Intent(getActivity().getApplicationContext(), LoginPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().finish();
                                startActivity(intent);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return myView;
    }


    private void loadOrder() {

        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.ORDER_STATUS_COMPLETE+adminLocation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                orderList.add(new Order(
                                        product.getInt("orderID"),
                                        product.getString("cardID"),
                                        product.getString("nameID"),
                                        product.getString("phoneID"),
                                        product.getString("emailID"),
                                        product.getString("matrixID"),
                                        product.getString("orderType"),
                                        product.getString("orderDay"),
                                        product.getString("orderDate"),
                                        product.getString("orderTime"),
                                        product.getString("orderQTT"),
                                        product.getString("orderUserType"),
                                        product.getString("puLocation"),
                                        product.getString("puTime"),
                                        product.getString("orderStatus"),
                                        product.getString("completeDate"),
                                        product.getString("completeTime"),
                                        product.getString("totalPrice")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            OrderAdapter adapter = new OrderAdapter(getActivity().getApplicationContext(), orderList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnClick(CompleteFragment.this);

                            if (adapter.getItemCount() == 0) {
                                imgGone.setVisibility(View.VISIBLE);
                                txtGone.setVisibility(View.VISIBLE);
                            } else{

                                imgGone.setVisibility(View.GONE);
                                txtGone.setVisibility(View.GONE);
                            }

                            //add shared preference ID,nama,credit here
                            loading.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(),"No internet . Please check your connection",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);


    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", curCheckPosition);
    }

   /* public void setRvadapter (List<Product> productList) {

        ProductsAdapter myAdapter = new ProductsAdapter(this,productList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }*/



    @Override
    public void onItemClick(int position) {
        // The onClick implementation of the RecyclerView item click
        //ur intent code here
        Order order = orderList.get(position);
        //Toast.makeText(FoodMenu.this, product.getLongdesc(),
        //      Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME,
                getActivity().getApplicationContext().MODE_PRIVATE);

        // Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Adding values to editor

        editor.putString(Config.ORDER_ID, String.valueOf(order.getOrderID()));
        editor.putString(Config.CARD_ID, order.getCardID());
        editor.putString(Config.NAME_ID, order.getNameID());
        editor.putString(Config.PHONE_ID, order.getPhoneID());
        editor.putString(Config.EMAIL_ID, order.getEmailID());
        editor.putString(Config.MATRIX_ID, order.getMatrixID());
        editor.putString(Config.ORDER_TYPE, order.getOrderType());
        editor.putString(Config.ORDER_DAY, order.getOrderDay());
        editor.putString(Config.ORDER_DATE2, order.getOrderDate());
        editor.putString(Config.ORDER_TIME2, order.getOrderTime());
        editor.putString(Config.ORDER_QTT, order.getOrderQTT());
        editor.putString(Config.ORDER_USERTYPE, order.getOrderUserType());
        editor.putString(Config.PICKUP_LOCATION, order.getPuLocation());
        editor.putString(Config.PICKUP_TIME, order.getPuTime());
        editor.putString(Config.ORDER_STATUS, order.getOrderStatus());
        editor.putString(Config.ORDER_COMPLETEDATE, order.getCompleteDate());
        editor.putString(Config.ORDER_COMPLETETIME, order.getCompleteTime());
        editor.putString(Config.TOTAL_FOOD_PRICE, order.getTotalPrice());


        // Saving values to editor
        editor.commit();

        Intent i = new Intent(getActivity().getApplicationContext(), OrderDetails.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        //finish();
    }
    public void todayProcessQTT(){

        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Config.COMPLETE_CHECK_TODAY_QTT+currentDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                showtodayQTT.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(),
                            "No internet. Please check your connection",
                            Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getActivity(),
                            error.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        })
                ;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}