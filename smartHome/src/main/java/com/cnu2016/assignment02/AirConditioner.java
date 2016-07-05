package com.cnu2016.assignment02;

public class AirConditioner extends Appliance {
    void stop() {
        isOn = false;
    }
    void start () {
        isOn = true;
    }
}
