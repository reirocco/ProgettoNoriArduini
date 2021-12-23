package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private String message;
    private String id;
    private LocalDateTime dataOraPubblicazione;

    public Post (String message, String id, LocalDateTime dataOraPubblicazione) {
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
}
