package com.vignesh.barcode.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.vignesh.barcode.R;


/**
 * Created by sysadmin on 11/4/17.
 */

public class DailyReportAdapter extends CursorAdapter {

    Context mContext;
    Cursor mCursor;

    public DailyReportAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor ,flags);
        this.mContext = context;
        this.mCursor = cursor;

    }


    public void setCommodityListAndRefresh(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_daily_report, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_name = (TextView) view.findViewById(R.id.tv_commodity_name);
        TextView textView_mrp = (TextView) view.findViewById(R.id.tv_commodity_rate);
        TextView textView_quantity = (TextView) view.findViewById(R.id.tv_commodity_quantity);
        TextView textView_date = (TextView) view.findViewById(R.id.tv_commodity_date);
        TextView textView_total_amount = (TextView) view.findViewById(R.id.tv_commodity_amount);

        String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
        String mrp = cursor.getString(cursor.getColumnIndex("mrp_unit"));
        String quantity = cursor.getString(cursor.getColumnIndex("quantity_stock"));
        String date = cursor.getString(cursor.getColumnIndex("date"));
        String total_amount = cursor.getString(cursor.getColumnIndex("total_amount"));

        textView_name.setText(name);
        textView_mrp.setText(mrp);
        textView_quantity.setText(quantity);
        textView_date.setText(date);
        textView_total_amount.setText(total_amount);
    }
}
