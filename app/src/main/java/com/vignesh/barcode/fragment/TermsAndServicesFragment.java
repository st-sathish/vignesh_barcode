package com.vignesh.barcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barcode.R;
import com.barcode.extras.AppConstants;

/**
 * Created by sysadmin on 23/3/17.
 */

public class TermsAndServicesFragment extends BaseFragment{

    public TermsAndServicesFragment(){

    }

    public static TermsAndServicesFragment newInstance(String aTitle) {
        TermsAndServicesFragment fragment = new TermsAndServicesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.INTENT_PARAM_ONE, aTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mParentView = inflater.inflate(R.layout.checkcommodity, container, false);
        return mParentView;
    }
}
