/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.model;

import static im2ag.m2cci.p01.model.Reduction.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author htm
 */
public class TicketTest {

    public TicketTest() {
    }

    /**
     * Test of getNumero method, of class Ticket.
     */
    @Test
    public void testGetNumero() {

        Ticket tkt = new Ticket (101, null);
        int expResult = 101;
        int result = tkt.getNumero();
        assertEquals(expResult, result);

    }

    /**
     * Test of getReduction method, of class Ticket.
     */
    @Test
    public void testGetReduction() {

        Ticket instance = new Ticket (101, null);
        Reduction expResult = null;
        Reduction result = instance.getReduction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of toString method, of class Ticket.
     */
    @Test
    public void testToString() {

        Ticket tkt1 = new Ticket (100, null);
        String expResult1 = "Ticket{" + "numero=" + tkt1.getNumero() + ", reduction=" + tkt1.getReduction() + '}';
        String expResult2 = "Ticket{" + "numero=" + 100 + ", reduction=" + "null" + '}';
        String result1 = tkt1.toString();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result1);

        Reduction red = new Reduction ("etudiant", 20);
        Ticket tkt2 = new Ticket (101, red);
        String expResult3 = "Ticket{" + "numero=" + tkt2.getNumero() + ", reduction=" + tkt2.getReduction() + '}';
        String expResult4 = "Ticket{" + "numero=" + 101 + ", reduction=" + "Reduction : etudiant (20%)" + '}';
        String result2 = tkt2.toString();
        assertEquals(expResult3, result2);
        assertEquals(expResult4, result2);



    }

    /**
     * Test of getNumeroPlace method, of class Ticket.
     */
    @Test
    public void testGetNumeroPlace() {
        Ticket tkt1 = new Ticket (101, null);
        Ticket tkt2 = new Ticket (101, null, 99, 1, "");
        int expNum1 = 0;
        int resultNum1 = tkt1.getNumeroPlace();
        assertEquals(expNum1, resultNum1);

        int expNum2 = 99;
        int resultNum2 = tkt2.getNumeroPlace();
        assertEquals(expNum2, resultNum2);
    }

    /**
     * Test of setNumeroPlace method, of class Ticket.
     */
    @Test
    public void testSetNumeroPlace() {
        Reduction red = new Reduction ("etudiant", 20);
        int num = 1;
        Ticket tkt1 = new Ticket(1, red, 0, 1, "");
        Ticket tkt2 = new Ticket(1, red, 1, 1, "");
        tkt1.setNumeroPlace(num);
        assertEquals(tkt2.getNumeroPlace(), tkt1.getNumeroPlace());
        assertEquals(num, tkt1.getNumeroPlace());

    }

    /**
     * Test of getNumeroRang method, of class Ticket.
     */
    @Test
    public void testGetNumeroRang() {
        Ticket tkt1 = new Ticket (101, null);
        Ticket tkt2 = new Ticket (101, null, 99, 1, "");
        int expNum1 = 0;
        int resultNum1 = tkt1.getNumeroRang();
        assertEquals(expNum1, resultNum1);

        int expNum2 = 1;
        int resultNum2 = tkt2.getNumeroRang();
        assertEquals(expNum2, resultNum2);

    }

    /**
     * Test of setNumeroRang method, of class Ticket.
     */
    @Test
    public void testSetNumeroRang() {
        Reduction red = new Reduction ("etudiant", 20);
        int num = 1;
        Ticket tkt1 = new Ticket(1, red, 1, 0, "");
        Ticket tkt2 = new Ticket(1, red, 1, 1, "");
        tkt1.setNumeroRang(num);
        assertEquals(tkt2.getNumeroRang(), tkt1.getNumeroRang());
        assertEquals(num, tkt1.getNumeroRang());

    }

}
