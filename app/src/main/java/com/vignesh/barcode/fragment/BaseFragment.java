package com.vignesh.barcode.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.vignesh.barcode.LandingPageActivity;


/**
 * Created by sysadmin on 23/3/17.
 */

public class BaseFragment extends Fragment {


    protected View mParentView;

    protected void switchFragment(int position, String title) {
        LandingPageActivity landingPageActivity = (LandingPageActivity) getActivity();
        if(landingPageActivity != null) {
            landingPageActivity.displayView(position, title);
        }
    }
}
