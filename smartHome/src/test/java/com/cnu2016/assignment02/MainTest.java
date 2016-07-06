package com.cnu2016.assignment02;
import java.util.*;
import java.io.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class MainTest {
    @Test
    public void testPopulate() {
        ArrayList<Appliance> ret = Main.populate();
        assertEquals(ret.size(), 4);
        assertEquals(ret.get(0).getType(), Appliance.Type.WATERHEATER);
        assertEquals(ret.get(1).getType(), Appliance.Type.AIRCONDITIONER);
        assertEquals(ret.get(2).getType(), Appliance.Type.COOKINGOVEN);
        assertEquals(ret.get(3).getType(), Appliance.Type.WATERHEATER);
    }
    @Test
    public void testScheduleAndRun() throws IOException, BadScheduleException {
        ArrayList<Appliance> appliances = Main.populate();
        String filename = "/projects/assignments/smartHome/src/test/java/testData/testGoodScheduleException.txt";
        ArrayList<Event> events = EventGenerator.readData(filename, appliances);
        try {
            Main.scheduleAndRun(events, appliances);
            Thread.sleep(10 * 1000);
            assertEquals(true, true);
        }
        catch (Exception e) {
            assertEquals(true, false);
        }
    }
}
