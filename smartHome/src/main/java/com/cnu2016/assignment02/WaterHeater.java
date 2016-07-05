package com.cnu2016.assignment02;

public class WaterHeater extends Appliance {
    void stop() {
        isOn = false;
    }
    void start () {
        isOn = true;
    }
}
