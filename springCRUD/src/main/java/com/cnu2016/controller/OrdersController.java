package com.cnu2016.controller;

import com.cnu2016.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      * UserID : will be extracted from Users object. Current support is only for ID
      */
    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public ResponseEntity<?> postNewOrder(@RequestBody Users user) {
        Orders orders = new Orders();
        if (user != null && user.getUserID() != null) {
            user = usersRepository.findOne(user.getUserID());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such user");
            }
            orders.setUser(user);
        }
        orders.setStatus("In Cart");
        return ResponseEntity.status(HttpStatus.OK).body(ordersRepository.save(orders));
    }

    /**
     * Adds to an existing order.
     * Will return 404 if productID or quantity is missing.
     * Will fetch price from product table
     */
    @RequestMapping(value = "/api/order/{orderID}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity<?> addToOrder(@RequestBody OrderDetailsSerializer orderDetailsSerializer, @PathVariable Integer orderID) {
        if (orderDetailsSerializer == null || orderDetailsSerializer.getQuantity() == null || orderDetailsSerializer.getProductID() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data");
        }
        Orders order = ordersRepository.findOne(orderID);
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
     */
//    @RequestMapping(value = "/api/orders/{orderID}", method = RequestMethod.PATCH)
//    public ResponseEntity<?> checkoutOrder(@PathVariable Integer orderID) {
//
//        Orders order = ordersRepository.findOne(orderID);
//        if (order == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid order id");
//        }
//        if (order.getStatus().equals("In Cart") == false) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order");
//        }
//    }
}
