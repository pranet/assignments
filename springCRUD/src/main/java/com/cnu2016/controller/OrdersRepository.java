package com.cnu2016.controller;

import com.cnu2016.model.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.*;
/**
 * Created by pranet on 09/07/16.
 */
public interface OrdersRepository extends CrudRepository<Orders, Integer> {

}
