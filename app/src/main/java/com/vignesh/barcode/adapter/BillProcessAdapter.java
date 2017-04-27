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
 * Created by sysadmin on 12/4/17.
 */

public class BillProcessAdapter extends CursorAdapter {

    Context mContext;
    Cursor mCursor;

    public BillProcessAdapter(Context context, Cursor c, int flags) {

        super(context, c ,flags);
        this.mContext = context;
        this.mCursor = c;
    }

    public void setCommodityListAndRefresh(Cursor cursor) {

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_stock_report, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_name = (TextView) view.findViewById(R.id.tv_commodity_name);
        TextView textView_mrp = (TextView) view.findViewById(R.id.tv_commodity_rate);
        TextView textView_quantity = (TextView) view.findViewById(R.id.tv_commodity_quantity);
        TextView textView_final_amount = (TextView) view.findViewById(R.id.tv_commodity_final_amount);

        String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
        String mrp = cursor.getString(cursor.getColumnIndex("mrp_unit"));
        //String quantity = cursor.getString(cursor.getColumnIndex("stock_quantity"));

        textView_name.setText(name);
        textView_mrp.setText(mrp);
        textView_quantity.setText(textView_quantity.getText().toString());
    }


}
