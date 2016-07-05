package com.cnu2016.assignment02;

public class CookingOven extends Appliance {
    void stop() {
        isOn = false;
    }
    void start () {
        isOn = true;
    }
}
