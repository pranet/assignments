package com.cnu2016.model;

/**
 * Created by pranet on 12/07/16.
 */
public class OrderDetailsSerializer {
    private Integer product_id;
    private Integer qty;

    public OrderDetailsSerializer(Integer product_id, Integer qty) {
        this.product_id = product_id;
        this.qty = qty;
    }

    public OrderDetailsSerializer() {

    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
