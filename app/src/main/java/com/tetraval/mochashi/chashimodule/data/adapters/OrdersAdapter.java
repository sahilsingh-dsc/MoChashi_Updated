package com.tetraval.mochashi.chashimodule.data.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.chashimodule.ui.activities.ChasiMyOrdersActivity;
import com.tetraval.mochashi.data.models.OrdersModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    List<OrdersModel> ordersModelList;
    Context context;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "https://www.mochashi.com/User_api/order_cancel";
    OrdersAdapter adapter;
    public OrdersAdapter(List<OrdersModel> ordersModelList, Context context) {
        this.ordersModelList = ordersModelList;
        this.context = context;
        this.adapter = this;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myorder_list_item, viewGroup, false);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder ordersViewHolder, int i) {

        OrdersModel ordersModel = ordersModelList.get(i);
        Glide.with(context).load(ordersModel.getOdr_product_image()).placeholder(R.drawable.productimage).into(ordersViewHolder.imgOrderImage);
        ordersViewHolder.txtOrderName.setText(ordersModel.getOdr_name());
        ordersViewHolder.txtOrderQuantity.setText(ordersModel.getOdr_qty());
        ordersViewHolder.txtOrderID.setText(ordersModel.getOdr_id());
        ordersViewHolder.txtOrderDate.setText(ordersModel.getOdr_date());
        ordersViewHolder.txtOrderAmt.setText("₹"+ordersModel.getTotal_amount());
        ordersViewHolder.txtstatus.setText(ordersModel.getStatus());
        if (ordersModel.getStatus().equals("Pending")){
            ordersViewHolder.txtdelet.setVisibility(View.VISIBLE);
            ordersViewHolder.txtdelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeletProduct(ordersModel.getOdr_id(),ordersModel.getOdr_qty(),ordersModel.getProductid());
                }
            });

        }else{
            ordersViewHolder.txtdelet.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ordersModelList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        ImageView imgOrderImage;
        TextView txtOrderName, txtOrderQuantity, txtOrderID, txtOrderDate, txtOrderAmt,txtstatus,txtdelet;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOrderImage = itemView.findViewById(R.id.imgOrderImage);
            txtOrderName = itemView.findViewById(R.id.txtOrderName);
            txtOrderQuantity = itemView.findViewById(R.id.txtOrderQuantity);
            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtOrderAmt = itemView.findViewById(R.id.txtOrderAmt);
            txtstatus = itemView.findViewById(R.id.textViewstatus);
            txtdelet = itemView.findViewById(R.id.txtdelet);

        }
    }


    private void DeletProduct(String orderid,String qty,String productid) {
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
                                    Intent intent=new Intent(context, ChasiMyOrdersActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intent);

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
                params.put("qty", qty);
                params.put("product_id", productid);
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
    }


}
