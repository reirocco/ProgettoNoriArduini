package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;
import java.util.ArrayList;

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

    public void loadConfigurations() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(this.configFilesFolder + "config.json")));
            JSONObject jsonObject = new JSONObject(jsonContent);
            this.accessToken = (String) jsonObject.get("access_token");
        } catch (IOException e) {
            Logger.printErrorMessage("Impossibile caricare le configurazioni del server: errore di I/O (probabilmente il file non esiste)");
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
            e.printStackTrace();
        }
    }
}
