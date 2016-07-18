package com.cnu2016.model;

/**
 * Created by pranet on 13/07/16.
 */
public class UserSerializer {
    private String user_name;
    private String address;

    private String status;

    public UserSerializer(String user_name, String address, String status) {
        this.user_name = user_name;
        this.address = address;
        this.status = status;
    }
    public UserSerializer() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
