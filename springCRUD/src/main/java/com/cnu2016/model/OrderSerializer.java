package com.cnu2016.model;

/**
 * Created by pranet on 13/07/16.
 */
public class OrderSerializer {
    private Integer id;

    public OrderSerializer(Integer id) {
        this.id = id;
    }

    public OrderSerializer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
