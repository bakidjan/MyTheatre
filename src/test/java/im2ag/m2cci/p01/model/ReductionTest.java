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
 * @author htm
 */
public class ReductionTest {
    
    public ReductionTest() {
    }

    /**
     * Test of getValeurReduction method, of class Reduction.
     */
    @Test
    public void testGetValeurReduction() {
        Reduction red = new Reduction("etudiant", 20);
        int expResult = 20;
        int result = red.getValeurReduction();
        assertEquals(expResult, result);

    }

    /**
     * Test of getNomReduction method, of class Reduction.
     */
    @Test
    public void testGetNomReduction() {
        Reduction red = new Reduction("etudiant", 20);
        String expResult = "etudiant";
        String result = red.getNomReduction();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Reduction.
     */
    @Test
    public void testToString() {

        Reduction red = new Reduction("etudiant", 20);
        
        String expResult = "Reduction : " + "etudiant" + " (" + 20 + "%)";
        String result = red.toString();
        assertEquals(expResult, result);

    }
    
}
