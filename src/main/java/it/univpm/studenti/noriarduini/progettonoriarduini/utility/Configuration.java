package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.Request;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private String accessToken;
    private ArrayList<String> dataSet;
    private String configFilesFolder;

    public Configuration() {
        this.dataSet = new ArrayList<>();
        this.configFilesFolder = "config/";
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public ArrayList<String> getDataSet() {
        return this.dataSet;
    }

    public void loadConfigurations() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(this.configFilesFolder + "config.json")));
            JSONObject jsonObject = new JSONObject(jsonContent);
            this.accessToken = (String) jsonObject.get("access_token");
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare le configurazioni del server: errore di I/O (probabilmente il file non esiste). Maggiori dettagli: " + e.getMessage());
        }
    }

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
