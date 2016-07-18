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
import static org.junit.Assert.*;

/**
 * Created by pranet on 18/07/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class OrdersControllerTest {
    private final String baseURL = "http://localhost:8080/api/";
    private final String productsURL = baseURL + "products";
    private final String productsURLWithID = baseURL + "products/{id}";
    private final String ordersURL = baseURL + "orders";
    private final String ordersURLWithID = baseURL + "orders/{id}";
    private final String orderLineItemURL = ordersURLWithID + "/orderLineItem";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    private Map<String, Object> emptyOrderRequest;
    private Map<String, Object> incompleteOrderRequest1;
    private Map<String, Object> incompleteOrderRequest2;
    private Map<String, Object> invalidProductOrderRequest;
    private Map<String, Object> validProductOrderRequest;
    private Map<String, Object> validProduct2OrderRequest;

    private Map<String, Object> validCheckoutRequest;

    private Orders order1;
    private Orders order2;
    private Orders order3;

    private Product product;
    private Product product2;

    private int order1ID;
    private int order2ID;
    private int order3ID;
    private int randomOrderID;

    @Before
    public void setUp() throws Exception {
        int productQuantity = 100;
        product = new Product(-1, "testProduct for Orders", "testProduct for Orders", productQuantity);
        product = productRepository.save(product);

        product2 = new Product(-1, "testProduct for Orders", "testProduct for Orders", productQuantity);
        product2 = productRepository.save(product2);

        order1 = new Orders();
        order2 = new Orders();
        order3 = new Orders();
        order1.setStatus("In Cart");
        order2.setStatus("In Cart");
        order3.setStatus("In Cart");
        order1 = ordersRepository.save(order1);
        order2 = ordersRepository.save(order2);
        order3 = ordersRepository.save(order3);
        order1ID = order1.getOrderID();
        order2ID = order2.getOrderID();
        order3ID = order3.getOrderID();
        randomOrderID = order1ID + 535334;

        emptyOrderRequest = new HashMap<>();

        incompleteOrderRequest1 = new HashMap<>();
        incompleteOrderRequest1.put("product_id", product.getProductID());

        incompleteOrderRequest2 = new HashMap<>();
        incompleteOrderRequest2.put("qty", productQuantity);

        invalidProductOrderRequest = new HashMap<>();
        invalidProductOrderRequest.put("product_id", randomOrderID);
        invalidProductOrderRequest.put("qty", productQuantity);

        validProductOrderRequest = new HashMap<>();
        validProductOrderRequest.put("product_id", product.getProductID());
        validProductOrderRequest.put("qty", productQuantity);

        /* designed so that two of these requests will exceed productQuantity */
        validProduct2OrderRequest = new HashMap<>();
        validProduct2OrderRequest.put("product_id", product2.getProductID());
        validProduct2OrderRequest.put("qty", productQuantity / 2 + 1);

        validCheckoutRequest = new HashMap<>();
        validCheckoutRequest.put("user_name", "randomString");
        validCheckoutRequest.put("status", "Shipped");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createOrders() throws Exception {
        given().
                contentType("application/json").
                when().
                post(ordersURL).
                then().
                statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void getOrder() throws Exception {
        given().
                contentType("application/json").
                when().
                get(ordersURLWithID, order1ID).
                then().
                statusCode(HttpStatus.OK.value());
        given().
                contentType("application/json").
                when().
                get(ordersURLWithID, randomOrderID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteOrder() throws Exception {
        /* Delete invalid order */
        given().
                contentType("application/json").
                when().
                delete(ordersURLWithID, randomOrderID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* Delete existing order */
        given().
                contentType("application/json").
                when().
                delete(ordersURLWithID, order2ID).
                then().
                statusCode(HttpStatus.OK.value());
        /* Delete deleted order */
        given().
                contentType("application/json").
                when().
                delete(ordersURLWithID, order2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* check deleted order */
        given().
                contentType("application/json").
                when().
                get(ordersURLWithID, order2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* add item to deleted order */
        given().
                contentType("application/json").
                body(validProductOrderRequest).
                when().
                post(orderLineItemURL, order2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        /* checkout deleted order */
        given().
                contentType("application/json").
                body(validCheckoutRequest).
                when().
                patch(ordersURLWithID, order2ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testAddToCartInvalidCases() throws Exception {
        given().
                contentType("application/json").
                body(validProductOrderRequest).
                when().
                post(orderLineItemURL, randomOrderID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
        given().
                contentType("application/json").
                body(emptyOrderRequest).
                when().
                post(orderLineItemURL, order1ID).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value());
        given().
                contentType("application/json").
                body(incompleteOrderRequest1).
                when().
                post(orderLineItemURL, order1ID).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value());
        given().
                contentType("application/json").
                body(incompleteOrderRequest2).
                when().
                post(orderLineItemURL, order1ID).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value());
        given().
                contentType("application/json").
                body(invalidProductOrderRequest).
                when().
                post(orderLineItemURL, order1ID).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void validTransactionTest() throws Exception {
        given().
                contentType("application/json").
                body(validProductOrderRequest).
                when().
                post(orderLineItemURL, order1ID).
                then().
                statusCode(HttpStatus.CREATED.value());

        given().
                contentType("application/json").
                body(validCheckoutRequest).
                when().
                patch(ordersURLWithID, order1ID).
                then().
                statusCode(HttpStatus.OK.value());
    }

    /**
     * Two calls of valid will lead to insufficient stock.
     */
    @Test
    public void insufficientStockTest() throws Exception {
        given().
                contentType("application/json").
                body(validProduct2OrderRequest).
                when().
                post(orderLineItemURL, order3ID).
                then().
                statusCode(HttpStatus.CREATED.value());
        given().
                contentType("application/json").
                body(validProduct2OrderRequest).
                when().
                post(orderLineItemURL, order3ID).
                then().
                statusCode(HttpStatus.CREATED.value());
        given().
                contentType("application/json").
                body(validCheckoutRequest).
                when().
                patch(ordersURLWithID, order3ID).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value());
    }
}