package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
public class EventGenerator { 
    public static ArrayList<Event> readData(String filename, ArrayList<Appliance> appliances) throws IOException {

        boolean state[] = new boolean[appliances.size()];
        int tim[] = new int[appliances.size()];
        
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
            events.add(new Event(startTime, appliances.get(ID)));
        }
        //Explicity shut off all water heaters which were left on 
        for (int i = 0; i < appliances.size(); ++i) {
            if (appliances.get(i).getType() == Appliance.Type.WATERHEATER && state[i] == true) {
                events.add(new Event(tim[i] + 2, appliances.get(i)));
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
