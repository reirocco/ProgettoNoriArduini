package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Post {
    private String message;
    private String id;
    private LocalDateTime dataOraPubblicazione;

    public Post(String message, String id, LocalDateTime dataOraPubblicazione) {
        this.message = message;
        this.id = id;
        this.dataOraPubblicazione = dataOraPubblicazione;
    }

    public Post(String message, String id, String dataOraPubblicazione) {
        this.message = message;
        this.id = id;

        // conversione della data di pubblicazione da String a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        this.dataOraPubblicazione = LocalDateTime.parse(dataOraPubblicazione, formatter);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDataOraPubblicazione() {
        return dataOraPubblicazione;
    }

    public void setDataOraPubblicazione(LocalDateTime dataOraPubblicazione) {
        this.dataOraPubblicazione = dataOraPubblicazione;
    }

    public boolean hasKeyWords(ArrayList<String> dataset) {
        return dataset.stream().parallel().anyMatch(this.message::contains);
    }

/*
    public Pair<Integer, String> hasKeyWordsWithDictionary(ArrayList<String> dataset) {
        Map<String, String> map = new HashMap<String, String>();
        Pair<>
        for (String s : dataset) {
            if (this.message.contains(s)) {
                if (!map.containsKey(s)) {
                    int count = StringUtils.countOccurrencesOf(this.message, s);
                    map.put(s, String.valueOf(count));
                } else {
                    int num = Integer.valueOf(map.get(s));
                    map.replace(s, String.valueOf(num), String.valueOf(num + 1));
                }
            }
        }
        return
    }

 */


    public JSONObject exportJSONObject() {
        JSONObject j = new JSONObject();
        j.append("id", this.getId());
        j.append("message", this.getMessage());
        j.append("date", this.getDataOraPubblicazione());
        return j;
    }
}
