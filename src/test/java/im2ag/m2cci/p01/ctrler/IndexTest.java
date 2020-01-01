package im2ag.m2cci.p01.ctrler;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Collections;
import java.sql.SQLException;
import javax.sql.DataSource;
import im2ag.m2cci.p01.dao.DataSourceDeTest;
import im2ag.m2cci.p01.dao.DateString;
import im2ag.m2cci.p01.dao.ConsultationDAO;
import im2ag.m2cci.p01.ctrler.Index;
import im2ag.m2cci.p01.model.Spectacle;
import im2ag.m2cci.p01.model.Representation;

import java.io.IOException;

public class IndexTest {

  private DataSource ds;

  @Before
  public void setUp() {
    this.ds = new DataSourceDeTest();
  }

  @Test
  public void testIndex () throws SQLException, IOException {
    String date1 = "2019-02-01";
    String date2 = "2019-02-15";
    List<Spectacle> allSpectacles = ConsultationDAO.getAllRepresentationsBetweenTwoDates(ds, date1, date2, "", "","");

    List<Representation> listRep = new ArrayList<>();
    allSpectacles.forEach(x -> listRep.addAll(x.getAllRepresentations()));
    Collections.sort(listRep);

    Index index = new Index(allSpectacles);
    List<Representation> listRepIndex = new ArrayList<>();
    listRepIndex.addAll(index.getAllRepresentations());

    assertFalse("need no changes", listRepIndex.retainAll(listRep));
    assertEquals(listRepIndex.size(), index.getNbRepresentations());
    assertEquals(listRepIndex.size(), listRep.size());


    Iterator<Representation> repIndexIt = listRepIndex.iterator();
    Iterator<Representation> repIt = listRep.iterator();
    while (repIt.hasNext() && repIndexIt.hasNext()) {
      assertEquals(0, repIt.next().getDate().compareTo(repIndexIt.next().getDate()));
    }
  }
}
