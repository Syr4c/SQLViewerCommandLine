package sqlviewer;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SqlViewer {
    String loginName;
    String loginPass;
    Scanner scanner;
    Connection connection;

    public void showMenu(){
        Integer counter = 1;
        Integer input = null;
        Integer tableColumCount = null;
        String table = null;
        ResultSet specificTable = null;
        ResultSet userTables = SqlBackend.getUserTables(connection);
        ResultSetMetaData rsmd = null;
        ArrayList<String> columNames = new ArrayList<>();


        System.out.println("\nUser Tables:\n ");
        try {
            while (userTables.next()) {
                System.out.println("(" + counter.toString() + ")" + userTables.getString(1));
                counter++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Wählen sie eine Tabelle: ");
        input = scanner.nextInt();

        if (input > counter || input < 1){
            System.out.println("Eingabe fehlerhaft");
            showMenu();
        }

        try {
            userTables.absolute(input);
            table = userTables.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        specificTable = SqlBackend.getSpecificTable(connection, table);

        try {
            rsmd = specificTable.getMetaData();
            tableColumCount = rsmd.getColumnCount();

            for(int i = 0; i < tableColumCount; i++){
                columNames.add(rsmd.getCatalogName(i));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        for(int i = 0; i < (columNames.size()-1); i++){
            System.out.print(columNames.get(i) + " ");
        }

        try {
            while(specificTable.next()){
                for(int i = 0; i < tableColumCount; i++){
                    System.out.print(specificTable.getString(i) + " ");
                }
                System.out.print("\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getLogin(){
        scanner = new Scanner(System.in);

        System.out.println("SQLViewer | DBP 6 | Chris Thiele");
        System.out.println("--------------------------------\n");
        System.out.print("Login Name: ");
        loginName = scanner.nextLine();
        System.out.print("Login Pass: ");
        loginPass = scanner.nextLine();

        connection = SqlBackend.getConnection(loginName, loginPass);

        showMenu();
    }
}
