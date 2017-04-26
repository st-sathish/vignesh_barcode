package com.vignesh.barcode.commodity;

/**
 * Created by sysadmin on 30/3/17.
 */

public class Commodity {

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    private String qr_code;



    public void setCommodity_unit_mrp(int commodity_unit_mrp) {
        this.commodity_unit_mrp = commodity_unit_mrp;
    }

    public void setCommodity_stock_quantity(int commodity_stock_quantity) {
        this.commodity_stock_quantity = commodity_stock_quantity;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }



    public int getCommodity_unit_mrp() {
        return commodity_unit_mrp;
    }

    public int getCommodity_stock_quantity() {
        return commodity_stock_quantity;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    int commodity_stock_quantity;
    int commodity_unit_mrp;

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    int commodity_id;
    String commodity_name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

}
