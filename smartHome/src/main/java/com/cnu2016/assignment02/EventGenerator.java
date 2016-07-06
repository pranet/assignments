package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
public class EventGenerator {
    static class Job {
        int ID, timeStamp;
        boolean state;
        public Job (int ID, int timeStamp, boolean state) {
            this.ID = ID;
            this.timeStamp = timeStamp;
            this.state = state;
        }
    };
    public static ArrayList<Job> getSortedJobs(String filename) throws IOException, BadScheduleException {
        ArrayList<Job> jobs = new ArrayList<Job>();
        Scanner in = new Scanner(new FileReader(filename));
        while(in.hasNext()) {
            int ID = in.nextInt();
            int startTime = in.nextInt();
            boolean newState = in.nextBoolean();
            jobs.add(new Job(ID, startTime, newState));
        }
        in.close();
        Collections.sort(jobs, new Comparator<Job>() {
            public int compare(Job a, Job b) {
                return a.timeStamp - b.timeStamp;
            } 
        });
        return jobs;
    }
    public static ArrayList<Event> readData(String filename, ArrayList<Appliance> appliances) throws BadScheduleException, IOException {

        boolean state[] = new boolean[appliances.size()];
        int tim[] = new int[appliances.size()];
        
        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<Job> jobs = getSortedJobs(filename);
        for (Job j : jobs) {
            int ID = j.ID;
            int startTime = j.timeStamp;
            boolean newState = j.state;
            if (state[ID] == newState) {
                throw new BadScheduleException("Please enter a valid schedule");
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
        return events;
    }
}
