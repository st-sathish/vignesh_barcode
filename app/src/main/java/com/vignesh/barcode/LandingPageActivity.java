package com.vignesh.barcode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vignesh.barcode.fragment.AddNewCommodityFragment;
import com.vignesh.barcode.fragment.DailyReportFragment;
import com.vignesh.barcode.fragment.MenuFragment;
import com.vignesh.barcode.fragment.NewBillFragment;
import com.vignesh.barcode.fragment.StockFragment;
import com.vignesh.barcode.fragment.UpdateCommodityFragment;
import com.vignesh.barcode.fragment.TermsAndServicesFragment;



/**
 * Created by sysadmin on 23/3/17.
 */

public class LandingPageActivity extends BaseActivity {

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
    Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        displayView(FRAGMENT_MENU, "menu");
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
                fragment = UpdateCommodityFragment.newInstance(title);
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
        if (SessionStore.scancode.equals("")) {
            Toast.makeText(this, "Please do Scan", Toast.LENGTH_LONG).show();
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
}