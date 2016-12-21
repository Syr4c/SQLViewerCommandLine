package sqlviewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class SqlConsole {
    /**
     * final connectionUrl variable to determine the targeted SQL-Server.
     * */
    private final String connectionUrl = "jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14";

    /**
     * showMenu() method to loop the input field and the menu.
     * */
    public void showMenu() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = getLogin();

        while(true){
            MenuLogic(connection);
        }
    }

    /**
     * MenuLogic() represents the given information and the interaction with the user.
     */
    public void MenuLogic(Connection connection) throws SQLException, IOException {
        Scanner userInput = new Scanner(System.in);

        Integer tableNumberCounter = 1;
        Integer userTableDecision;
        Integer tableColumnCount;

        String specificTableName;

        ResultSet userTables = SqlBackend.getUserTables(connection);
        ResultSet specificTable;
        ResultSetMetaData specificTableMetaData;

        System.out.println("\nUser Tables:\n ");

        while (userTables.next()) {
            System.out.println("(" + tableNumberCounter.toString() + ")" + userTables.getString(1));
            tableNumberCounter++;
        }

        System.out.println("\nWählen sie eine Tabelle: ");
        userTableDecision = userInput.nextInt();

        while(userTableDecision > tableNumberCounter || userTableDecision < 1) {
            System.out.println("Eingabe fehlerhaft, bitte wiederholen: ");
            userTableDecision = userInput.nextInt();
        }

        userTables.absolute(userTableDecision);
        specificTableName = userTables.getString(1);

        specificTable = SqlBackend.getSpecificTable(connection, specificTableName);

        specificTableMetaData = specificTable.getMetaData();
        tableColumnCount = specificTableMetaData.getColumnCount();

        System.out.println("Inhalt der Tabelle " + userTables.getString(1));

        for (int i = 1; i <= tableColumnCount; i++) {
            System.out.printf("%20s |", specificTableMetaData.getColumnLabel(i));
        }

        System.out.print("\n");
        System.out.print(new String(new char[tableColumnCount]).replace("\0", "----------------------"));
        System.out.print("\n");

        while (specificTable.next()) {
            for (int i = 1; i <= tableColumnCount; i++) {
                System.out.printf("%20s |", specificTable.getString(i));
            }
            System.out.print("\n");
        }

        /* pause a program till next keystroke */
        System.out.println("\nEnter drücken um eine weitere Tabelle auszugeben");
        System.in.read();
    }

    /**
     * getLogin() reads the login informations like name and passphrase, also establish the sql connection.
     * */
    public Connection getLogin() throws SQLException, ClassNotFoundException {
        Scanner scanner;
        String loginName;
        String loginPass;

        scanner = new Scanner(System.in);

        System.out.println("SQLViewer | DBP 6 | Chris Thiele");
        System.out.println("--------------------------------\n");
        System.out.print("Login Name: ");
        loginName = scanner.nextLine();
        System.out.print("Login Pass: ");
        loginPass = scanner.nextLine();

        return SqlBackend.getConnection(loginName, loginPass, connectionUrl);
    }
}
