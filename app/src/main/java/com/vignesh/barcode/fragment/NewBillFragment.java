package com.vignesh.barcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.vignesh.barcode.R;
import com.vignesh.barcode.SessionStore;
import com.vignesh.barcode.adapter.BillAdapter;
import com.vignesh.barcode.adapter.CustomPrintAdapter;
import com.vignesh.barcode.commodity.BillProcess;
import com.vignesh.barcode.commodity.Commodity;
import com.vignesh.barcode.commodity.CommodityOperationType;
import com.vignesh.barcode.databases.DatabaseManager;
import com.vignesh.barcode.extras.AppConstants;
import com.vignesh.barcode.extras.DateUtils;
import com.vignesh.barcode.extras.FragmentIntentIntegrator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysadmin on 23/3/17.
 */

public class NewBillFragment extends  BaseFragment implements View.OnClickListener,View.OnFocusChangeListener {

    TextView textView,tv_name, tv_mrp;
    Button scan,clear,total,next,print,newbill,one,two,three,four,five,six,seven,eight,nine,zero,search;
    EditText commodityid,quantity,calculating;
    ListView listView;
    int id;
    BillAdapter mBillAdapter;
    List<Commodity> billProcessList;

    SQLiteDatabase db;
    Cursor cursor = null;
    EditText editText = null;
    String check_value;

    public NewBillFragment(){

    }
    public static NewBillFragment newInstance(String aTitle) {
        NewBillFragment fragment = new NewBillFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mParentView = inflater.inflate(R.layout.fr_newbilling, container, false);

        commodityid = (EditText) mParentView.findViewById(R.id.et_comid);
        commodityid.setText(SessionStore.scancode);

        quantity = (EditText) mParentView.findViewById(R.id.howmuch);
        commodityid.setOnFocusChangeListener(this);
        quantity.setOnFocusChangeListener(this);
        textView = (TextView) mParentView.findViewById(R.id.totalamount);
        tv_name = (TextView) mParentView.findViewById(R.id.com_name);
        tv_mrp = (TextView) mParentView.findViewById(R.id.com_mrp);

        one = (Button) mParentView.findViewById(R.id.one);
        two = (Button) mParentView.findViewById(R.id.two);
        three = (Button) mParentView.findViewById(R.id.three);
        four = (Button) mParentView.findViewById(R.id.four);
        five = (Button) mParentView.findViewById(R.id.five);
        six = (Button) mParentView.findViewById(R.id.six);
        seven = (Button) mParentView.findViewById(R.id.seven);
        eight = (Button) mParentView.findViewById(R.id.eight);
        nine = (Button) mParentView.findViewById(R.id.nine);
        zero = (Button) mParentView.findViewById(R.id.zero);
        search = (Button) mParentView.findViewById(R.id.search1);

        scan = (Button) mParentView.findViewById(R.id.scan);
        clear = (Button) mParentView.findViewById(R.id.clear);
        newbill = (Button) mParentView.findViewById(R.id.biil_process);
        next = (Button) mParentView.findViewById(R.id.next);
        total = (Button) mParentView.findViewById(R.id.total);
        print = (Button) mParentView.findViewById(R.id.print);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);

        clear.setOnClickListener(this);
        scan.setOnClickListener(this);
        next.setOnClickListener(this);
        print.setOnClickListener(this);
        total.setOnClickListener(this);
        newbill.setOnClickListener(this);
        search.setOnClickListener(this);

