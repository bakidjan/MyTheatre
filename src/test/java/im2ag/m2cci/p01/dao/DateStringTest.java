/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.dao;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elkhatay
 */
public class DateStringTest {

    public DateStringTest() {
    }

    /**
     * Test of after method, of class DateString.
     */
    @Test
    public void testAfter() {
        String date = "2018-03-15";
        DateString instance = new DateString();
        boolean expResult = true;
        boolean result = instance.after(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class DateString.
     */
    @Test
    public void testGetDate() {
        DateString instance = new DateString();
        Date expResult = new Date();
        Date result = instance.getDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDateString method, of class DateString.
     */
    @Test
    public void testGetDateString() {
        String expResult = "2019-03-19";
        DateString instance = new DateString(expResult);
        String result = instance.getDateString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DateString.
     */
    @Test
    public void testToString() {
        String dateS = "2018-02-01";
        DateString instance = new DateString(dateS);
        String expResult = "01/02/2018";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
