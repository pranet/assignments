package com.cnu2016;

import com.cnu2016.controller.ProductRepository;
import com.cnu2016.model.Product;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pranet on 07/07/16.
 */
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) throws Exception {
        SpringApplication.run(Application.class);
    }
}
