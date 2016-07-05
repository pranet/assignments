package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
public class EventGenerator {
    static final int TOTAL_APPLIANCES = 25; 
    public static ArrayList<Event> readData(String filename) throws IOException {
        
        //need to hard code types. for now type 0 is a water heater
        int type[] = new int[TOTAL_APPLIANCES];
        type[0] = 1;
        //hard coding ends
        
        boolean state[] = new boolean[TOTAL_APPLIANCES];
        int tim[] = new int[TOTAL_APPLIANCES];
        ArrayList<Event> events = new ArrayList<Event>();
        Scanner in = new Scanner(new FileReader(filename));
        while(in.hasNext()) {
            int ID = in.nextInt();
            int startTime = in.nextInt();
            boolean newState = in.nextBoolean();
            if (state[ID] == newState) {
                //throw an exception here
            }
            else {
                state[ID] = newState;
                tim[ID] = startTime;
            }
            events.add(new Event(startTime, ID));
        }
        //Explicity shut off all water heaters which were left on 
        for (int i = 0; i < TOTAL_APPLIANCES; ++i) {
            if (type[i] == 1 && state[i] == true) {
                events.add(new Event(tim[i] + 2, i));
            }
        }
        Collections.sort(events, new Comparator<Event>() {
            public int compare(Event a, Event b) {
                return a.timeStamp - b.timeStamp;
            } 
        });
        in.close();
        return events;
    }
}
