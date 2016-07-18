package com.cnu2016.controller;

import com.cnu2016.Application;
import com.cnu2016.model.Orders;
import com.cnu2016.model.Product;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.put;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.*;

/**
 * Created by pranet on 17/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class ProductControllerTest {
    private final String baseURL = "http://localhost:8080/api/";
    private final String productsURL = baseURL + "products";
    private final String productsURLWithID = baseURL + "products/{id}";
    private final String healthURL = baseURL + "health";

    @Autowired
    private ProductRepository productRepository;
    private int product1ID;
    private int product2ID;
    private int randomProductID;
    private Product product1;
    private Product product2;
    private Product product3;
    Map<String, Object> requestData;
    Map<String, Object> patchRequestData;
    Map<String, Object> emptyPatchRequestData;

    Map<String, Object> putRequestData;
    @Before
    public void setUp() throws Exception {
        product1 = new Product(-1, "testProduct1", "testProduct1", 1);
        product2 = new Product(-1, "testProduct2", "testProduct2", 2);
        product3 = new Product(-1, "testProduct3", "testProduct3", 3);

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        product1ID = product1.getProductID();
        product2ID = product2.getProductID();
        randomProductID = product1ID + 535334;

        requestData = new HashMap<>();
        requestData.put("code", product3.getProductCode());
        requestData.put("description", product3.getProductDescription());
        requestData.put("qty", product3.getQuantityInStock());

        patchRequestData = requestData;
        putRequestData = requestData;
        emptyPatchRequestData = new HashMap<>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getHealth() throws Exception {
        given().
                contentType("application/json").
                when().
                get(healthURL).
                then().
                statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getAllProducts() throws Exception {
        given().
                contentType("application/json").
                when().
                get(productsURL).
                then().
                statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getProduct() throws Exception {
        given().
                contentType("application/json").
                when().
                get(productsURLWithID, product1ID).
                then().
                statusCode(HttpStatus.OK.value());
        given().
                contentType("application/json").
                when().
                get(productsURLWithID, randomProductID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteProduct() throws Exception {
        /* Delete invalid product */
        given().
                contentType("application/json").
                when().
                delete(productsURLWithID, randomProductID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* Delete existing product */
        given().
                contentType("application/json").
                when().
                delete(productsURLWithID, product2ID).
                then().
                statusCode(HttpStatus.OK.value());
        /* Delete deleted product */
        given().
                contentType("application/json").
                when().
                delete(productsURLWithID, product2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* check deleted product */
        given().
                contentType("application/json").
                when().
                get(productsURLWithID, product2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* patch deleted product */
        given().
                contentType("application/json").
                body(patchRequestData).
                when().
                patch(productsURLWithID, product2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        given().
                contentType("application/json").
                body(putRequestData).
                when().
                put(productsURLWithID, product2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    public void createProductTest() {

        given().
                contentType("application/json").
                body(requestData).
                when().
                post(productsURL).
                then().
                statusCode(HttpStatus.CREATED.value()).
                body("code", Matchers.is(product3.getProductCode())).
                body("description", Matchers.is(product3.getProductDescription())).
                body("qty", Matchers.is(product3.getQuantityInStock())).
                extract().response();
    }

    @Test
    public void putProductTest() {

        given().
                contentType("application/json").
                body(putRequestData).
                when().
                put(productsURLWithID, randomProductID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        given().
                contentType("application/json").
                body(putRequestData).
                when().
                put(productsURLWithID, product1ID).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is(putRequestData.get("code"))).
                body("description", Matchers.is(putRequestData.get("description"))).
                body("qty", Matchers.is(putRequestData.get("qty"))).
                body("id", Matchers.is(product1ID)).
                extract().response();

    }

    @Test
    public void patchProductTest() {

        given().
                contentType("application/json").
                body(putRequestData).
                when().
                patch(productsURLWithID, randomProductID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        given().
                contentType("application/json").
                body(patchRequestData).
                when().
                patch(productsURLWithID, product1ID).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is(patchRequestData.get("code"))).
                body("description", Matchers.is(patchRequestData.get("description"))).
                body("qty", Matchers.is(patchRequestData.get("qty"))).
                body("id", Matchers.is(product1ID)).
                extract().response();
        given().
                contentType("application/json").
                body(emptyPatchRequestData).
                when().
                patch(productsURLWithID, product1ID).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is(requestData.get("code"))).
                body("description", Matchers.is(requestData.get("description"))).
                body("qty", Matchers.is(requestData.get("qty"))).
                body("id", Matchers.is(product1ID)).
                extract().response();
    }
}