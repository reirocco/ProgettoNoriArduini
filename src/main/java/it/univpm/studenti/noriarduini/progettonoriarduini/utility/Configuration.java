package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * <b>Configuration class</b>
 * <p>
 * la classe provvede alla definizione e al caricamento dei metodi e dei dataset necessari all'esecuzione del progetto.
 * vengono caricati da file json il dataset delle parole e il token delle API di facebook
 * <p>
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    /**
     * access token delle API
     */
    private String accessToken;
    /**
     * dataset interno delle parole
     */
    private ArrayList<String> dataSet;
    /**
     * path della directory contenente i file di configurazione da importare
     */
    private String configFilesFolder;

    /**
     * costruttore della classe
     */
    public Configuration() {
        this.dataSet = new ArrayList<>();
        this.configFilesFolder = "config/";
    }

    /**
     * restituisce l'access token
     * @return stringa access token
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * get dataset parole
     * @return array dataset delle parole
     */
    public ArrayList<String> getDataSet() {
        return this.dataSet;
    }

    /**
     * metodo per caricare(ricaricare) le configurazioni da locale
     */
    public void loadConfigurations() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(this.configFilesFolder + "config.json")));
            JSONObject jsonObject = new JSONObject(jsonContent);
            this.accessToken = (String) jsonObject.get("access_token");
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare le configurazioni del server: errore di I/O (probabilmente il file non esiste). Maggiori dettagli: " + e.getMessage());
        }
    }

    /**
     * metodo per caricare(ricaricare) il dataset di parole dal locales
     */
    public void loadDataset() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(this.configFilesFolder + "dataset.json")));

            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray wordList = jsonObject.getJSONArray("keywords");

            for (Object x : wordList) {
                this.dataSet.add((String) x);
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare il dataset delle parole: errore di I/O (probabilmente il file dataset.json non esiste). Maggiori dettagli: " + e.getMessage());
        }
    }
}
