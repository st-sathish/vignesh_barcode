package com.vignesh.barcode.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.barcode.LandingPageActivity;
import com.barcode.LoginActivity;
import com.barcode.R;
import com.barcode.commodity.Commodity;
import com.barcode.commodity.CommodityOperationType;
import com.barcode.databases.DatabaseManager;
import com.barcode.extras.AppConstants;

/**
 * Created by sysadmin on 23/3/17.
 */

public class StockFragment extends BaseFragment implements View.OnClickListener {
    Button update,logout,search;
    EditText editText_qr_code,editText_stock;
    SQLiteDatabase db;
    int getStock;
    String find;

    public StockFragment(){

    }
    public static StockFragment newInstance(String aTitle) {
        StockFragment fragment = new StockFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mParentView = inflater.inflate(R.layout.checkcommodity, container, false);

//        commodity_id12 = (EditText) mParentView.findViewById(R.id.check_id);
        editText_qr_code = (EditText) mParentView.findViewById(R.id.show_id);
        editText_stock = (EditText) mParentView.findViewById(R.id.et_stock);
//        commodity_name1 = (EditText) mParentView.findViewById(R.id.name_commodity);
//        mrp1 = (EditText) mParentView.findViewById(R.id.mrp_quentity);
//        quanty1 = (EditText) mParentView.findViewById(R.id.et_quantity);

        search = (Button) mParentView.findViewById(R.id.search);
        update = (Button) mParentView.findViewById(R.id.button_update);
        logout = (Button) mParentView.findViewById(R.id.end);
        update.setOnClickListener(this);
        logout.setOnClickListener(this);
        search.setOnClickListener(this);

        db = DatabaseManager.getInstance().openDatabase();
        return mParentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                getData();
                break;
            case R.id.end:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.button_update:
                setData();
                break;
        }
    }

    public void getData(){

        Commodity commodity = new Commodity();
        StackAsyncTask stackAsyncTask = new StackAsyncTask(commodity, CommodityOperationType.CHECK);
        stackAsyncTask.execute();

    }

    public class StackAsyncTask extends AsyncTask<Void, Void, Integer> {

        CommodityOperationType commodityOperationType;
        Commodity commodity;

        public StackAsyncTask(Commodity commodity, CommodityOperationType commodityOperationType) {
            this.commodity = commodity;
            this.commodityOperationType = commodityOperationType;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            find = editText_qr_code.getText().toString();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Cursor cursor = null;
            Integer quantity = 0;
            switch (commodityOperationType) {
                case CHECK:
                    String sql = "Select quantity from commodity where qr_code ='" + find+"'";
                    try {
                        cursor = db.rawQuery(sql,null);
                        if(cursor.moveToFirst()) {
                            quantity = cursor.getInt(0);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if(cursor != null)
                        cursor.close();
                    }
            }
            return quantity;
        }

        @Override
        protected void onPostExecute(Integer quantity) {
            editText_stock.setText(String.valueOf(quantity));
        }
    }

    public  void setData(){
        String sql1 = "UPDATE commodity SET quantity =" + Integer.parseInt(editText_stock.getText().toString()) + " WHERE qr_code='" + find + "'";
        db.execSQL(sql1);
        switchFragment(LandingPageActivity.FRAGMENT_MENU, "Menu");
    }
}
