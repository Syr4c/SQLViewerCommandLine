package sqlviewer;

/**
 * SQLViewerCommandLine
 * Datenbank Praktikum Termin 6
 *
 * Chris Thiele
 * chris.thiele@haw-hamburg.de
 * 21.12.2016
 * */

public class Main {

    /**
     * Main to run the Program
     * */
    public static void main(String[] args) {
	    SqlConsole sqlConsole = new SqlConsole();

        try {
            sqlConsole.showMenu();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}