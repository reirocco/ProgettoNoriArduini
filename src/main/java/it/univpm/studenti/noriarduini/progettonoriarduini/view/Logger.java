package it.univpm.studenti.noriarduini.progettonoriarduini.view;

public class Logger {
    public static void printInfoMessage(String msg) {
        System.out.println("[ SERVER ] " + msg);
    }

    public static void printErrorMessage(String msg) {
        System.err.println("[ SERVER ERROR! ] Errore - " + msg);
    }

    public static void printSeparator() {
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void printServerUrl() {
        System.out.println(" --> http://localhost:8080");
    }
}
