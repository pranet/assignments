package com.cnu2016.assignment02;
import java.io.*;
import java.util.*;
public class Main {
    static final int TOTAL_APPLIANCES = 3;
    public static void main(String args[]) throws IOException {
        boolean state[] = new boolean[TOTAL_APPLIANCES];
        ArrayList<Event> events = EventGenerator.readData("/projects/assignments/smartHome/input.txt");
        Timer timer = new Timer();
        events.forEach( e -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    state[e.ID] ^= true;
                    System.out.printf("At time %d\n", e.timeStamp);
                    for (int i = 0; i < TOTAL_APPLIANCES; ++i) {
                        System.out.printf("%d %b\n", i, state[i]);
                    }
                }
            }, e.timeStamp * 1000);
        });
    }
}
