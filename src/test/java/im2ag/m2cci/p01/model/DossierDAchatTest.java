/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im2ag.m2cci.p01.model;

import static im2ag.m2cci.p01.model.Reduction.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G01
 */
public class DossierDAchatTest {

    public DossierDAchatTest() {
    }

    /**
     * Test of addTicket method, of class DossierDAchat.
     */
    @Test
    public void testAddTicket() {
        int numeroTicket = 0;
        Reduction reduction = null;
        DossierDAchat doss1 = new DossierDAchat(1);
        doss1.addTicket(numeroTicket, reduction);
        List<Ticket> tkts = doss1.getAllTickets();
        int result = tkts.size();
        assertEquals(1, result);

    }

    /**
     * Test of payerDossier method, of class DossierDAchat.
     */
    @Test
    public void testPayerDossier() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        dossier1.payerDossier("");
        assertTrue(dossier1.getEstPaye());

    }

    /**
     * Test of getNbTicket method, of class DossierDAchat.
     */
    @Test
    public void testGetNbTicket() {
        DossierDAchat doss1 = new DossierDAchat(1);
        DossierDAchat doss2 = new DossierDAchat(2);
        Reduction red = new Reduction("etudiant", 20);
        doss1.addTicket(1, null);
        doss1.addTicket(2, red);

        int expResult1 = 2;
        int expResult2 = 0;
        int result1 = doss1.getNbTicket();
        int result2 = doss2.getNbTicket();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getDateDAchat method, of class DossierDAchat.
     */
    @Test
    public void testGetDateDAchat() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        //String expResult = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String expResult = "";
        String result = dossier1.getDateDAchat();
        assertEquals(expResult, result);

    }

    /**
     * Test of iterator method, of class DossierDAchat.
     */
    @Test
    public void testIterator() {
        DossierDAchat doss1 = new DossierDAchat(1);
        Reduction red = new Reduction("etudiant", 20);
        doss1.addTicket(1, null);
        doss1.addTicket(2, red);

        Iterator<Ticket> result1 = doss1.iterator();
        assertTrue(result1.hasNext());

    }

    /**
     * Test of getNumero method, of class DossierDAchat.
     */
    @Test
    public void testGetNumero() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        int expResult = 1;
        int result = dossier1.getNumero();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class DossierDAchat.
     */
    @Test
    public void testToString() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        //String expResult1 = "DossierDAchat{ " + "numero=" + 1 + ", dateDAchat=" + (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " }\n";
        String expResult1 = "DossierDAchat{ " + "numero=" + 1 + ", dateDAchat= }\n";
        expResult1 += "Le dossier est " + (dossier1.getEstPaye() ? "" : "non ") + "payé.";
        String result1 = dossier1.toString();
        assertEquals(expResult1, result1);

        dossier1.payerDossier("");
        //String expResult2 = "DossierDAchat{ " + "numero=" + 1 + ", dateDAchat=" + (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + " }\n";
        String expResult2 = "DossierDAchat{ " + "numero=" + 1 + ", dateDAchat= }\n";
        expResult2 += "Le dossier est " + (true ? "" : "non ") + "payé.";
        String result2 = dossier1.toString();
        assertEquals(expResult2, result2);

    }

    /**
     * Test of equals method, of class DossierDAchat.
     */
    @Test
    public void testEquals() {
        Object obj = null;
        DossierDAchat dossier1 = new DossierDAchat(1);
        boolean result1 = dossier1.equals(obj);
        assertFalse(result1);

        Object obj2 = dossier1;
        boolean result2 = dossier1.equals(obj2);
        assertTrue(result2);

        DossierDAchat dossier2 = new DossierDAchat(2);
        Object obj3 = dossier2;
        boolean result3 = dossier2.equals(obj3);
        assertTrue(result3);

    }

    /**
     * Test of hashCode method, of class DossierDAchat.
     */
    @Test
    public void testHashCode() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        int expResult = 29 * 7 + dossier1.getNumero();
        int result = dossier1.hashCode();
        assertEquals(expResult, result);

    }

    /**
     * Test of getstPaye method, of class DossierDAchat.
     */
    @Test
    public void testgetEstPaye() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        DossierDAchat dossier2 = new DossierDAchat(2);
        dossier2.payerDossier("");
        boolean expResult1 = false;
        boolean expResult2 = true;
        boolean result1 = dossier1.getEstPaye();
        boolean result2 = dossier2.getEstPaye();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getAllTickets method, of class DossierDAchat.
     */
    @Test
    public void testGetAllTickets() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        Reduction red = new Reduction("etudiant", 20);
        Ticket tkt1 = new Ticket(1, null);
        Ticket tkt2 = new Ticket(2, red);
        dossier1.addTicket(1, null);
        dossier1.addTicket(2, red);
        List<Ticket> expResult = new ArrayList<Ticket>();
        expResult.add(tkt1);
        expResult.add(tkt2);
        List<Ticket> result = dossier1.getAllTickets();
        assertEquals(expResult.size(), dossier1.getNbTicket());
        assertEquals(expResult.get(0).getNumero(), result.get(0).getNumero());
        assertEquals(expResult.get(0).getReduction(), result.get(0).getReduction());
        assertEquals(expResult.get(1).getNumero(), result.get(1).getNumero());
        assertEquals(expResult.get(1).getReduction(), result.get(1).getReduction());
    }

    /**
     * Test of getPrixTotal method, of class DossierDAchat.
     */
    @Test
    public void testGetPrixTotal() {
        DossierDAchat dossier1 = new DossierDAchat(1);
        int expResult = 0;
        int result = dossier1.getPrixTotal();
        assertEquals(expResult, result);

    }

}
