package com.cnu2016.controller;

import com.cnu2016.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pranet on 09/07/16.
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {
}
