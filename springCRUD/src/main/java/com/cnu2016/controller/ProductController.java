package com.cnu2016.controller;
import java.util.*;
import com.cnu2016.model.Product;
import com.cnu2016.model.ProductSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by pranet on 08/07/16.
 */
@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
        List ret = new ArrayList<ProductSerializer>();
        for (Product p : productRepository.findAll()) {
            if (p.getAvailable() == TRUE) {
                ret.add(new ProductSerializer(p));
            }
        }
        return ResponseEntity.ok(ret);
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        Product product = productRepository.findOne(id);
        if (product == null || product.getAvailable() == FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
        return ResponseEntity.ok(new ProductSerializer(product));
    }
    /* id is ignored in POST, hence dummy value */
    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public ResponseEntity<?> postProduct(@RequestBody ProductSerializer p) {
        Product product = new Product(-1, p.getCode(), p.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductSerializer(productRepository.save(product)));
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putProduct(@RequestBody ProductSerializer p, @PathVariable int id) {
        Product product = productRepository.findOne(id);
        if (productRepository.findOne(id) == null || product.getAvailable() == FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
        product = productRepository.save(new Product(id, p.getCode(), p.getDescription()));
        return ResponseEntity.status(HttpStatus.OK).body(new ProductSerializer(product));
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchProduct(@RequestBody ProductSerializer p, @PathVariable int id) {
        Product product = productRepository.findOne(id);
        if (product == null || product.getAvailable() == FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
        if (p.getCode() == null) {
            p.setCode(product.getProductCode());
        }
        if (p.getDescription() == null) {
            p.setDescription(product.getProductDescription());
        }
        product = productRepository.save(new Product(id, p.getCode(), p.getDescription()));
        return ResponseEntity.status(HttpStatus.OK).body(new ProductSerializer(product));
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        Product product = productRepository.findOne(id);
        if (product == null || product.getAvailable() == FALSE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
        product.setAvailable(false);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }
}