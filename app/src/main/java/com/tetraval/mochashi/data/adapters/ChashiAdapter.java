package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.ChashiModel;
import com.tetraval.mochashi.ui.activities.ChashiProductDtl;
import java.util.List;

public class ChashiAdapter extends RecyclerView.Adapter<ChashiAdapter.ChasiViewHolder> {


    List<ChashiModel> chashiModelList;
    Context context;

    public ChashiAdapter(List<ChashiModel> chashiModelList, Context context) {
        this.chashiModelList = chashiModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChashiAdapter.ChasiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chashi_list_item, viewGroup, false);
        return new ChasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChashiAdapter.ChasiViewHolder chasiViewHolder, int i) {

        final ChashiModel chashiModel = chashiModelList.get(i);
        Glide.with(context).load(chashiModel.getVendor_img()).placeholder(R.drawable.productimage).into(chasiViewHolder.imgChashiProduct);
        chasiViewHolder.txtChashiProductName.setText(chashiModel.getVendor_name());
        chasiViewHolder.txtChashiPrice.setText("₹"+chashiModel.getRate());
        chasiViewHolder.txtQty.setText(chashiModel.getQty_avl()+"Kg(s)");
        chasiViewHolder.cardCOPD.setOnClickListener(view -> {
            Intent chashiIntent = new Intent(context, ChashiProductDtl.class);
            Bundle chashiBundle = new Bundle();
            chashiBundle.putString("product_id", chashiModel.getProduct_id());
            chashiBundle.putString("vendor_id", chashiModel.getVendor_id());
            chashiIntent.putExtras(chashiBundle);
            context.startActivity(chashiIntent);
        });
    }

    @Override
    public int getItemCount() {
        return chashiModelList.size();
    }

    public class ChasiViewHolder extends RecyclerView.ViewHolder {

        ImageView imgChashiProduct;
        TextView txtChashiProductName, txtChashiPrice, txtQty, txtLocation;
        CardView cardCOPD;

        public ChasiViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChashiProduct = itemView.findViewById(R.id.imgChashiProduct);
            txtChashiProductName = itemView.findViewById(R.id.txtChashiProductName);
            txtChashiPrice = itemView.findViewById(R.id.textView23);
            txtQty = itemView.findViewById(R.id.textView21);
            cardCOPD = itemView.findViewById(R.id.cardCOPD);

        }
    }
}
