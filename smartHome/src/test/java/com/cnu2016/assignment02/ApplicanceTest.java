package com.cnu2016.assignment02;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class ApplicanceTest {
    @Test
    public void testBasics() {
        Appliance a = new Appliance(Appliance.Type.WATERHEATER);
        Appliance b = new Appliance(Appliance.Type.COOKINGOVEN);
        assertEquals(a.getID() + 1,b.getID());
        assertEquals(a.getType(), Appliance.Type.WATERHEATER);
        assertEquals(b.getType(), Appliance.Type.COOKINGOVEN);
        assertEquals(a.getState(), false);
        a.toggle();
        assertEquals(a.getState(), true);
        a.toggle();
        assertEquals(a.getState(), false);
        boolean check = a.toString().contains("false");
        assertEquals(check, true);
    }
    
}
