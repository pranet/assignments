package com.cnu2016.model;

/**
 * Created by pranet on 08/07/16.
 */
public class ProductSerializer {
    private Integer id;
    private String code;
    private String description;

    public ProductSerializer(Product p) {
        this.id = p.getProductID();
        this.code = p.getProductCode();
        this.description = p.getProductDescription();
    }

    public ProductSerializer() {
    }

    public ProductSerializer(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
