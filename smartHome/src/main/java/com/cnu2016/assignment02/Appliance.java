package com.cnu2016.assignment02;

abstract public class Appliance {
    boolean isOn;
    int maxOnTime;
    abstract void start();
    abstract void stop();
}