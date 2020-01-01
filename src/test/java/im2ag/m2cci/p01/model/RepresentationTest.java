/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.model;

import im2ag.m2cci.p01.dao.DateString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G01
 */
public class RepresentationTest {

    public RepresentationTest() {
    }

    /**
     * Test of getId method, of class Representation.
     */
    @Test
    public void testGetId() {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        int result = instance.getId();
        int expResult = 111;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDateString method, of class Representation.
     */
    @Test
    public void testGetDateString() {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        String result = instance.getDateString();
        String s = "18/03/2019";
        assertEquals(s, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDate method, of class Representation.
     */
    @Test
    public void testGetDate() throws ParseException {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        Date result = instance.getDate();
        String s = "2019-03-18";
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        Date expResult = sdf.parse(s);

        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHeure method, of class Representation.
     */
    @Test
    public void testGetHeure() {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        int result = instance.getHeure();
        int expResult = 18;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNbPlacesDispo method, of class Representation.
     */
    @Test
    public void testGetNbPlacesDispo() {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        int result = instance.getNbPlacesDispo();
        int expResult = 150;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPromotion method, of class Representation.
     */
    @Test
    public void testGetPromotion() {
        Representation instance1 = new Representation(111, "2019-03-18", 18, 150, 20);
        int result1 = instance1.getPromotion();
        int expResult1 = 20;
        assertEquals(expResult1, result1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Representation.
     */
    @Test
    public void testToString() {
        Representation instance = new Representation(111, "2019-03-18", 18, 150, 20);
        String expResult1 = 111 + " à " + "2019-03-18" + " " + 18 + "h\n\tIl reste " + 150 + " places.";
        //String expResult2 = instance.getId() + " à " + instance.getDate().toString() + " " + instance.getHeure() + "h\n\tIl reste " + instance.getNbPlacesDispo() + " places.";
        String result = instance.toString();
        assertEquals(expResult1, result);
        //assertEquals(expResult2, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Representation.
     */
    @Test
    public void testCompareTo() {
        Representation rep = new Representation(111, "2019-03-18", 18, 150, 20);
        Representation anotherRep1 = new Representation(111, "2019-03-18", 18, 150, 20);
        Representation anotherRep2 = new Representation(110, "2019-03-18", 18, 150, 20);
        Representation anotherRep3 = new Representation(111, "2019-03-18", 17, 150, 20);
        Representation anotherRep4 = new Representation(111, "2019-03-18", 19, 150, 20);
        int expResult1 = 0;
        int expResult2 = 1;
        int expResult3 = 1;
        int expResult4 = -1;
        int result1 = rep.compareTo(anotherRep1);
        int result2 = rep.compareTo(anotherRep2);
        int result3 = rep.compareTo(anotherRep3);
        int result4 = rep.compareTo(anotherRep4);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Representation.
     */
    @Test
    public void testEquals() {
        Representation rep = new Representation(111, "2019-03-18", 18, 150, 20);
        Object object1 = null;
        Object object2 = rep;
        boolean expResult1 = false;
        boolean expResult2 = true;
        boolean result1 = rep.equals(object1);
        boolean result2 = rep.equals(object2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Representation.
     */
    @Test
    public void testHashCode() {
        Representation rep = new Representation(111, "2019-03-18", 18, 150, 20);
        int expResult1 = 41 * 3 + 111 *18 *150;
        int expResult2 = 41 * 3 + rep.getId()*rep.getHeure()*rep.getNbPlacesDispo();
        int result = rep.hashCode();
        assertEquals(expResult1, result);
        assertEquals(expResult2, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
