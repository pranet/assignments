package com.cnu2016.controller;

import com.cnu2016.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cnu2016.model.Users;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * Created by pranet on 11/07/16.
 */
@RestController
public class OrdersController {
    @Autowired OrdersRepository ordersRepository;

     /**
     * Creates a new order and returns it as response.
     * Date will be decided when order is submitted.
     * Status : "In Cart"
     * UserID : will be extracted from Users object. Current support is only for ID
     */
    @RequestMapping(value = "/api/orders", method = RequestMethod.POST)
    public ResponseEntity<?> postNewOrder(@RequestBody Users user) {
        Orders orders = new Orders();
        if (user != null && user.getUserID() != 0)
            orders.setUser(user);
        orders.setStatus("In Cart");
        return ResponseEntity.status(HttpStatus.OK).body(ordersRepository.save(orders));
    }
}
