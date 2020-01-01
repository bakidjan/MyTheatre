package im2ag.m2cci.p01.dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.sql.SQLException;
import javax.sql.DataSource;
import im2ag.m2cci.p01.dao.DataSourceDeTest;
import im2ag.m2cci.p01.dao.ConsultationDAO;
import static im2ag.m2cci.p01.dao.InsertionDAO.insertUtilisateur;
import im2ag.m2cci.p01.model.Spectacle;
import im2ag.m2cci.p01.model.Representation;
import im2ag.m2cci.p01.model.Reduction;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.After;

public class ConsultationDAOTest {

    private DataSource ds;

    @Before
    public void setUp() {
        this.ds = new DataSourceDeTest();
    }

    @After
    public void setDown() throws SQLException {
        try (Connection conn = this.ds.getConnection();) {
            Statement stmt = conn.createStatement();
            stmt.addBatch("DELETE FROM LesUtilisateurs");
            stmt.addBatch("DELETE FROM LesDossiersDAchat_base");
            stmt.addBatch("DELETE FROM LesTickets");
            stmt.executeBatch();
        }
    }

    @Test
    public void testGetAllSpectacles() throws SQLException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllSpectacles(this.ds);
        List<Integer> numRef = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> numEch = new ArrayList<>();
        theatre.forEach(x -> numEch.add(x.getNumero()));

        assertEquals(numRef.size(), theatre.size());
        assertEquals(numRef.size(), numEch.size());
        assertFalse("need no changes", numRef.retainAll(numEch));

