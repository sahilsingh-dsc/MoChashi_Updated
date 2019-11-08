package com.tetraval.mochashi.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.data.models.CreditModel;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewModel> {

    List<CreditModel> creditModelList;
    Context context;

    public CreditAdapter(List<CreditModel> creditModelList, Context context) {
        this.creditModelList = creditModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CreditAdapter.CreditViewModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.credit_list_item, viewGroup, false);
        return new CreditViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.CreditViewModel creditViewModel, int i) {

        CreditModel creditModel = creditModelList.get(i);
        creditViewModel.txtCreditAmount.setText("₹"+creditModel.getCredit_amount());
        //creditViewModel.txtCreditReason.setText(creditModel.getCredit_reason());
        creditViewModel.txtCreditDate.setText(creditModel.getCredit_date());

    }

    @Override
    public int getItemCount() {
        return creditModelList.size();
    }

    public class CreditViewModel extends RecyclerView.ViewHolder {

        TextView txtCreditAmount, txtCreditReason, txtCreditDate;

        public CreditViewModel(@NonNull View itemView) {
            super(itemView);

            txtCreditAmount = itemView.findViewById(R.id.txtCreditAmount);
            txtCreditReason = itemView.findViewById(R.id.txtCreditReason);
            txtCreditDate = itemView.findViewById(R.id.txtCreditDate);

        }
    }
}
