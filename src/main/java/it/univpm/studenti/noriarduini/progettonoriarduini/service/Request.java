package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class Request {

    private final RestTemplate restTemplate;

    public Request(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public String post(String url, Map<String, String> map) {
        
        
        return this.restTemplate.getForObject(url, String.class);
    }

}
