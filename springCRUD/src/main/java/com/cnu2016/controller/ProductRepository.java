package com.cnu2016.controller;
import java.util.*;
import com.cnu2016.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pranet on 07/07/16.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
//    List<Product> findByLastName(String lastName);
}
