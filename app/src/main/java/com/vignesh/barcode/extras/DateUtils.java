package com.vignesh.barcode.extras;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sysadmin on 25/4/17.
 */

public class DateUtils {
    public  static String getCurrentDate(){
        DateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        dateFormatter.setLenient(false);
        Date today = new Date();
        return dateFormatter.format(today);
    }
}
