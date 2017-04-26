package com.vignesh.barcode.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.vignesh.barcode.R;
import com.vignesh.barcode.adapter.BillAdapter;
import com.vignesh.barcode.commodity.BillProcess;
import com.vignesh.barcode.commodity.Commodity;
import com.vignesh.barcode.commodity.CommodityOperationType;
import com.vignesh.barcode.databases.DatabaseManager;
import com.vignesh.barcode.extras.AppConstants;
import com.vignesh.barcode.extras.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysadmin on 23/3/17.
 */

public class DailyReportFragment extends BaseFragment  {

    EditText qr_code;
    BillAdapter mBillAdapter;
    List<BillProcess> billProcessList;
    SQLiteDatabase db;
    ListView listView;

    public DailyReportFragment() {

    }

    public static DailyReportFragment newInstance(String aTitle) {
        DailyReportFragment fragment = new DailyReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.daily_report, container, false);

        qr_code = (EditText) mParentView.findViewById(R.id.show_id);
        billProcessList = new ArrayList<>();
        listView = (ListView) mParentView.findViewById(R.id.lv_daily_report);
        mBillAdapter = new BillAdapter(getContext(), billProcessList);
        listView.setAdapter(mBillAdapter);
        db = DatabaseManager.getInstance().openDatabase();
        showData();
        return mParentView;
    }




    public void showData() {
        ReportDetailsAsyncTask dailyReportAsyncTask = new ReportDetailsAsyncTask(CommodityOperationType.CHECK);
        dailyReportAsyncTask.execute();
    }

    public class ReportDetailsAsyncTask extends AsyncTask<Void, Void, List<BillProcess> > {

        CommodityOperationType commodityOperationType;
        Commodity commodity;
        String check_value;



        public ReportDetailsAsyncTask(CommodityOperationType commodityOperationType) {
            this.commodityOperationType = commodityOperationType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            check_value = DateUtils.getCurrentDate();
        }

        @Override
        protected List<BillProcess> doInBackground(Void... voids) {
            Cursor cursor = null;
            List<BillProcess> billProcesses = new ArrayList<>();
            switch (commodityOperationType) {
                case CHECK:
                    String sql = "select commodity_name, mrp_unit, qr_code, total_amount , " +
                            "quantity, billed_at, commmodity_id from bill as b left outer join commodity as c on c.commmodity_id = b.commodity_id where b.billed_at = '"+check_value+"'";
                    //String sql = "Select commodity_name, mrp_unit, qr_code, total_amount , quantity, date, commmodity_id  from commodity where date ='" + check_value+"'";
                    try {
                        String name,date;
                        int mrp,quantity,id, total_amount;
                        cursor = db.rawQuery(sql, null);
                        while (cursor.moveToNext()){
                            name = cursor.getString(cursor.getColumnIndex("commodity_name"));
                            mrp = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mrp_unit")));
                            String totalAmount = cursor.getString(cursor.getColumnIndex("total_amount"));
                            if(totalAmount == null) {
                                totalAmount = "0";
                            }
                            total_amount = Integer.parseInt(totalAmount);
                            quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                            date = cursor.getString(cursor.getColumnIndex("billed_at"));
                            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("commmodity_id")));

                            BillProcess billProcess = new BillProcess();
                            billProcess.setCommodity_id(id);
                            billProcess.setCommodity_name(name);
                            billProcess.setCommodity_unit_mrp(mrp);
                            billProcess.setQuantity(quantity);
                            billProcess.setDate(date);
                            billProcess.setTotalAmount(total_amount);
                            billProcesses.add(billProcess);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        if(cursor != null) {
                            cursor.close();
                        }
                    }

            }
            return billProcesses;

        }

        @Override
        protected void onPostExecute(List<BillProcess> billProcessList) {
            mBillAdapter.refresh(billProcessList);
            Toast.makeText(getContext(), "Check stock Successfully ", Toast.LENGTH_LONG).show();
        }
    }
}