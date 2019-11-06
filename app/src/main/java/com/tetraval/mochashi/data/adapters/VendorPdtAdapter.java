package com.tetraval.mochashi.data.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.BookedCustomerModel;
import com.tetraval.mochashi.data.models.VendorProductModel;
import com.tetraval.mochashi.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VendorPdtAdapter extends RecyclerView.Adapter<VendorPdtAdapter.VendorViewHolder> {

    List<VendorProductModel> vendorProductModelList;
    Context context;
    ProgressDialog progressDialog;
    RecyclerView recyclerBooked;
    List<BookedCustomerModel> bookedCustomerModelList;
    BookedCustomerAdapter bookedCustomerAdapter;
    ConstraintLayout constrainVendor;
    RequestQueue requestQueue;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "";

    public VendorPdtAdapter(List<VendorProductModel> vendorProductModelList, Context context) {
        this.vendorProductModelList = vendorProductModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public VendorPdtAdapter.VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_list_item, parent, false);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        constrainVendor = view.findViewById(R.id.constrainVendor);
        bookedCustomerModelList = new ArrayList<>();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");

        requestQueue = Volley.newRequestQueue(context);

        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorPdtAdapter.VendorViewHolder holder, final int position) {

        final VendorProductModel vendorProductModel = vendorProductModelList.get(position);
        Glide.with(context).load(vendorProductModel.getP_img1()).placeholder(R.drawable.productimage).into(holder.imgChashiProduct);
        holder.txtChashiProductName.setText(vendorProductModel.getProduct_name());
        holder.txtAvlQty.setText(vendorProductModel.getQty_hosted() + "Kg(s)");
        holder.txtBkdQty.setText(vendorProductModel.getQty_booked() + "Kg(s)");
        holder.txtRemainQty.setText(vendorProductModel.getQty_avl() + "Kg(s)");
        holder.txtRate.setText("₹" + vendorProductModel.getRate());
        if (vendorProductModel.getIs_deliver().equals("1")) {
            holder.imgVendorPdtDelivery.setVisibility(View.VISIBLE);
        } else {
            holder.imgVendorPdtDelivery.setVisibility(View.GONE);
        }
        holder.imgVendorPdtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete Product");
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this product, but booked products has to be delivered to customers.");
                alertDialogBuilder.setPositiveButton("Yes, Delete it",
                        (arg0, arg1) -> {
                            removeAt(position);
                            progressDialog.show();
                            StringRequest getRequest = new StringRequest(Request.Method.POST, AppConst.BASE_URL+"User_api/delete_chashi_product",
                                    response -> {
                                        Log.d("Response", response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String status =  jsonObject.getString("status");
                                            if (status.equals("200")) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            progressDialog.dismiss();

                                        }

                                    },
                                    new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("Error.Response", error.toString());
                                            progressDialog.dismiss();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                    Map<String, String>  params = new HashMap<String, String>();
                                    params.put("p_id", vendorProductModel.getProduct_id());
                                    return params;
                                }
                            };

                            requestQueue.add(getRequest);


                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.imgPersonBooked.setOnClickListener(view -> {
           /* final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.customer_booked_alert);
            recyclerBooked = dialog.findViewById(R.id.recyclerBooked);
            recyclerBooked.setLayoutManager(new LinearLayoutManager(context));*/
           // FetchProduct();
//                Query bookingRef = FirebaseDatabase.getInstance().getReference("products");
//                bookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        bookedCustomerModelList.clear();
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.child(vendorProductModel.getProduct_id()).getChildren()){
//                                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
//                                    Log.d("products", dataSnapshot3.toString());
//                                    for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()){
//                                        Log.d("products", dataSnapshot4.toString());
//                                        BookedCustomerModel bookedCustomerModel = new BookedCustomerModel(
//                                                dataSnapshot4.child("booking_id").getValue().toString(),
//                                                Double.parseDouble(dataSnapshot4.child("booking_lat").getValue().toString()),
//                                                Double.parseDouble(dataSnapshot4.child("booking_lng").getValue().toString()),
//                                                dataSnapshot4.child("booking_name").getValue().toString(),
//                                                dataSnapshot4.child("booking_photo").getValue().toString(),
//                                                dataSnapshot4.child("booking_qty").getValue().toString()
//                                        );
//                                        bookedCustomerModelList.add(bookedCustomerModel);
//                                    }
//                                }
//                            }
//                        }
//
//                        if (bookedCustomerModelList.size() == 0){
//                            Snackbar snackbar = Snackbar
//                                    .make(constrainVendor, "Currently there are no customers for this products.", Snackbar.LENGTH_LONG);
//                            snackbar.show();
//                        }
//                        bookedCustomerAdapter = new BookedCustomerAdapter(bookedCustomerModelList, context);
//                        bookedCustomerAdapter.notifyDataSetChanged();
//                        recyclerBooked.setAdapter(bookedCustomerAdapter);
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
          //  dialog.show();
        });

    }


    @Override
    public int getItemCount() {
        return vendorProductModelList.size();
    }

    public class VendorViewHolder extends RecyclerView.ViewHolder {

        ImageView imgChashiProduct, imgVendorPdtEdit, imgVendorPdtDelete, imgVendorPdtDelivery, imgPersonBooked;
        TextView txtChashiProductName, txtAvlQty, txtRate, txtBkdQty, txtRemainQty;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChashiProduct = itemView.findViewById(R.id.imgChashiProduct);
            txtChashiProductName = itemView.findViewById(R.id.txtChashiProductName);
            txtAvlQty = itemView.findViewById(R.id.textView21);
            txtBkdQty = itemView.findViewById(R.id.txtBkdQty);
            txtRemainQty = itemView.findViewById(R.id.txtRemainQty);
            txtRate = itemView.findViewById(R.id.textView23);
//            imgVendorPdtEdit = itemView.findViewById(R.id.imgVendorPdtEdit);
            imgVendorPdtDelete = itemView.findViewById(R.id.imgVendorPdtDelete);
            imgVendorPdtDelivery = itemView.findViewById(R.id.imgVendorPdtDelivery);
            imgPersonBooked = itemView.findViewById(R.id.imgPersonBooked);

        }
    }

    public void removeAt(int position) {
        vendorProductModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, vendorProductModelList.size());
    }


  /*  private void FetchProduct() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customer_booked_alert);
        recyclerBooked = dialog.findViewById(R.id.recyclerBooked);
        recyclerBooked.setLayoutManager(new LinearLayoutManager(context));
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
                             *//*   if (status.equals("1")){
                                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                    Intent intent=new Intent(context, MyOrderActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intent);

                                }else if (status.equals("0")){
                                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                }
*//*


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

                params.put("item_id", itemid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/

}
