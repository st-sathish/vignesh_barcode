package com.vignesh.barcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vignesh.barcode.LandingPageActivity;
import com.vignesh.barcode.R;
import com.vignesh.barcode.extras.AppConstants;


/**
 * Created by sysadmin on 25/3/17.
 */

public class MenuFragment extends BaseFragment implements View.OnClickListener {


    public MenuFragment(){

    }

    public static MenuFragment newInstance(String aTitle) {
        MenuFragment fragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.menu, container, false);
        mParentView.findViewById(R.id.new_bill).setOnClickListener(this);
        mParentView.findViewById(R.id.daily_report).setOnClickListener(this);
        mParentView.findViewById(R.id.newCommodity).setOnClickListener(this);
        //mParentView.findViewById(R.id.btnTerms).setOnClickListener(this);
        mParentView.findViewById(R.id.update_commodity).setOnClickListener(this);
        mParentView.findViewById(R.id.stock).setOnClickListener(this);
        return mParentView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_bill:
                switchFragment(LandingPageActivity.FRAGMENT_NEW_BILL, "New Bill");
                break;
            case R.id.daily_report:
                switchFragment(LandingPageActivity.FRAGMENT_DAILY_REPORT, "Daily Report");
                break;
            case R.id.newCommodity:
                switchFragment(LandingPageActivity.FRAGMENT_ADD_NEW_COMMODITY,"Add New Commodity");
                break;
            case R.id.btnTerms:
                switchFragment(LandingPageActivity.FRAGMENT_TERMS_AND_CONDITIONS,"Terms And Conditions");
                break;
            case R.id.update_commodity:
                switchFragment(LandingPageActivity.FRAGMENT_UPDATE_COMMODITY,"Update Commodity");
                break;
            case R.id.stock:
                switchFragment(LandingPageActivity.FRAGMENT_STOCK,"Stock");
                break;
        }
    }
}
