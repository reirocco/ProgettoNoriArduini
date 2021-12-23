package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Request {
    private final RestTemplate restTemplate;

    public Request(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // metodo che permette di effettuare una richesta GET all'url desiderato e ne ritorna il risultato come stringa
    public String plainTextGetRequest(String url) {
        return this.restTemplate.getForObject(url, String.class, 1);
    }

    // metodo che permette di effettuare una richesta GET all'url desiderato e ne ritorna il risultato come un oggetto JSON
    public JSONObject jsonGetRequest(String url) {
        return this.restTemplate.getForObject(url, JSONObject.class, 1);
    }
}
