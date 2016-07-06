package com.cnu2016.assignment02;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class EventTest {
    @Test
    public void testBasics() {
        Appliance a = new Appliance(Appliance.Type.WATERHEATER);
        Event e = new Event(23, a);
        assertEquals(e.timeStamp, 23);
        assertEquals(e.appliance.getID(), a.getID());
    }
}