        /* print */
        //theatre.forEach(x -> System.out.println(x));
    }

    @Test
    public void testGetAllSpectaclesWithGenreAndAge() throws SQLException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllSpectacles(this.ds, "tragédie", 16);
        List<Integer> numRef = Arrays.asList(1, 7);
        List<Integer> numEch = new ArrayList<>();
        theatre.forEach(x -> numEch.add(x.getNumero()));

        assertEquals(numRef.size(), theatre.size());
        assertEquals(numRef.size(), numEch.size());
        assertFalse("need no changes", numRef.retainAll(numEch));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailGetAllSpectaclesWithGenre() throws SQLException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllSpectacles(this.ds, "truc", 16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailGetAllSpectaclesWithAge() throws SQLException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllSpectacles(this.ds, "tragedie", 17);
    }

    @Test
    public void testGetAllRepresentations() throws SQLException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllRepresentations(this.ds);

        assertEquals(10, theatre.size());
        assertEquals(32, this.nbAllRepresentations(theatre.iterator()));
    }

    /**
     * Test of getAllRepresentationsBetweenTwoDates method, of class
     * ConsultationDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    @SuppressWarnings("empty-statement")
    public void testGetAllRepresentationsBetweenTwoDates() throws Exception, IllegalArgumentException, IOException {
        String date1 = "2019-02-04";
        String date2 = "2019-02-08";
        String genre = "";
        String trancheAge = "";
        List<Spectacle> result = ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, date1, date2, genre, trancheAge, "");
        assertEquals(10, result.size());

        for (Spectacle s : result) {
            int no = 0;
            if (no > 10) {
                no = 1;
            } else {
                no = s.getNumero();
            }

            assertEquals(no, s.getNumero());

            no++;

        }
        assertEquals("04/02/2019", result.get(0).getRepresentation(14).getDateString());

        /* date1 = date2 */
        result = ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, date1, date1, genre, trancheAge, "");
        List<Integer> numRef = Arrays.asList(7, 8);

        assertEquals(numRef.size(), this.nbAllRepresentations(result.iterator()));

        List<Integer> numEch = new ArrayList<>();
        result.forEach(x -> numEch.add(x.getNumero()));

        assertEquals(numRef.size(), result.size());
        assertEquals(numRef.size(), numEch.size());
        assertFalse("need no changes", numRef.retainAll(numEch));

        /* date doesn't exist */
        result = ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, "2019-03-04", "2019-03-06", genre, trancheAge, "");
        assertEquals(0, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailGetAllRepresentationsBetweenTwoDates1() throws SQLException, IllegalArgumentException, IOException {
        String date1 = "2019-02-04";
        String date2 = "2019-02-08";
        List<Spectacle> result = ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, date2, date1, "", "","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailGetAllRepresentationsBetweenTwoDates2() throws SQLException, IllegalArgumentException, IOException {
        List<Spectacle> result = ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, "01/02/2018", "03/02/2018", "", "","");
    }

    private int nbAllRepresentations(Iterator<Spectacle> it) throws SQLException {
        int sumRepresentation;
        if (it.hasNext()) {
            Spectacle spectacle = it.next();
            sumRepresentation = nbAllRepresentations(it);
            sumRepresentation += spectacle.getNbRepresentation();
        } else {
            sumRepresentation = 0;
        }
        return sumRepresentation;
    }

    @Test
    public void testGetSpectacle() throws SQLException, IOException {
        Spectacle spectacle = ConsultationDAO.getSpectacle(this.ds, 1);
        assertEquals(3, spectacle.getNbRepresentation());

        Iterator<Representation> representationIt = spectacle.iterator();
        while (representationIt.hasNext()) {
            Representation rep = representationIt.next();
            assertEquals(18, rep.getHeure());
            assertEquals(50, rep.getNbPlacesDispo());
        }

        spectacle = ConsultationDAO.getSpectacle(this.ds, 10);
        assertEquals("05/02/2019", spectacle.getRepresentation(17).getDateString());
        assertEquals("10/02/2019", spectacle.getRepresentation(3).getDateString());
        assertEquals("15/02/2019", spectacle.getRepresentation(30).getDateString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFailGetRepresentation() throws SQLException {
        Spectacle spectacle = ConsultationDAO.getSpectacle(this.ds, 2);
        spectacle.getRepresentation(11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailGetAllRepresentationWithDate() throws SQLException, IllegalArgumentException, IOException {
        List<Spectacle> theatre = ConsultationDAO.getAllRepresentationsWithDate(this.ds, "01/01/2019", "", "","");
    }

    //  @Test
    //  public void testGetAllRepresentationsWithDate() throws SQLException, IllegalArgumentException, IOException {
    //    List<Spectacle> theatre = ConsultationDAO.getAllRepresentationsWithDate(this.ds, "2019-02-01", "humouristique");
    //    assertEquals(2, this.nbAllRepresentations(theatre.iterator()));
    //    theatre = ConsultationDAO.getAllRepresentationsWithDate(this.ds, "2019-03-01", "humouristique");
    //    assertEquals(0, theatre.size());
    //  }
    @Test
    public void testSetZonesOccupees() throws SQLException {
        Spectacle spectacle = ConsultationDAO.getSpectacle(this.ds, 4);
        ConsultationDAO.setZonesOccupees(this.ds, spectacle);
        assertEquals(2, spectacle.getNbZonesOccupees());

        spectacle = ConsultationDAO.getSpectacle(this.ds, 1);
        ConsultationDAO.setZonesOccupees(this.ds, spectacle);
        List<Integer> zonesOccupees = spectacle.getZonesOccupees();
        List<Integer> zonesRef = Arrays.asList(1, 3);
        assertFalse("need no changes", zonesRef.retainAll(zonesOccupees));

        spectacle = ConsultationDAO.getSpectacle(this.ds, 8);
        ConsultationDAO.setZonesOccupees(this.ds, spectacle);
        assertEquals(0, spectacle.getNbZonesOccupees());
    }

    /**
     * Test of getAllSpectacles method, of class ConsultationDAO.
     */
    @Test
    public void testGetAllSpectacles_DataSource() throws Exception {
        List<String> expResult = Arrays.asList("Les Justes", "Fin de Partie", "Montserrat", "La Cantatrice chauve", "La leçon", "Le Silence", "Faust", "Phèdre", "L`Avare", "Horace");
        List<Spectacle> result = ConsultationDAO.getAllSpectacles(this.ds);
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getAllSpectacles method, of class ConsultationDAO.
     */
    @Test
    public void testGetAllSpectacles_3args() throws Exception {
        Spectacle spec1 = new Spectacle (2, "Fin de Partie", "humoristique" , 12);
        Spectacle spec2 = new Spectacle (9, "L`Avare", "humoristique" , 12);
        List<Spectacle> expResult = Arrays.asList(spec1, spec2);

        String genre = "humoristique";
        int trancheAge = 12;

        List<Spectacle> result = ConsultationDAO.getAllSpectacles (this.ds, genre, trancheAge);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAllGenres method, of class ConsultationDAO.
     */
    @Test
    public void testGetAllGenres() throws Exception {
        List<String> expResult = Arrays.asList("tragédie", "humoristique", "comédie", "improvisation", "musical", "intrigue");
        List<String> result = ConsultationDAO.getAllGenres(this.ds);
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getAllAgeClassifications method, of class ConsultationDAO.
     */
    @Test
    public void testGetAllAgeClassifications() throws Exception {
        List<Integer> expResult = Arrays.asList(0, 12, 16, 18);
        List<Integer> result = ConsultationDAO.getAllAgeClassifications(this.ds);
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getAllRepresentationsWithDate method, of class ConsultationDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllRepresentationsWithDate() throws Exception {
        String date = "2019-04-02";
        String genre = "";
        String trancheAge = "";
        List<Spectacle> result = ConsultationDAO.getAllRepresentationsWithDate(this.ds, date, genre, trancheAge, "");
        assertEquals(0, result.size());
    }

    /**
     * Test of getSpectacleWithDate method, of class ConsultationDAO.
     */
    @Test
    public void testGetSpectacleWithDate() throws Exception {
        DataSource dataSource = null;
        int numero = 0;
        String date = "2019-04-08";
        Spectacle expResult = null;
        Spectacle result = ConsultationDAO.getSpectacleWithDate(this.ds, numero, date);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasLogin method, of class ConsultationDAO.
     */
    @Test
    public void testHasLogin() throws Exception {
        insertUtilisateur(this.ds, "alainR", "alain.rock@gmail.com", "0000", "Rock", "Alain");

        String login1 = "alainR";
        String login2 = "alainS";

        boolean expResult1 = true;

        boolean result1 = ConsultationDAO.hasLogin(this.ds, login1);
        boolean result2 = ConsultationDAO.hasLogin(this.ds, login2);

        assertEquals(expResult1, result1);
        assertFalse(result2);

    }

    /**
     * Test of hasEmail method, of class ConsultationDAO.
     */
    @Test
    public void testHasEmail() throws Exception {
        insertUtilisateur(this.ds, "alainR", "alain.rock@gmail.com", "0000", "Rock", "Alain");
        String email1 = "alain.rock@gmail.com";
        boolean expResult1 = true;
        boolean result1 = ConsultationDAO.hasEmail(this.ds, email1);
        assertEquals(expResult1, result1);
    }

    /**
     * Test of isValidatePassword method, of class ConsultationDAO.
     */
    @Test
    public void testIsValidatePassword() throws Exception {
        insertUtilisateur(this.ds, "alainR", "alain.rock@gmail.com", "0000", "Rock", "Alain");

        String login = "alainR";
        String password = "0000";
        String password1 = "0001";
        boolean expResult1 = true;
        boolean result1 = ConsultationDAO.isValidatePassword(ds, login, password);
        boolean result2 = ConsultationDAO.isValidatePassword(ds, login, password1);
        assertEquals(expResult1, result1);
        assertFalse(result2);

    }

    /**
     * Test of getAllReductions method, of class ConsultationDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllReductions() throws Exception {
        List<String> expResult = Arrays.asList("adherent", "etudiant", "familleNombreuse", "scolaire", "senior");
        List<Reduction> result = ConsultationDAO.getAllReductions(this.ds);
        assertEquals(expResult.size(), result.size());
    }
}
