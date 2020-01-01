package im2ag.m2cci.p01.dao;

import im2ag.m2cci.p01.model.Reduction.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.Connection;

import im2ag.m2cci.p01.dao.DataSourceDeTest;
import im2ag.m2cci.p01.dao.InsertionDAO;
import im2ag.m2cci.p01.model.Reduction;

public class InsertionDAOTest {

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
    public void testInsertUtilisateur() throws SQLException {
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "frusciante@gmail.com", "redhotsucks", "Frusciante", "John");
        // test getUtilisateur
        // TODO classe Utilisateur
    }

    @Test(expected = SQLException.class)
    public void testFailInsertUtilisateur1() throws SQLException {
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "frusciante@gmail.com", "redhotsucks", "Frusciante", "John");
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "john.frusc@gmail.com", "ceozkaid", "Frusciante", "John");
    }

    @Test(expected = SQLException.class)
    public void testFailInsertUtilisateur2() throws SQLException {
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "frusciante@gmail.com", "redhotsucks", "Frusciante", "John");
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc38", "frusciante@gmail.com", "ceozkaid", "Frusciante", "John");
    }

    @Test
    public void testInsertDossierDAchat() throws SQLException {
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "frusciante@gmail.com", "redhotsucks", "Frusciante", "John");
        InsertionDAO.insertDossierDAchat(this.ds, "johnFrusc", true);
        InsertionDAO.insertDossierDAchat(this.ds, "johnFrusc", false);
    }

   /* @Test(expected = SQLException.class)
    * public void testFailInsertDossierDAchat() throws SQLException {
    *    //InsertionDAO.insertUtilisateur (this.ds, "johnFrusc","frusciante@gmail.com","redhotsucks","Frusciante","John");
    *    //InsertionDAO.insertDossierDAchat(this.ds, "johnFrusc", true);
    *    InsertionDAO.insertDossierDAchat(this.ds, "johnFruscii", true);
    }*/

    @Test
    public void testInsertTicket() throws SQLException, AchatConcurrentException {
        Reduction red = new Reduction("etudiant", 20);
        InsertionDAO.insertUtilisateur(this.ds, "johnFrusc", "frusciante@gmail.com", "redhotsucks", "Frusciante", "John");
        InsertionDAO.insertDossierDAchat(this.ds, "johnFrusc", true);
        InsertionDAO.insertTicket(this.ds, 1, 1, 1, red);
        InsertionDAO.insertTicket(this.ds, 2, 2, 1, null);
    }

    /* @Test(expected = SQLException.class)
  *public void testFailInsertTicket () throws SQLException, AchatConcurrentException {
   * Reduction red = new Reduction("etudiant", 20); 
   * InsertionDAO.insertUtilisateur (this.ds, "johnFrusc","frusciante@gmail.com","redhotsucks","Frusciante","John");
    *InsertionDAO.insertDossierDAchat(this.ds, "johnFrusc", true);
    *InsertionDAO.insertTicket (this.ds, 1, 1, 1, red);
    *InsertionDAO.insertTicket (this.ds, 159, 550, 1, null);
  }*/
}
