package com.cnu2016.assignment02;
import java.util.*;
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
}
