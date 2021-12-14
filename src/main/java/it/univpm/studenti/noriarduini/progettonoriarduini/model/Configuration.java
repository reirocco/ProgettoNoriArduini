package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Configuration {
    private String accessToken;
    private ArrayList<String> dataSet;

    public Configuration() {
        this.dataSet = new ArrayList<>();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void loadConfigurations() {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("config/config.json"));
            this.accessToken = (String) jsonObject.get("access_token");
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("Impossibile caricare le configurazioni del server: file non trovato");
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare le configurazioni del server: errore di I/O");
        } catch (ParseException e) {
            Logger.printErrorMessage("Il file di configurazione non è valido; probabilmente è mal-formattato.");
        }
    }

    public void loadDataset() {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("config/dataset.json"));
            JSONArray wordList = (JSONArray) jsonObject.get("keywords");
            this.dataSet = wordList;
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("Impossibile caricare il dataset delle parole chiave: file non trovato");
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare il dataset delle parole chiave: errore di I/O");
        } catch (ParseException e) {
            Logger.printErrorMessage("Il file del dataset delle parole chiave non è valido; probabilmente è mal-formattato.");
        }
    }
}
