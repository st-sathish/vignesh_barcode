package com.vignesh.barcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.vignesh.barcode.R;
import com.vignesh.barcode.commodity.BillProcess;

import java.util.List;

/**
 * Created by sysadmin on 22/4/17.
 */

public class BillAdapter extends BaseAdapter {

    Context mContexts;

    List<BillProcess> mBillprocess;

    public BillAdapter(Context context, List<BillProcess> billProcess) {
        this.mContexts = context;
        this.mBillprocess = billProcess;
    }

    @Override
    public BillProcess getItem(int i) {
        return mBillprocess.get(i);
    }

    @Override
    public int getCount() {
        return mBillprocess.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillProcess billProcess = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContexts).inflate(R.layout.item_bill_process, parent, false);
        }
        TextView textView_id = (TextView) convertView.findViewById(R.id.tv_commodity_id);
        TextView textView_name = (TextView) convertView.findViewById(R.id.tv_commodity_name);
        TextView textView_mrp = (TextView) convertView.findViewById(R.id.tv_commodity_rate);
        TextView textView_quantity = (TextView) convertView.findViewById(R.id.tv_commodity_quantity);
        TextView textView_date = (TextView) convertView.findViewById(R.id.tv_commodity_date);
        TextView textView_final_amount = (TextView) convertView.findViewById(R.id.tv_commodity_final_amount);

        textView_name.setText(billProcess.getCommodity_name());
        textView_mrp.setText(String.valueOf(billProcess.getCommodity_unit_mrp()));
        textView_quantity.setText(String.valueOf(billProcess.getQuantity()));
        textView_date.setText(billProcess.getDate());
        textView_final_amount.setText(String.valueOf(billProcess.getTotalAmount()));

        return convertView;
    }

    public void refresh(List<BillProcess> billProcess) {
        this.mBillprocess = billProcess;
        notifyDataSetChanged();
    }
}
