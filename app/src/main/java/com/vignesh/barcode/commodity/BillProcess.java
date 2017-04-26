package com.vignesh.barcode.commodity;

/**
 * Created by sysadmin on 22/4/17.
 */

public class BillProcess extends Commodity {

    private int billId, quantity,totalAmount;
    private String billedAt;

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBillId() {
        return billId;
    }

    public String getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(String billedAt) {
        this.billedAt = billedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
