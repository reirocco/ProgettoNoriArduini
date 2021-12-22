package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Request {
    private final RestTemplate restTemplate;

    public Request(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Post plainJsonGetRequest(String url) {
        // faccio la richiesta all'url che viene passato come parametro
        ResponseEntity<Post> response = this.restTemplate.getForEntity(url, Post.class, 1);

        // controllo il codice di risposta HTTP...
        if(response.getStatusCode() == HttpStatus.OK) {
            // ... se è OK (200) allora va bene, ritorno il corpo della risposta
            return response.getBody();
        } else {
            // ... altrimenti c'è qualcosa che non va, non torno nulla
            return null;
        }
    }
}
