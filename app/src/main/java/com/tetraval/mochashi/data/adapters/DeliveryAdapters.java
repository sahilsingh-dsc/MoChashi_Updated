package com.tetraval.mochashi.data.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.chashimodule.data.adapters.OrdersAdapter;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.data.models.DeliveryModel;
import com.tetraval.mochashi.data.models.PickupModel;
import com.tetraval.mochashi.ui.activities.DeliveryManActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryAdapters extends RecyclerView.Adapter<DeliveryAdapters.DeliveryViewHolder> {

    List<DeliveryModel> deliveryModelList;
    Context context;
    List<PickupModel> pickupModelList;
    PickupAdapter pickupAdapter;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "https://www.mochashi.com/User_api/chashi_booking_delivered";
    DeliveryAdapters adapter;
    EditText amount;
    TextView txt_resend;


    public DeliveryAdapters(List<DeliveryModel> deliveryModelList, Context context) {
        this.deliveryModelList = deliveryModelList;
        this.context = context;
        this.adapter = this;
    }


    @NonNull
    @Override
    public DeliveryAdapters.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_item, parent, false);
        pickupModelList = new ArrayList<>();
        pickupModelList.clear();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAdapters.DeliveryViewHolder holder, int position) {
        DeliveryModel deliveryModel = deliveryModelList.get(position);
        holder.txtDelCustomerName.setText(deliveryModel.getCustomer_name());
        holder.txtDelContact.setText(deliveryModel.getCustomer_contact());
        holder.txtCODAmount.setText("₹"+deliveryModel.getCustomer_rate());
        holder.txtqty.setText("Qty "+deliveryModel.getCustomer_qty());
        holder.txtCustomerDelivery.setText(deliveryModel.getCustomer_address());
        if (deliveryModel.getStatus().equals("3")){
            holder.txtDelivery.setVisibility(View.GONE);
            holder.txtstatus.setVisibility(View.VISIBLE);
        }
        holder.txtDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmamount(deliveryModel.getCustomer_id(),deliveryModel.getCustomer_qty(),deliveryModel.getProduct_id(),deliveryModel.getCustomer_rate(),deliveryModel.getUserid());
            }
        });


    }

    @Override
    public int getItemCount() {
        return deliveryModelList.size();
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder {

        //RecyclerView listPickupAddress;
        TextView txtDelCustomerName, txtDelContact, txtCODAmount,txtqty, txtCustomerDelivery,txtDelivery,txtstatus;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);

            //listPickupAddress = itemView.findViewById(R.id.listPickupAddress);
            txtDelCustomerName = itemView.findViewById(R.id.txtDelCustomerName);
            txtDelContact = itemView.findViewById(R.id.txtDelContact);
            txtCODAmount = itemView.findViewById(R.id.txtCODAmount);
            txtqty = itemView.findViewById(R.id.txtquantity);
            txtCustomerDelivery = itemView.findViewById(R.id.txtCustomerDelivery);
            txtDelivery = itemView.findViewById(R.id.txtdeli);
            txtstatus = itemView.findViewById(R.id.txtstatus);

        }
    }
 /*   private void DeliverProduct(String orderid,String qty,String productid) {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidepDialog();
                        if (null == response || response.length() == 0) {
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status= mainJson.getString("status");
                                String message=mainJson.getString("message");
                                if (status.equals("200")){
                                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                   *//* Intent intent=new Intent(context, DeliveryManActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);*//*
                                    Intent intent=new Intent("Follow");
                                    intent.putExtra("pid",productid);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                }else if (status.equals("404")){
                                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                }



                            } catch (JSONException e) {
                                hidepDialog();
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidepDialog();
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request

                params.put("order_id", orderid);
                params.put("quantity", qty);
                params.put("product_id", productid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }*/


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void confirmamount(String orderid,String qty,String productid,String price,String userid)  {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dailog_confirm, null);

        amount = confirmDialog.findViewById(R.id.amount);
        txt_resend = confirmDialog.findViewById(R.id.txt_resend);



        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);


        //On the click of the confirm button from alert dialog
        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                final String one = amount.getText().toString();

                if (one.equals("")) {
                    Toast.makeText(context, "Please enter amount", Toast.LENGTH_SHORT).show();

                } else {

                    //Creating an string request
                    showpDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    hidepDialog();
                                    if (null == response || response.length() == 0) {
                                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                                    } else {
                                        try {
                                            JSONObject mainJson = new JSONObject(response);
                                            String status = mainJson.getString("status");
                                            String message = mainJson.getString("message");
                                            if (status.equals("200")) {
                                                alertDialog.dismiss();
                                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                Intent intent = new Intent("Follow");
                                                intent.putExtra("pid", productid);
                                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                            } else if (status.equals("404")) {
                                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                            }


                                        } catch (JSONException e) {
                                            hidepDialog();
                                            e.printStackTrace();
                                        }

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    hidepDialog();
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding the parameters to the request

                            params.put("order_id", orderid);
                            params.put("quantity", qty);
                            params.put("product_id", productid);
                            params.put("price_recived", one);
                            params.put("price", price);
                            params.put("user_id", userid);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }


}
