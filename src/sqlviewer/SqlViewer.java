package sqlviewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SqlViewer {
    final String connectionUrl = "jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14";
    private String loginName;
    private String loginPass;
    private Scanner scanner;
    private Connection connection;

    public void showMenu() throws SQLException {
        Integer counter = 1;
        Integer input = null;
        Integer tableColumCount = null;
        String table = null;
        ResultSet specificTable = null;
        ResultSet userTables = SqlBackend.getUserTables(connection);
        ResultSetMetaData rsmd = null;
        ArrayList<String> columNames = new ArrayList<>();


        System.out.println("\nUser Tables:\n ");

        while (userTables.next()) {
            System.out.println("(" + counter.toString() + ")" + userTables.getString(1));
            counter++;
        }

        System.out.println("\nWählen sie eine Tabelle: ");
        input = scanner.nextInt();

        if (input > counter || input < 1) {
            System.out.println("Eingabe fehlerhaft");
            showMenu();
        }

        userTables.absolute(input);
        table = userTables.getString(1);

        specificTable = SqlBackend.getSpecificTable(connection, table);

        rsmd = specificTable.getMetaData();
        tableColumCount = rsmd.getColumnCount();

        for (int i = 1; i < tableColumCount; i++) {
            columNames.add(rsmd.getCatalogName(i));
        }


        for (int i = 0; i < (columNames.size() - 1); i++) {
            System.out.print(columNames.get(i) + " ");
        }


        while (specificTable.next()) {
            for (int i = 1; i < tableColumCount; i++) {
                System.out.print(specificTable.getString(i) + " ");
            }
            System.out.print("\n");
        }

        System.out.println("Möchten Sie eine weitere Tabelle anzeigen?");

    }


    public void getLogin() throws SQLException, ClassNotFoundException {
        scanner = new Scanner(System.in);

        System.out.println("SQLViewer | DBP 6 | Chris Thiele");
        System.out.println("--------------------------------\n");
        System.out.print("Login Name: ");
        loginName = scanner.nextLine();
        System.out.print("Login Pass: ");
        loginPass = scanner.nextLine();

        connection = SqlBackend.getConnection(loginName, loginPass, connectionUrl);

        showMenu();
    }
}
