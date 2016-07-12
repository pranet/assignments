package com.cnu2016.controller;

import com.cnu2016.model.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
      * Date will be decided when order is submitted.
      * Status : "In Cart"
      * CANCELLED : UserID : will be extracted from Users object. Current support is only for ID
      */
    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public ResponseEntity<?> postNewOrder(/*@RequestBody Users user*/) {
        Orders orders = new Orders();
//        if (user != null && user.getUserID() != null) {
//            user = usersRepository.findOne(user.getUserID());
//            if (user == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such user");
//            }
//            orders.setUser(user);
//        }
        orders.setStatus("In Cart");
        return ResponseEntity.status(HttpStatus.OK).body(ordersRepository.save(orders));
    }

    /**
     * Adds to an existing order.
     * Will return 404 if productID or quantity is missing.
     * Will fetch price from product table
     * Will check if enough quantity is available
     * TODO : Will adjust inventory only during checkout
     */
    @RequestMapping(value = "/api/order/{orderID}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity<?> addToOrder(@RequestBody OrderDetailsSerializer orderDetailsSerializer, @PathVariable Integer orderID) {
        Orders order = ordersRepository.findOne(orderID);
        if (order == null || order.getStatus().equals("Deleted") == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid order id");
        }
        if (orderDetailsSerializer == null || orderDetailsSerializer.getQuantity() == null || orderDetailsSerializer.getProductID() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data");
        }
        Product product = productRepository.findOne(orderDetailsSerializer.getProductID());
        if (order == null || product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data");
        }
        if (order.getStatus().equals("In Cart") == false || product.getQuantityInStock() < orderDetailsSerializer.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order or insufficient stock");
        }
        OrderDetails orderDetails = new OrderDetails(
            order, product, orderDetailsSerializer.getQuantity(), product.getSellPrice(), product.getBuyPrice()
        );
        orderDetails = orderDetailsRepository.save(orderDetails);
        // adjust inventory
        product.setQuantityInStock(product.getQuantityInStock() - orderDetailsSerializer.getQuantity());
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    /**
     * Checkout method
     * TODO : Will adjust inventory here, instead of during adding products
     */
    @RequestMapping(value = "/api/order/{orderID}", method = RequestMethod.PATCH)
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

    @RequestMapping(value = "/api/order/{orderID}", method = RequestMethod.DELETE)
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
