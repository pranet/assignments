package com.cnu2016.controller;

import com.cnu2016.model.OrderDetails;
import com.cnu2016.model.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by pranet on 09/07/16.
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {
//    public List<OrderDetails> findByOrder(Orders order);
}
