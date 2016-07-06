package com.cnu2016.assignment02;
import java.io.*;
import java.util.*;
public class Main {
    public static ArrayList<Appliance> populate() {
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        appliances.add(new Appliance(Appliance.Type.AIRCONDITIONER));
        appliances.add(new Appliance(Appliance.Type.COOKINGOVEN));
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        return appliances;
    }
    public static void scheduleAndRun(ArrayList<Event> events, ArrayList<Appliance> appliances) {
        Timer timer = new Timer();
        events.forEach( e -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    e.appliance.toggle();
                    System.out.printf("At time %d\n", e.timeStamp);
                    appliances.forEach( a -> {
                        System.out.println(a);
                    });
                }
            }, e.timeStamp * 1000);
        });    
    }
    public static void main(String args[]) throws InterruptedException, IOException, BadScheduleException {
        ArrayList<Appliance> appliances = populate();
        ArrayList<Event> events = EventGenerator.readData("/projects/assignments/smartHome/input.txt", appliances);
        scheduleAndRun(events, appliances);
        Thread.sleep(10 * 1000);
    }
}