package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Rappresenta una richiesta HTTP verso un URL.
 */
@Service
public class Request {
    private final RestTemplate restTemplate;

    /**
     * Istanzia un oggetto di tipo Request.
     * @param restTemplateBuilder Builder che viene usato per creare una <code>RestTemplate</code>, ovvero un modello
     *                            per la realizzazione di client HTTP.
     */
    public Request(@NotNull RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Effettua una richiesta HTTP in GET all'URL fornito e ne ritorna la risposta testuale (plain text)
     *
     * @param url Indirizzo verso cui fare la richiesta
     * @return Testo contenente la risposta della richiesta
     * @throws HttpClientErrorException Quando la richiesta non va a buon fine e si ottiene un errore HTTP. Maggiori
     * informazioni sui codici di stato HTTP su <a href="https://it.wikipedia.org/wiki/Codici_di_stato_HTTP">questo indirizzo</a>.
     */
    public String plainTextGetRequest(String url) throws HttpClientErrorException {
        return this.restTemplate.getForObject(url, String.class);
    }

    /**
     * Effettua una richiesta HTTP in GET all'url fornito e ne ritorna il risultato sotto forma di <code>JSONArray</code>.<br>
     * Il metodo adotta un approccio ricorsivo poiché i JSON ritornati dalle API di Facebook sono limitati a un numero
     * massimo di elementi (si può regolare il massimo impostando il parametro LIMIT); è necessario quindi usare i link di
     * paginazione contenuti nel JSON di risposta per poter scorrere tutte le pagine fino all'ultima e quindi recuperare
     * tutti gli elementi.
     *
     * @param url Indirizzo verso cui fare la richiesta
     * @return <code>JSONArray</code> che contiene tutti i dati ottenuti dalla richiesta
     * @throws HttpClientErrorException Quando la richiesta non va a buon fine e si ottiene un errore HTTP. Maggiori
     * informazioni sui codici di stato HTTP su <a href="https://it.wikipedia.org/wiki/Codici_di_stato_HTTP">questo indirizzo</a>.
    */
    public JSONArray jsonArrayGetRequest(String url) throws HttpClientErrorException {
        String tmp = "{}";
        tmp = this.restTemplate.getForObject(url, String.class, 1);

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
