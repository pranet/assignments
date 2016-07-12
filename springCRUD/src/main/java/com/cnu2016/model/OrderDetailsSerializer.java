package com.cnu2016.model;

/**
 * Created by pranet on 12/07/16.
 */
public class OrderDetailsSerializer {
    private Integer productID;
    private Integer quantity;

    public OrderDetailsSerializer(Integer productID, Integer quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public OrderDetailsSerializer() {

    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
