package com.vignesh.barcode.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;

/**
 * Created by sysadmin on 25/4/17.
 */

public class CustomPrintAdapter extends PrintDocumentAdapter {

    Context mContext;

    public CustomPrintAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {

    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
