package com.badrul.qnitiboxadmin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScannerFragment extends Fragment {

    Button scan;
    Button submit;
    EditText idNum;
    int requestCode;
    String adminID_Shared;
    String orderID;
    ImageButton logout;
    //the recyclerview
    RecyclerView recyclerView;

    TextView userNama_tx;
    TextView userCredit_tx;

    List<Order> orderList;
    private int orderID2;
    private String cardID;
    private String nameID;
    private String phoneID;
    private String emailID;
    private String matrixID;
    private String orderType;
    private String orderDay;
    private String orderDate;
    private String orderTime;
    private String orderQTT;
    private String orderUserType;
    // private int usercredit;
    private String puLocation;
    private String puTime;
    private String orderStatus;
    private String completeDate;
    private String completeTime;
    private String totalPrice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_scanner, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, getActivity().getApplicationContext().MODE_PRIVATE);
        adminID_Shared = sharedPreferences.getString(Config.ID_SHARED_PREF, "Not Available");

        scan = myView.findViewById(R.id.qrbutton);
        submit = myView.findViewById(R.id.submitBtn);
        idNum = myView.findViewById(R.id.idInput);
        userCredit_tx = myView.findViewById(R.id.userCredit1);
        logout = myView.findViewById(R.id.logout);

        orderList = new ArrayList<>();

        userCredit_tx.setText(adminID_Shared);

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

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, requestCode);
                } else {

                    startQRScanner();
                }
            } });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderID = idNum.getText().toString().trim();

                if (orderID.length()<5){

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter correct Order ID", Toast.LENGTH_LONG).show();
                }
                else {

                        loadOrder();
                }}
        });

        return myView;
    }

    private void loadOrder() {

        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Please Wait","Contacting Server",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.SCANNER_URL+orderID,
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
                                        orderID2 = product.getInt("orderID"),
                                        cardID = product.getString("cardID"),
                                        nameID = product.getString("nameID"),
                                        phoneID = product.getString("phoneID"),
                                        emailID = product.getString("emailID"),
                                        matrixID = product.getString("matrixID"),
                                        orderType = product.getString("orderType"),
                                        orderDay = product.getString("orderDay"),
                                        orderDate = product.getString("orderDate"),
                                        orderTime = product.getString("orderTime"),
                                        orderQTT = product.getString("orderQTT"),
                                        orderUserType = product.getString("orderUserType"),
                                        puLocation = product.getString("puLocation"),
                                        puTime = product.getString("puTime"),
                                        orderStatus = product.getString("orderStatus"),
                                        completeDate = product.getString("completeDate"),
                                        completeTime = product.getString("completeTime"),
                                        totalPrice = product.getString("totalPrice")
                                ));
                            }

                            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME,
                                    getActivity().getApplicationContext().MODE_PRIVATE);

                            // Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // Adding values to editor

                            editor.putString(Config.ORDER_ID, String.valueOf(orderID2));
                            editor.putString(Config.CARD_ID, cardID);
                            editor.putString(Config.NAME_ID, nameID);
                            editor.putString(Config.PHONE_ID, phoneID);
                            editor.putString(Config.EMAIL_ID, emailID);
                            editor.putString(Config.MATRIX_ID, matrixID);
                            editor.putString(Config.ORDER_TYPE, orderType);
                            editor.putString(Config.ORDER_DAY, orderDay);
                            editor.putString(Config.ORDER_DATE2, orderDate);
                            editor.putString(Config.ORDER_TIME2, orderTime);
                            editor.putString(Config.ORDER_QTT, orderQTT);
                            editor.putString(Config.ORDER_USERTYPE, orderUserType);
                            editor.putString(Config.PICKUP_LOCATION, puLocation);
                            editor.putString(Config.PICKUP_TIME, puTime);
                            editor.putString(Config.ORDER_STATUS, orderStatus);
                            editor.putString(Config.ORDER_COMPLETEDATE, completeDate);
                            editor.putString(Config.ORDER_COMPLETETIME, completeTime);
                            editor.putString(Config.TOTAL_FOOD_PRICE, totalPrice);


                            // Saving values to editor
                            editor.commit();

                            loading.dismiss();

                            Intent i = new Intent(getActivity(), OrderDetails.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            //finish();

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

    private void startQRScanner() {
        IntentIntegrator.forSupportFragment(ScannerFragment.this).initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                orderID = result.getContents();
                //idNum.setText(orderID);

                loadOrder();
            }

        }
    }}
