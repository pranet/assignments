package com.cnu2016.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;
import java.util.*;
/**
 * Created by pranet on 09/07/16.
 */
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int orderID;
    private String status;
    private Date orderDate;

    @OneToMany(mappedBy = "orders")
    private Set<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private Users user;

    public Orders(int orderID, String status, Date orderDate, Set<OrderDetails> orderDetails, Users user) {
        this.orderID = orderID;
        this.status = status;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.user = user;
    }
    public Orders() {

    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
