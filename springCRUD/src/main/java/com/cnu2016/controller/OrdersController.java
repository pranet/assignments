package com.cnu2016.controller;

import com.cnu2016.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pranet on 11/07/16.
 */
@RestController
public class OrdersController {
    @Autowired
    public OrdersRepository ordersRepository;
    @Autowired
    public OrderDetailsRepository orderDetailsRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    UsersRepository usersRepository;
    /**
     * Creates a new order and returns it as response.
     * Response: {id:}
     * Will set status to "In Cart"
     */
    @RequestMapping(value = "/api/orders", method = RequestMethod.POST)
    public ResponseEntity<?> postNewOrder() {
        Orders orders = new Orders();
        orders.setStatus("In Cart");
        orders = ordersRepository.save(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderSerializer(orders.getOrderID()));
    }
    /**
     * Adds to an existing order.
     * Will return NOT_FOUND if any of the following is true
     *      1) Order is missing
     *      2) ProductID is missing
     *      3) Quantity is missing
     * Will return BAD_REQUEST if status is not "In Cart"
     * Will fetch price from product table
     */
    @RequestMapping(value = "/api/orders/{orderID}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity<?> addToOrder(@RequestBody OrderDetailsSerializer orderDetailsSerializer, @PathVariable Integer orderID) {
        Orders order = ordersRepository.findOne(orderID);
        if (order == null || order.getStatus().equals("Deleted") == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid order id");
        }
        if (orderDetailsSerializer == null || orderDetailsSerializer.getQty() == null || orderDetailsSerializer.getProduct_id() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data");
        }
        Product product = productRepository.findOne(orderDetailsSerializer.getProduct_id());
        if (order == null || product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data");
        }
        if (order.getStatus().equals("In Cart") == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order or insufficient stock");
        }
        OrderDetails orderDetails = new OrderDetails(
            order, product, orderDetailsSerializer.getQty(), product.getSellPrice(), product.getBuyPrice()
        );
        orderDetails = orderDetailsRepository.save(orderDetails);
        // adjust inventory
//        product.setQuantityInStock(product.getQuantityInStock() - orderDetailsSerializer.getQty());
//        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    /**
     * Request: {user_name, address}
     * Will return NOT_FOUND if order corresponding to orderID does not exist
     * Will return BAD_REQUEST if
     *      1) Order status is not "In Cart"
     *      2) user_name is null
     *      3) user_name does not exist in Users table
     * Will set status to "Checkout"
     * Will set date
     * Will set user
     */
    @RequestMapping(value = "/api/orders/{orderID}", method = RequestMethod.PATCH)
    public ResponseEntity<?> checkoutOrder(@PathVariable Integer orderID, @RequestBody UserSerializer userSerializer) {
        Orders order = ordersRepository.findOne(orderID);
        if (order == null || order.getStatus().equals("Deleted") == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid order id");
        }
        if (order.getStatus().equals("In Cart") == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order");
        }
        if (userSerializer == null || userSerializer.user_name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not specified");
        }
        Users user = usersRepository.findDistinctUsersByCustomerName(userSerializer.user_name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not specified");
        }
        order.setUser(user);
        order.setStatus("Checkout");
        order.setOrderDate(new Date());
        ordersRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    /**
     * Will delete an existing order from the DB.
     * Will return NOT_FOUND if order does not exist.
     * Will set status to "Deleted"
     */
    @RequestMapping(value = "/api/orders/{orderID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder (@PathVariable Integer orderID) {
        Orders order = ordersRepository.findOne(orderID);
        if (order == null || order.getStatus().equals("Deleted") == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid order id");
        }
        order.setStatus("Deleted");
        order = ordersRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
