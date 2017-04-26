package com.vignesh.barcode.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.vignesh.barcode.R;
import com.vignesh.barcode.commodity.Commodity;

import java.util.ArrayList;


/**
 * Created by sysadmin on 7/4/17.
 */

public class StockListAdapter extends CursorAdapter {

    Context mContext;
    Cursor mCursor;

    public StockListAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor ,flags);
        this.mContext = context;
        this.mCursor = cursor;

    }


    public void setCommodityListAndRefresh(ArrayList<Commodity> aDocList) {
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

        String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
        String mrp = cursor.getString(cursor.getColumnIndex("mrp_unit"));
        String quantity = cursor.getString(cursor.getColumnIndex("stock_quantity"));

        textView_name.setText(name);
        textView_mrp.setText(mrp);
        textView_quantity.setText(quantity);
    }
}
