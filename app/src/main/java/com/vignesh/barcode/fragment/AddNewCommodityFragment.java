package com.vignesh.barcode.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vignesh.barcode.LandingPageActivity;
import com.vignesh.barcode.MainActivity;
import com.vignesh.barcode.R;
import com.vignesh.barcode.commodity.Commodity;
import com.vignesh.barcode.commodity.CommodityOperationType;
import com.vignesh.barcode.databases.DatabaseManager;
import com.vignesh.barcode.extras.AppConstants;
import com.vignesh.barcode.extras.DateUtils;


/**
 * Created by sysadmin on 23/3/17.
 */

public class AddNewCommodityFragment extends BaseFragment implements View.OnClickListener {

    EditText et_id,et_commodity_name,et_mrp_unit,et_quantity_stock;
    Button add,user_logout,goto_home;
    SQLiteDatabase db;

    public AddNewCommodityFragment(){

    }
    public static AddNewCommodityFragment newInstance(String aTitle) {
        AddNewCommodityFragment fragment = new AddNewCommodityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.checkid, container, false);
        et_id = (EditText) mParentView.findViewById(R.id.commodity_id);
        et_commodity_name = (EditText) mParentView.findViewById(R.id.commodity_name);
        et_mrp_unit = (EditText) mParentView.findViewById(R.id.commodity_mrp);
        et_quantity_stock = (EditText) mParentView.findViewById(R.id.commodity_quantity);

        add = (Button) mParentView.findViewById(R.id.add_commodity);
        //user_logout = (Button) mParentView.findViewById(R.id.logout);
        //goto_home = (Button) mParentView.findViewById(R.id.home);

        add.setOnClickListener(this);
       // user_logout.setOnClickListener(this);
        //goto_home.setOnClickListener(this);

        db = DatabaseManager.getInstance().openDatabase();
        return mParentView;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add_commodity:
                insertOrUpgrade();
                switchFragment(LandingPageActivity.FRAGMENT_MENU, "Menu");
                break;
            /*case R.id.logout:
                Intent intent  = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent i  = new Intent(mParentView.getContext(),MenuFragment.class);
                mParentView.getContext().startActivity(i);
                getActivity().finish();
                break;*/
        }
    }

    public void insertOrUpgrade(){
        String mCommodityName = et_commodity_name.getText().toString();
        String mComodityUnit = et_mrp_unit.getText().toString();
        if(mComodityUnit.equals("")) {
            Toast.makeText(getActivity(), "Please enter commodity price", Toast.LENGTH_SHORT).show();
            return;
        }
        int mComodityQuanty = 1;
        if(!et_quantity_stock.getText().toString().equals("")) {
            mComodityQuanty = Integer.parseInt(et_quantity_stock.getText().toString());
        }
        Commodity commodity = new Commodity();
        commodity.setQr_code(et_id.getText().toString());
        commodity.setCommodity_name(mCommodityName);
        commodity.setCommodity_unit_mrp(Integer.parseInt(mComodityUnit));
        commodity.setCommodity_stock_quantity(mComodityQuanty);

        commodity.setDate(DateUtils.getCurrentDate());
        AddCommodityAsyncTask userAsyncTask = new AddCommodityAsyncTask(commodity, CommodityOperationType.ADD);
        userAsyncTask.execute();
    }

    public class AddCommodityAsyncTask extends AsyncTask<Void, Void, Void> {

        CommodityOperationType addOperationType;
        Commodity commodity;


        public AddCommodityAsyncTask(Commodity commodity, CommodityOperationType addOperationType) {
            this.commodity = commodity;
            this.addOperationType = addOperationType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (addOperationType) {
                case ADD:
                    String sql = "insert into commodity (qr_code ,commodity_name , mrp_unit ,stock, date  ) values (" + commodity.getQr_code() + ",'" + commodity.getCommodity_name() + "'," + commodity.getCommodity_unit_mrp() + "," + commodity.getCommodity_stock_quantity() + ",'"+ commodity.getDate()+"')";
                    try {
                        db.execSQL(sql);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(),"commodity add successful",Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
}

