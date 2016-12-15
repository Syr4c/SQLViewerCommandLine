package sqlviewer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlBackend {
    public static Connection getConnection(String loginName, String loginPass){
        Connection connection = null;
        String driverName = "oracle.jdbc.driver.OracleDriver";

        /** Try Block zum Laden des Treibers */
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {

            System.out.println("Konnte den " + driverName + " Treiber nicht laden.");
            e.printStackTrace();
        }

        /** Try Block zum herstellen der Verbindung */
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14", loginName,
                    loginPass);

        } catch (SQLException e) {
            System.out.println("Konnte die Verbindung nicht herstellen.");
            e.printStackTrace();
        }

        return connection;
    }

    public static ResultSet getUserTables(Connection connection){
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery("Select table_name FROM user_tables");
        } catch (SQLException e){
            System.out.println("Konnte die user_tables nicht laden");
            e.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getSpecificTable(Connection connection, String table){
        ResultSet rs = null;

        try {
            rs = connection.createStatement().executeQuery("Select * FROM " + table);
        } catch (SQLException e){
            System.out.println("Konnte " + table + " nicht abrufen");
            e.printStackTrace();
        }

        return rs;
    }
}
