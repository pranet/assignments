package com.cnu2016.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by pranet on 07/07/16.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productID;
    private String productName;
    private String productCode;
    private String productDescription;
    private int quantityInStock;
    private int buyPrice;
    private int sellPrice;
    private Boolean isAvailable;

    public Product(int productID, String productCode, String productDescription) {
        this.productID = productID;
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.isAvailable = true;
    }

    public Product(String productName, String productCode, String productDescription, int quantityInStock, int buyPrice, int sellPrice, Boolean isAvailable, int ID) {
        this.productName = productName;
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.isAvailable = isAvailable;
        this.productID = ID;
    }

    public Product() {
    }



//    @Override
//    public String toString() {
//        return "Product{" +
//                "ID=" + productID +
//                ", productName='" + productName + '\'' +
//                '}';
//    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
