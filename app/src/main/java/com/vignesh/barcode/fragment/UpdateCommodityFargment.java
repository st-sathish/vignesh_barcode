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

import com.vignesh.barcode.LandingPageActivity;
import com.vignesh.barcode.MainActivity;
import com.vignesh.barcode.R;
import com.vignesh.barcode.commodity.Commodity;
import com.vignesh.barcode.commodity.CommodityOperationType;
import com.vignesh.barcode.databases.DatabaseManager;
import com.vignesh.barcode.extras.AppConstants;
import com.vignesh.barcode.extras.DateUtils;

import static com.vignesh.barcode.R.id.commodity_id;
import static com.vignesh.barcode.R.id.commodity_name;


/**
 * Created by sysadmin on 23/3/17.
 */

public class UpdateCommodityFargment extends BaseFragment implements View.OnClickListener{

    EditText editText_qrcode,editText_comName,editText_comMrp,editText_comQuentity,editText_date;
    Button button_update,button_home,button_logout,button_search;
    SQLiteDatabase db;

    public UpdateCommodityFargment(){

    }
    public static UpdateCommodityFargment newInstance(String aTitle) {
        UpdateCommodityFargment fragment = new UpdateCommodityFargment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fr_update, container, false);

        editText_qrcode = (EditText) mParentView.findViewById(commodity_id);
        editText_comName = (EditText) mParentView.findViewById(commodity_name);
        editText_comMrp = (EditText) mParentView.findViewById(R.id.commodity_mrp);
        editText_comQuentity = (EditText) mParentView.findViewById(R.id.commodity_quantity);


        button_update = (Button) mParentView.findViewById(R.id.add_commodity12);
        button_home = (Button) mParentView.findViewById(R.id.home);
        //button_logout = (Button) mParentView.findViewById(R.id.logout);
        button_search = (Button) mParentView.findViewById(R.id.button_search);
        button_logout.setOnClickListener(this);
        button_home.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_search.setOnClickListener(this);
        db = DatabaseManager.getInstance().openDatabase();
        //listView = (ListView) mParentView.findViewById(R.id.list_update);
        return mParentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_commodity12:
                update();
                break;
            case R.id.button_search:
                search();
                break;
            /*case R.id.home:
                Intent i  = new Intent(mParentView.getContext(), MenuFragment.class);
                mParentView.getContext().startActivity(i);
                getActivity().finish();
                break;
            case R.id.logout:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;*/
        }
    }

    public void search(){
        SearchAsyncTask searchAsyncTask = new SearchAsyncTask(CommodityOperationType.CHECK);
        searchAsyncTask.execute();

    }

    public class SearchAsyncTask extends AsyncTask<Void, Void, Commodity> {

        CommodityOperationType commodityOperationType;
        String qrcode;
        String name,mrp,date;
        int quantity;


        private SearchAsyncTask(CommodityOperationType commodityOperationType) {
                this.commodityOperationType = commodityOperationType;
        }

        @Override
        protected void onPreExecute() {
            qrcode = editText_qrcode.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected Commodity doInBackground(Void... voids) {
            Cursor cursor  = null;
            Commodity commodity = new Commodity();
            switch (commodityOperationType) {
                case CHECK:

                    String sql = "Select commmodity_id, qr_code, commodity_name, mrp_unit, quantity from commodity where qr_code ='" + qrcode+"'";
                    try {
                        cursor = db.rawQuery(sql,null);
                        if(cursor != null && cursor.moveToFirst()) {
                            commodity.setCommodity_id(cursor.getInt(cursor.getColumnIndex("commmodity_id")));
                            commodity.setQr_code(cursor.getString(cursor.getColumnIndex("qr_code")));
                            commodity.setCommodity_name(cursor.getString(cursor.getColumnIndex("commodity_name")));
                            commodity.setCommodity_stock_quantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                            commodity.setCommodity_unit_mrp(cursor.getInt(cursor.getColumnIndex("mrp_unit")));
                        }
             } catch(Exception e){
                        e.printStackTrace();
             }
            finally {
                if(cursor != null) {
                    cursor.close();
                }
            }

            }
            return commodity;
        }

        @Override
        protected void onPostExecute(Commodity commodity) {
            editText_comName.setText(commodity.getCommodity_name());
            editText_comMrp.setText(String.valueOf(commodity.getCommodity_unit_mrp()));
            editText_comQuentity.setText(String.valueOf(commodity.getCommodity_stock_quantity()));
        }
    }

    public void update(){
        String GetName = editText_comName.getText().toString();
        String GetMrp = editText_comMrp.getText().toString();
        int GetQuantity = Integer.parseInt(editText_comQuentity.getText().toString());
        String GetDate = editText_date.getText().toString();

        Commodity commodity = new Commodity();
        commodity.setCommodity_name(GetName);
        commodity.setCommodity_unit_mrp(Integer.parseInt(GetMrp));
        commodity.setCommodity_stock_quantity(GetQuantity);
        commodity.setQr_code(editText_qrcode.getText().toString());
        commodity.setDate(DateUtils.getCurrentDate());

        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(commodity, CommodityOperationType.UPDATE);
        updateAsyncTask.execute();

    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        Commodity commodity;
        CommodityOperationType commodityOperationType;


        public UpdateAsyncTask (Commodity commodity, CommodityOperationType commodityOperationType) {
            this.commodity = commodity;
            this.commodityOperationType = commodityOperationType;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String sql1 = "UPDATE commodity SET commodity_name ='" + commodity.getCommodity_name() + "', mrp_unit=" + commodity.getCommodity_unit_mrp() + ", quantity=" + commodity.getCommodity_stock_quantity() + ", date = '"+ commodity.getDate()+"' WHERE qr_code='" + commodity.getQr_code() + "'";
            try{
                db.execSQL(sql1);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Void) {
            switchFragment(LandingPageActivity.FRAGMENT_MENU, "Menu");
        }
    }

}