        billProcessList = new ArrayList<>();
        listView = (ListView) mParentView.findViewById(R.id.bill_report);
        db = DatabaseManager.getInstance().openDatabase();
        return mParentView;

    }

    @Override
    public void onClick(View v) {
        String empty = "" ;
        switch (v.getId()){
            case R.id.scan:
                doScan();
                break;
            case R.id.clear:
                commodityid.setText("");
                quantity.setText("");
                break;
            case R.id.total:
                //getAmount();
                break;
            case R.id.next:
                pressnext();
                break;
            case R.id.new_bill:
                pressNewBill();
                break;
            case R.id.print:
                printDocument();
                break;
            case R.id.search1:
                getData(commodityid.getText().toString());
                break;

            case R.id.one:
                empty = "1";
                break;
            case R.id.two:
                empty = "2";
                break;
            case R.id.three:
                empty = "3";
                break;
            case R.id.four:
                empty = "4";
                break;
            case R.id.five:
                empty = "5";
                break;
            case R.id.six:
                empty = "6";
                break;
            case R.id.seven:
                empty = "7";
                break;
            case R.id.eight:
                empty = "8";
                break;
            case R.id.nine:
                empty = "9";
                break;
            case R.id.zero:
                empty = "0";
                break;
        }
        editText.setText(editText.getText().toString().concat(empty));
    }

    public void doScan() {
        FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(NewBillFragment.this);integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("never here");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            Toast.makeText(getActivity(), scanResult.getContents(), Toast.LENGTH_LONG).show();
        }
        // else continue with any other code you need in the method
    }

    public void pressNewBill() {
        String name = tv_name.getText().toString();
        String mrp = tv_mrp.getText().toString();
        String noOfItem = quantity.getText().toString();
        int totalAmount = Integer.parseInt(mrp) * Integer.parseInt(noOfItem);
        String date = DateUtils.getCurrentDate();
        String sql = "insert into bill (commodity_id ,total_amount , billed_at ,quantity  ) values (" + id + "," + totalAmount + ",'" + date + "'," + noOfItem + ")";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData (String commodity_id){

        check_value = commodity_id;

        String sql1 = "Select commodity_name,commmodity_id, mrp_unit from commodity where qr_code ='" + check_value +"'";
        try {
            cursor = db.rawQuery(sql1,null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex("commodity_name"));
                id = cursor.getInt(cursor.getColumnIndex("commmodity_id"));
                int mrp = cursor.getInt(cursor.getColumnIndex("mrp_unit"));
                tv_name.setText(name);
                tv_mrp.setText(String.valueOf(mrp));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
    }

    public void printDocument() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                printManager.print("My document", new CustomPrintAdapter(getActivity()), null);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                Toast.makeText(getActivity(), activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "printer not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void pressnext(){
        BillProcess billProcess = new BillProcess();
        BillProcessAsyncTask billProcessAsyncTask = new BillProcessAsyncTask(billProcess, CommodityOperationType.ADD);
        billProcessAsyncTask.execute();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        editText = (EditText) v;
        switch (v.getId()){
            case R.id.commodity_id:
                break;
            case R.id.quantity:
                break;
        }

    }

    public class BillProcessAsyncTask extends AsyncTask<Void, Void, List<Commodity>> {

        CommodityOperationType commodityOperationType;
        BillProcess billProcess;


        public BillProcessAsyncTask(BillProcess billProcess, CommodityOperationType commodityOperationType) {
            this.billProcess = billProcess;
            this.commodityOperationType = commodityOperationType;
        }

        @Override
        protected void onPreExecute() {
           check_value = commodityid.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected List<Commodity> doInBackground(Void... voids) {
            Cursor cursor = null;

            switch (commodityOperationType) {
                case CHECK:
                    String sql = "Select commodity_name, mrp, date , total_amount , quantity  from commodity where qr_code =" + check_value;
                    try {
                        String name,date;
                        int mrp,quantity,id;
                        cursor = db.rawQuery(sql,null);
                        if (cursor.moveToFirst()){
                            name = cursor.getString(cursor.getColumnIndex("commodity_name"));
                            mrp = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mrp_unit")));
                            quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                            date = DateUtils.getCurrentDate();
                            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("commodity_id")));
                            Commodity commodity1 = new Commodity();
                            commodity1.setCommodity_name(name);
                            commodity1.setCommodity_unit_mrp(mrp);
                            commodity1.setCommodity_stock_quantity(quantity);
                            commodity1.setCommodity_id(id);
                            billProcessList.add(commodity1);

                        }while (cursor.moveToNext());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        cursor.close();
                    }

            }
            return billProcessList;
        }

        @Override
        protected void onPostExecute(List<Commodity> billProcessList) {
            //mBillAdapter = new BillAdapter(getContext(), billProcessList);
            //listView.setAdapter(mBillAdapter);
            Toast.makeText(getContext(),"commodity add successful",Toast.LENGTH_LONG).show();
            //mBillAdapter.refresh(billProcessList);
        }
    }



}