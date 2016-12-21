package sqlviewer;

/**
 * Main Method to run the Program
 */
public class Main {

    public static void main(String[] args) {
	    SqlConsole sqlConsole = new SqlConsole();

        try {
            sqlConsole.showMenu();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}