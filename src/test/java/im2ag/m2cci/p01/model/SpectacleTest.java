/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.model;

import java.util.*;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G01
 */
public class SpectacleTest {

    public SpectacleTest() {
    }

    /**
     * Test of setImg method, of class Spectacle.
     */
    @Test
    public void testSetImg() {

        byte[] img = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes();
        Spectacle spec1 = new Spectacle(100, "LalaLand");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes());
        spec1.setImg(img);
        assertEquals(spec1, spec2);
       //img = null;
        //Spectacle instance = null;


    }

    /**
     * Test of getNom method, of class Spectacle.
     */
    @Test
    public void testGetNom() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        String expResult1 = "Hopital!";
        String expResult2 = "LalaLand";
        String result1 = spec1.getNom();
        String result2 = spec2.getNom();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getNumero method, of class Spectacle.
     */
    @Test
    public void testGetNumero() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        int expResult1 = 11;
        int expResult2 = 100;
        int result1 = spec1.getNumero();
        int result2 = spec2.getNumero();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getImg method, of class Spectacle.
     */
    @Test
    public void testGetImg() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle (100, "LalaLand",  "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        Spectacle spec3 = spec1;
        spec3.setImg("\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes());

        assertEquals(spec2.getImg(), spec3.getImg());

    }

    /**
     * Test of addRepresentation method, of class Spectacle.
     */
    @Test
    public void testAddRepresentation_4args() {
        int id = 0;
        String date = "2019-02-01";
        int heure = 0;
        int nbPlacesDispo = 0;
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(id, date, heure, nbPlacesDispo);
        int expResult1 = 1;
        int expResult2 = 0;
        int result1 = spec1.getNbRepresentation();
        int result2 = spec2.getNbRepresentation();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getGenre method, of class Spectacle.
     */
    @Test
    public void testGetGenre() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        String expResult1 = "";
        String expResult2 = "musical";
        String result1 = spec1.getGenre();
        assertEquals(expResult1, result1);
        String result2 = spec2.getGenre();
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getTrancheAge method, of class Spectacle.
     */
    @Test
    public void testGetTrancheAge() {
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);

        //Integer expResult1 = null;
        Integer expResult2 = (Integer) 18;
        //Integer result1 = spec1.getTrancheAge();
        Integer result2 = spec2.getTrancheAge();
        //assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of addRepresentation method, of class Spectacle.
     */
    @Test
    public void testAddRepresentation_5args() {
        int id = 0;
        String date = "2018-01-30";
        int heure = 0;
        int nbPlacesDispo = 0;
        int promotion = 0;
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(id, date, heure, nbPlacesDispo, promotion);
        int expResult1 = 1;
        int expResult2 = 0;
        int result1 = spec1.getNbRepresentation();
        int result2 = spec2.getNbRepresentation();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of addZoneOccupee method, of class Spectacle.
     */
    @Test
    public void testAddZoneOccupee() {
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        int numero = 1;
        spec1.addZoneOccupee(numero);
        int expResult1 = 1;
        int expResult2 = 0;
        int result1 = spec1.getNbZonesOccupees();
        int result2 = spec2.getNbRepresentation();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getZonesOccupees method, of class Spectacle.
     */
    @Test
    public void testGetZonesOccupees() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);

        spec1.addZoneOccupee(1);
        spec1.addZoneOccupee(2);

        List<Integer> expResult1 = new ArrayList<>();
        expResult1.add(0, 1);
        expResult1.add(1, 2);
        List<Integer> result1 = spec1.getZonesOccupees();
        List<Integer> expResult2 = new ArrayList<>();
        List<Integer> result2 = spec2.getZonesOccupees();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getNbZonesOccupees method, of class Spectacle.
     */
    @Test
    public void testGetNbZonesOccupees() {
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        int numero = 1;
        spec1.addZoneOccupee(numero);
        int expResult1 = 1;
        int expResult2 = 0;
        int result1 = spec1.getNbZonesOccupees();
        int result2 = spec2.getNbRepresentation();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of toString method, of class Spectacle.
     */
    @Test
    public void testToString() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        String expResult1 = "Hopital!" + " (" + 11 + ")";
        String expResult2 = spec2.getNom() + " (" + spec2.getNumero() + ")";
        String result1 = spec1.toString();
        String result2 = spec2.toString();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of iterator method, of class Spectacle.
     */
    @Test
    public void testIterator() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(10, "2019-06-06", 16, 100);
        spec1.addRepresentation(11, "2019-06-06", 18, 100);
        //Iterator<Representation> result1 = spec1.iterator();
        //Iterator<Representation> expResult1 = null;
        //Representation rep1= new Representation(10, "2019-06-06", 16, 100);
        //expResult1.add(0, rep1);
        Iterator<Representation> result2 = spec1.iterator();
        Representation rep1= result2.next();
        Representation expRep = new Representation(10, "2019-06-06", 16, 100);
        //assertEquals(expResult1, result1);
        assertTrue(result2.hasNext());
        //rep1=result2.next();
        assertEquals(expRep, rep1);
    }

    /**
     * Test of getNbRepresentation method, of class Spectacle.
     */
    @Test
    public void testGetNbRepresentation() {
        int id = 0;
        String date = "2019-02-01";
        int heure = 0;
        int nbPlacesDispo = 0;

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(id, date, heure, nbPlacesDispo);
        int expResult1 = 1;
        int expResult2 = 0;
        int result1 = spec1.getNbRepresentation();
        int result2 = spec2.getNbRepresentation();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getRepresentation method, of class Spectacle.
     */
    @Test
    public void testGetRepresentation() {
        int id = 0;
        String date = "2018-01-30";
        int heure = 0;
        int nbPlacesDispo = 0;
        int promotion = 0;

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(id, date, heure, nbPlacesDispo, promotion);
        Representation expResult1 = new Representation(0, "2018-01-30", 0, 0, 0);
        Representation expResult2 = null;
        Representation result1 = spec1.getRepresentation(id);
        assertEquals(expResult1, result1);

    }

    /**
     * Test of getAllRepresentations method, of class Spectacle.
     */
    @Test
    public void testGetAllRepresentations() {
        int id = 0;
        String date = "2018-01-30";
        int heure = 0;
        int nbPlacesDispo = 0;
        int promotion = 0;
        Representation rep1 = new Representation(id, date, heure, nbPlacesDispo, promotion);
        Representation rep2 = new Representation(1, "2018-02-30", 1, 150);
        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        spec1.addRepresentation(id, date, heure, nbPlacesDispo, promotion);
        spec1.addRepresentation(1, "2018-02-30", 1, 150);

        List<Representation> expResult1 = new ArrayList<>();
        expResult1.add(0, rep1);
        expResult1.add(1, rep2);
        List<Representation> result1 = spec1.getAllRepresentations();
        List<Representation> expResult2 = new ArrayList<>();
        List<Representation> result2 = spec2.getAllRepresentations();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of equals method, of class Spectacle.
     */
    @Test
    public void testEquals() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        Object object1 = null;
        Object object2 = spec2;
        boolean expResult1 = false;
        boolean expResult2 = true;
        boolean result1 = spec1.equals(object1);
        boolean result2 = spec2.equals(object2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of hashCode method, of class Spectacle.
     */
    @Test
    public void testHashCode() {

        Spectacle spec1 = new Spectacle(11, "Hopital!");
        Spectacle spec2 = new Spectacle(100, "LalaLand", "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes(), "musical", 18);
        int expResult1 = 68 * 3 + spec1.getNumero();
        int expResult2 = 68 * 3 + 100;
        int result1 = spec1.hashCode();
        int result2 = spec2.hashCode();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

}
