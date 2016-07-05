package com.cnu2016.assignment02;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String args[]) throws IOException {
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        appliances.add(new Appliance(Appliance.Type.AIRCONDITIONER));
        appliances.add(new Appliance(Appliance.Type.COOKINGOVEN));
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        
        ArrayList<Event> events = EventGenerator.readData("/projects/assignments/smartHome/input.txt", appliances);
        Timer timer = new Timer();
        events.forEach( e -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(e.appliance.getState() == true) {
                        e.appliance.stop();
                    }
                    else {
                        e.appliance.start();
                    }
                    System.out.printf("At time %d\n", e.timeStamp);
                    appliances.forEach( a -> {
                        System.out.printf("%d %b\n", a.getID(), a.getState()); 
                    });
                }
            }, e.timeStamp * 1000);
        });
    }
}
