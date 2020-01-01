package im2ag.m2cci.p01.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
* Emule un objet DataSource, utilisé pour les tests unitaires.
*
* @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
*/
public class DataSourceDeTest implements DataSource {
  private static final String DRIVER_CLASS = "org.sqlite.JDBC";
  private static final String URL = "jdbc:sqlite:../../sql/database_test.sqlite3";
  private static final String USER = null;
  private static final String PASSWD = null;

  @Override
  public Connection getConnection() throws SQLException {
    try {
      Class.forName(DRIVER_CLASS);
    } catch (ClassNotFoundException ex) {
      throw new SQLException(ex.getMessage(), ex);
    }

    return DriverManager.getConnection(URL, USER, PASSWD); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public static void main(String[] args) throws SQLException {
    DataSource ds = new DataSourceDeTest();
    Connection conn = ds.getConnection();
    conn.close();
    System.out.println("DataSourceDeTest OK");
  }
}
