package com.cnu2016;

import com.cnu2016.controller.ProductRepository;
import com.cnu2016.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

/**
 * Created by pranet on 07/07/16.
 */
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ProductRepository repository) {
        return (args) -> {
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Product p : repository.findAll()) {
                log.info(p.toString());
            }
            log.info("");
        };
    }
}
