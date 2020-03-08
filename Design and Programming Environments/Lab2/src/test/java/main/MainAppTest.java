package main;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainAppTest {

    @Test
    public void suma() {
        assertEquals(MainApp.suma(1,1),2);
        assertEquals(MainApp.suma(2,3),5);
        assertEquals(MainApp.suma(3,9),12);
        assertEquals(MainApp.suma(-1,1),0);
        assertEquals(MainApp.suma(-1,-2),-3);
    }
}