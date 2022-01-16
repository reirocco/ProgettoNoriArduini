package it.univpm.studenti.noriarduini.progettonoriarduini;

import it.univpm.studenti.noriarduini.progettonoriarduini.utility.AccessTokenValidator;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Configuration;
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

        // vado a controllare se il token di accesso è valido, se non lo è lo comunico all'utente
        if (!AccessTokenValidator.tokenIsValid())
            Logger.printErrorMessage("Il token di accesso fornito non è più valido. Modificare il file config/config.json e inserire un access token valido.");

        // comunico che il server spring è stato avviato correttamente ed è pronto all'uso
        Logger.printInfoMessage("Server Spring avviato correttamente. Da adesso è possibile effettuare tutte le chiamate verso questo server all'indirizzo:");
        Logger.printServerUrl();
    }
}
