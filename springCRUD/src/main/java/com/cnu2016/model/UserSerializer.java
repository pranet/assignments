package com.cnu2016.model;

/**
 * Created by pranet on 13/07/16.
 */
public class UserSerializer {
    public String user_name;
    public String address;

    public UserSerializer(String user_name, String address) {
        this.user_name = user_name;
        this.address = address;
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
}
