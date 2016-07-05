package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
public class EventGenerator {
    static final int TOTAL_APPLIANCES = 25; 
    
    public static ArrayList<Event> readData(String filename) throws IOException {
        //initial state of the applicances : get somehow?
        boolean state[] = new boolean[TOTAL_APPLIANCES];
        Scanner in = new Scanner(new FileReader(filename));
        ArrayList<Event> events = new ArrayList<Event>();
        while(in.hasNext()) {
            int ID = in.nextInt();
            int startTime = in.nextInt();
            boolean newState = in.nextBoolean();
            if (state[ID] == newState) {
                //throw an exception here
            }
            else {
                state[ID] = newState;
            }
            events.add(new Event(startTime, ID));
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
