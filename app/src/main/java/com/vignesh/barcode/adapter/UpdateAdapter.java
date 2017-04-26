package com.vignesh.barcode.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;

import com.vignesh.barcode.R;


/**
 * Created by sysadmin on 14/4/17.
 */

public class UpdateAdapter extends CursorAdapter {

    Context mContext;
    Cursor mCursor;

    public UpdateAdapter(Context context, Cursor cursor, int flags){
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

        View view = LayoutInflater.from(context).inflate(R.layout.item_update, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        EditText editText_name = (EditText) view.findViewById(R.id.editText_name);
        EditText editText_mrp = (EditText) view.findViewById(R.id.editText_mrp);
        EditText editText_quantity = (EditText) view.findViewById(R.id.editText_quantity);

        String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
        String mrp = cursor.getString(cursor.getColumnIndex("mrp_unit"));
        String quantity = cursor.getString(cursor.getColumnIndex("quantity_stock"));

        editText_name.setText(name);
        editText_mrp.setText(mrp);
        editText_quantity.setText(quantity);
    }
}
