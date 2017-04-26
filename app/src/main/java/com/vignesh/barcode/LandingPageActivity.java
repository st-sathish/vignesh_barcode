package com.vignesh.barcode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vignesh.barcode.fragment.AddNewCommodityFragment;
import com.vignesh.barcode.fragment.DailyReportFragment;
import com.vignesh.barcode.fragment.MenuFragment;
import com.vignesh.barcode.fragment.NewBillFragment;
import com.vignesh.barcode.fragment.StockFragment;
import com.vignesh.barcode.fragment.TermsAndServicesFragment;
import com.vignesh.barcode.fragment.UpdateCommodityFargment;


/**
 * Created by sysadmin on 23/3/17.
 */

public class LandingPageActivity extends BaseActivity  implements View.OnClickListener {

    public static final int FRAGMENT_MENU = 0;
    public static final int FRAGMENT_NEW_BILL = 1;
    public static final int FRAGMENT_DAILY_REPORT = 2;
    public static final int FRAGMENT_ADD_NEW_COMMODITY = 3;
    public static final int FRAGMENT_TERMS_AND_CONDITIONS = 4;
    public static final int FRAGMENT_UPDATE_COMMODITY = 5;
    public static final int FRAGMENT_STOCK = 6;
    private static final String Menu_FRAGMENT_TITLE = "Barcode Scanning";

    Fragment fragment = null;
    String title = null, url = "";
    boolean addToBackstack = true;
    Button button1,button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        displayView(FRAGMENT_MENU, "menu");

        //button1 = (Button) findViewById(R.id.butQR);
        //button2 = (Button) findViewById(R.id.butProd);
        //button3 = (Button) findViewById(R.id.butOther);

        //button3 = (Button) findViewById(R.id.next);
        /*ActivityCompat.requestPermissions(LandingPageActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);*/
        //button2.setOnClickListener(this);
       // button3.setOnClickListener(this);

    }

    public void displayView(int position, String aTitle) {
        Fragment fragment = null;
        String title = null, url = "";
        boolean addToBackstack = true;
        if (null == aTitle) {
            title = getString(R.string.app_name);
        } else {
            title = aTitle;
        }

        switch (position) {
            case FRAGMENT_MENU:
                title = Menu_FRAGMENT_TITLE;
                fragment = MenuFragment.newInstance(title);
                break;
            case FRAGMENT_NEW_BILL:
                fragment = NewBillFragment.newInstance(title);
                break;
            case FRAGMENT_DAILY_REPORT:
                fragment = DailyReportFragment.newInstance(title);
                break;
            case FRAGMENT_ADD_NEW_COMMODITY:
                fragment = AddNewCommodityFragment.newInstance(title);
                break;
            case FRAGMENT_TERMS_AND_CONDITIONS:
                fragment = TermsAndServicesFragment.newInstance(title);
                break;
            case FRAGMENT_UPDATE_COMMODITY:
                fragment = UpdateCommodityFargment.newInstance(title);
                break;
            case FRAGMENT_STOCK:
                fragment = StockFragment.newInstance(title);
                break;
        }
        switchContent(fragment, title, addToBackstack);
    }

    public void switchContent(Fragment fragment, String title, boolean aAddtoBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String backStateName = ft.getClass().getName();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (aAddtoBackstack)
            ft.addToBackStack(backStateName);
        ft.commit();
        //fragmentStack.add(fragment);
        setActionBarTitle(title);

    }

    public void goNext() {
        if(SessionStore.scancode.equals("")) {
            Toast.makeText(this,"Please do Scan",Toast.LENGTH_LONG).show();
           // return;
        }
        Fragment fragment = NewBillFragment.newInstance("New Bill");
        switchContent(fragment, "New Bill", false);
    }

    private void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(title);
        }
    }

    public void handleClick(View view){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0); //Barcode Scanner to scan for us
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String result = intent.getStringExtra("SCAN_RESULT_FORMAT");
                SessionStore.scancode = intent.getStringExtra("SCAN_RESULT");
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,"Press a button to start a scan.",Toast.LENGTH_LONG).show();
                Toast.makeText(this,"Scan cancelled.",Toast.LENGTH_LONG).show();
            }
        }
    }

    private static final int REQUEST_WRITE_PERMISSION = 20;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(LandingPageActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            /*case R.id.butProd:
                handleClick(view);
                break;
            case R.id.next:
                goNext();
                break;*/
        }
    }
}