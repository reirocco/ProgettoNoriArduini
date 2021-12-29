package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class Request {
    private final RestTemplate restTemplate;

    public Request(@NotNull RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /* metodo che permette di effettuare una richiesta GET all'url desiderato e ne ritorna il risultato come stringa
     * il metodo adotta una approccio ricorsivo poichè i json ritornati dalle API di facebook sono limitati ad un numero
     * massimo di elementi (si può regolare il massimo impostando il parametro LIMIT), è necessagio quindi usare i link di
     * paginazione messi a disposizione nello stesso per poter scorrere tutte le pagine fino alla fine e quindi recuperare
     * tutti gli elementi.
     *
     * @param url è la stringa contenente l'url con i parametri della richiesta da effettuare
     * @return JSONArray contiene tutti gli elementi della richiesta
    */
    public JSONArray jsonArrayGetRequest(String url) {
        String tmp = "{}";
        try {
            tmp = this.restTemplate.getForObject(url, String.class, 1);
        }catch(HttpClientErrorException e){
            throw e;
        }
        JSONObject j = new JSONObject(tmp);
        JSONArray array = new JSONArray();
        if (j.has("data")){
            array = j.getJSONArray("data");
        }
        if(j.has("paging") && j.getJSONObject("paging").has("next")) {
            array.putAll(new JSONArray(this.jsonArrayGetRequest(j.getJSONObject("paging").getString("next"))));
        }
        return array;
    }

}
