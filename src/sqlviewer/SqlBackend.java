package sqlviewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlBackend {


    public static Connection getConnection(String loginName, String loginPass, String connectionUrl) throws ClassNotFoundException, SQLException {
        Connection connection;
        String driverName = "oracle.jdbc.driver.OracleDriver";

        Class.forName(driverName);

        connection = DriverManager.getConnection(connectionUrl, loginName, loginPass);

        return connection;
    }

    public static ResultSet getUserTables(Connection connection) throws SQLException {
        ResultSet rs;

        rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("Select table_name FROM user_tables");

        return rs;
    }

    public static ResultSet getSpecificTable(Connection connection, String table) throws SQLException {
        ResultSet rs;


        rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("Select * FROM " + table);

        return rs;
    }
}
