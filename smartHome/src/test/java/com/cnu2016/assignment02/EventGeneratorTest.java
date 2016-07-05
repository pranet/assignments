package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class EventGeneratorTest {
    @Test
    public void testWaterHeaterAutoShutOff() throws IOException, BadScheduleException {
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        String filename = "/projects/assignments/smartHome/src/test/java/testData/testWaterHeaterAutoShutOff.txt";
        ArrayList<Event> ret = EventGenerator.readData(filename, appliances);
        assertEquals(ret.size(), 2);
        assertEquals(ret.get(0).timeStamp, 2);
        assertEquals(ret.get(1).timeStamp, 4);
    }
    @Test
    public void testBadSchedule() throws IOException {
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        String filename = "/projects/assignments/smartHome/src/test/java/testData/testBadScheduleException.txt";
        try {
            ArrayList<Event> ret = EventGenerator.readData(filename, appliances);   
            assertEquals(true, false);
        }
        catch (Exception e) {
            boolean check = (e instanceof BadScheduleException);
            assertEquals(check, true);
        }
    }
    @Test
    public void testGoodSchedule() throws IOException {
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        appliances.add(new Appliance(Appliance.Type.WATERHEATER));
        appliances.add(new Appliance(Appliance.Type.COOKINGOVEN));
        appliances.add(new Appliance(Appliance.Type.AIRCONDITIONER));
        String filename = "/projects/assignments/smartHome/src/test/java/testData/testGoodScheduleException.txt";
        try {
            ArrayList<Event> ret = EventGenerator.readData(filename, appliances);   
        }
        catch (Exception e) {
            assertEquals(true, false);
        }
    }
    
}
