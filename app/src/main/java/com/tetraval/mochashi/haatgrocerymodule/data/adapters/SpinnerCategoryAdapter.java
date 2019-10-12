package com.tetraval.mochashi.haatgrocerymodule.data.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tetraval.mochashi.R;
import com.tetraval.mochashi.haatgrocerymodule.data.models.SpinnerCategoryModel;

import java.util.ArrayList;

public class SpinnerCategoryAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<SpinnerCategoryModel> spinnerCategoryModelArrayList;
    public SpinnerCategoryAdapter(Context context,ArrayList<SpinnerCategoryModel> spinnerCategoryModelArrayList) {
        this.context = context;
        this.spinnerCategoryModelArrayList=spinnerCategoryModelArrayList;
    }
    @Override
    public int getCount() {
        return spinnerCategoryModelArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView cattypeid,cattype;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.cattypeid = (TextView) convertView.findViewById(R.id.catid);
            holder.cattype = (TextView) convertView.findViewById(R.id.txtSpinnerItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // RowItem rowItem = (RowItem) getItem(position);
        holder.cattypeid.setText(spinnerCategoryModelArrayList.get(position).getCat_id());
        holder.cattype.setText(spinnerCategoryModelArrayList.get(position).getCat_name());
        return convertView;
    }
}
