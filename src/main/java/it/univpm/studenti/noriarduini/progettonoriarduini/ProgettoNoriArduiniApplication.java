package it.univpm.studenti.noriarduini.progettonoriarduini;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Configuration;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgettoNoriArduiniApplication {
    // questo oggetto contiene tutte le configurazioni necessarie per il corretto funzionamento dell'app
    public static Configuration conf = new Configuration();

    public static void main(String[] args) {
        // saluto l'utente
        Logger.printInfoMessage("Facebook API server - di Federico Arduini e Rocco Nori");
        Logger.printInfoMessage("Realizzato per il progetto di OOP dell'Università Politecnica delle Marche");
        Logger.printSeparator();

        // leggo il file di config (dove è contenuto l'access token) e il dataset delle parole chiave.
        conf.loadConfigurations();
        conf.loadDataset();

        // inizio avvio del server spring
        Logger.printInfoMessage("Avvio del server Spring in corso...");
        SpringApplication.run(ProgettoNoriArduiniApplication.class, args);

        // comunico che il server spring è stato avviato correttamente
        Logger.printInfoMessage("Server Spring avviato correttamente. Da adesso è possibile effettuare tutte le chiamate verso questo server all'indirizzo:");
        Logger.printServerUrl();
    }
}
