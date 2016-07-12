package com.cnu2016.model;

import javax.persistence.*;

/**
 * Created by pranet on 09/07/16.
 */
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderDetailsID;
    @ManyToOne
    @JoinColumn(name = "orderID")
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
    private Integer quantity;
    private Integer sellPrice;
    private Integer buyPrice;

    public OrderDetails(Orders orders, Product product, Integer quantity, Integer sellPrice, Integer buyPrice) {
        this.orders = orders;
        this.product = product;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public OrderDetails() {

    }

    public Integer getOrderDetailsID() {
        return orderDetailsID;
    }

    public void setOrderDetailsID(Integer orderDetailsID) {
        this.orderDetailsID = orderDetailsID;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }
}
