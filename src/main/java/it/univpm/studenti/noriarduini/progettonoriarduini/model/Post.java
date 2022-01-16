package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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

    public boolean hasKeyWords(ArrayList<String> dataset, boolean full_words, boolean case_sensitive) {
        boolean found = false;

        Iterator datasetIterator = Arrays.stream(dataset.toArray()).iterator();
        while (!found && datasetIterator.hasNext()) {
            String next = datasetIterator.next().toString();
            System.err.println(next);
            // versione case insensitive
            //if (!found && this.getMessage().toLowerCase().contains(x.toLowerCase())) found = true;

            boolean res = false;
            // controllo parole intere
            if (full_words) {
                String[] messageWords = Splitter.split(this.message);
                Iterator iter = Arrays.stream(messageWords).iterator();
                while (!res && iter.hasNext()) {
                    if (case_sensitive)
                        res = iter.next().toString().equals(next);
                    else
                        res = iter.next().toString().equalsIgnoreCase(next);
                }
            } else {
                if (case_sensitive)
                    res = this.getMessage().contains(next);
                else {
                    res = StringUtils.containsIgnoreCase(this.message, next);
                }
            }
            // versione case sensitive
            if (!found && res) found = true;
        }

        return found;
    }

    public JSONObject exportToJSONObject() {
        JSONObject j = new JSONObject();

        j.put("id", this.getId());
        j.put("message", this.getMessage());
        j.put("date", this.getDataOraPubblicazione());

        return j;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj.getClass() == Post.class) {
            if (this.id.equals(((Post) obj).id)) {
                res = true;
            }
        }
        return res;
    }

}
