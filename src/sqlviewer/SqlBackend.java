package sqlviewer;

/**
 * SQLViewerCommandLine
 * Datenbank Praktikum Termin 6
 *
 * Chris Thiele
 * chris.thiele@haw-hamburg.de
 * 21.12.2016
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SqlBackend {

    /**
     * method returns an connection object with the given url, login name and passphrase
     * */
    public static Connection getConnection(String loginName, String loginPass, String connectionUrl) throws ClassNotFoundException, SQLException {
        Connection connection;
        String driverName = "oracle.jdbc.driver.OracleDriver";

        Class.forName(driverName);

        connection = DriverManager.getConnection(connectionUrl, loginName, loginPass);

        return connection;
    }

    /**
     * method returns an ResultSet with the default user tables on the given connection object
     * */
    public static ResultSet getUserTables(Connection connection) throws SQLException {
        ResultSet rs;

        rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("Select table_name FROM user_tables");

        return rs;
    }

    /**
     * method returns an ResultSet with the specific sql table on the given connection object and given table name
     * */
    public static ResultSet getSpecificTable(Connection connection, String table) throws SQLException {
        ResultSet rs;


        rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("Select * FROM " + table);

        return rs;
    }
}
