package com.cnu2016.controller;

import com.cnu2016.model.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pranet on 12/07/16.
 */
public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findDistinctUsersByCustomerName(String customerName);
}
