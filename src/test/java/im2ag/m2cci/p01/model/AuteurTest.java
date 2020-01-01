/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G01
 */
public class AuteurTest {

    public AuteurTest() {
    }

    /**
     * Test of getNomAut method, of class Auteur.
     */
    @Test
    public void testGetNomAut() {
        Auteur aut1 = new Auteur("Shakspere", "William");
        Auteur aut2 = new Auteur("Hugo", "Victor");
        String expResult1 = "Shakspere";
        String expResult2 = "Hugo";
        String result1 = aut1.getNomAut();
        String result2 = aut2.getNomAut();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getPrenomAut method, of class Auteur.
     */
    @Test
    public void testGetPrenomAut() {
        Auteur aut1 = new Auteur("Shakspere", "William");
        Auteur aut2 = new Auteur("Hugo", "Victor");
        String expResult1 = "William";
        String expResult2 = "Victor";
        String result1 = aut1.getPrenomAut();
        String result2 = aut2.getPrenomAut();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getAuteur method, of class Auteur.
     */
    @Test
    public void testGetAuteur() {
        Auteur aut1 = new Auteur("Shakspere", "William");
        Auteur aut2 = new Auteur("Hugo", "Victor");
        String expResult1 = "William" + " " + "Shakspere";
        String result1 = aut1.getAuteur();
        String result2 = aut2.getAuteur();
        String expResult2 = aut2.getPrenomAut() + " " + aut2.getNomAut();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of equals method, of class Auteur.
     */
    @Test
    public void testEquals() {
        Auteur aut1 = new Auteur("", "");
        aut1.setNom("Hugo");
        aut1.setPrenom("Victor");
        Object object1 = null;
        Object object2 = aut1;

        Auteur aut2 = new Auteur("Hugo", "Victor");

        boolean expResult1 = false;
        boolean expResult2 = true;

        boolean result1 = aut1.equals(object1);
        boolean result2 = aut2.equals(object2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of setNom method, of class Auteur.
     */
    @Test
    public void testSetNom() {
        Auteur aut = new Auteur("", "Victor");
        aut.setNom("Hugo");
        Auteur expAut = new Auteur("Hugo", "Victor");
        assertEquals(expAut, aut);

    }

    /**
     * Test of setPrenom method, of class Auteur.
     */
    @Test
    public void testSetPrenom() {
        Auteur aut = new Auteur("Hugo", "");
        aut.setPrenom("Victor");
        Auteur expAut = new Auteur("Hugo", "Victor");
        assertEquals(expAut, aut);
    }

    /**
     * Test of toString method, of class Auteur.
     */
    @Test
    public void testToString() {
        Auteur aut1 = new Auteur("Shakspere", "William");
        String expResult1 = aut1.getPrenomAut() + " " + aut1.getNomAut();
        String result1 = aut1.toString();
        assertEquals(expResult1, result1);

        Auteur aut2 = new Auteur("Hugo", "Victor");
        String expResult2 = "Victor Hugo";
        String result2 = aut2.toString();
        assertEquals(expResult2, result2);

    }

}
