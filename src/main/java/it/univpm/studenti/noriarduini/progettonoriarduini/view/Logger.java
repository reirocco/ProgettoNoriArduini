package it.univpm.studenti.noriarduini.progettonoriarduini.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
        BufferedReader reader = null;
        String path = System.getProperty("user.dir");
        Boolean find = false;
        String port = "";

        try {
            reader = new BufferedReader(new FileReader(path + "/src/main/resources/application.properties"));
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("Impossibile leggere il file application.properties: " + e.getMessage());
        }

        if (reader != null) {
            try {
                String line = reader.readLine();
                while (!find || line != null) {
                    String[] param = line.split("=");

                    if (param[0].equals("server.port")) {
                        port = param[1];
                        find = true;
                    }
                    // read next line
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String ip = "";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            Logger.printErrorMessage("Impossibile ottenere l'indirizzo IP del tuo PC: " + e.getMessage());
        }

        System.out.println("Machine address:");
        System.out.println(" --> http://localhost:" + port);
        System.out.println(" --> http://127.0.0.1:" + port);
        System.out.println("Local address:");
        System.out.println(" --> http://" + ip + ":" + port);
    }
}
