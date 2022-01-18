package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import it.univpm.studenti.noriarduini.progettonoriarduini.stats.Stats;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.AccessTokenValidator;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;
import java.util.Map;

/**
 * <b>APIController class</b>
 * <p>
 * La classe provvede alla definizione delle rotte richiamabili tramite chiamata HTTP da un host generico.
 * <p>
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
@RestController
public class APIController {

    /**
     * Testa se il server è avviato:
     * ritorna al client lo status http 302 se è raggiungibile.
     * <p>
     *
     * @return ResponseEntity ritorna una istanza dell'oggetto contenente il codice http 302
     * @see ResponseEntity Response Entity object
     */
    @RequestMapping("/")
    public ResponseEntity home() {
        return new ResponseEntity(HttpStatus.FOUND);
    }

    /**
     * La rotta serve per ricevere le statistiche sui post di un determinato utente nel tempo.
     * Ritorna la mappa di un JSON contenente le statistiche.
     * <p>
     *
     * @return <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html">ResponseEntity</a> codice di risposta 302
     * @see Stats#buildStats(Feed)  vedi buildStats
     * @see Feed vedi Feed
     */
    @RequestMapping("/stats")
    public Map<String, Object> stats() {
        try {
            return FacebookService.getUserStats().toMap();
        } catch (HttpClientErrorException e) {
            // comunico l'errore in console...
            Logger.printErrorMessage("Impossibile ottenere le statistiche sui post: " + e.getMessage());
            // ... e all'utente ritonando un JSON apposito
            JSONObject errorObj = new JSONObject(e.getResponseBodyAsString());
            return errorObj.toMap();
        } catch (DateTimeException e) {
            // comunico l'errore in console...
            Logger.printErrorMessage("Impossibile ottenere le statistiche sui post: " + e.getMessage());
            // ... e all'utente creando un JSON apposito
            JSONObject errorObj = new JSONObject();
            errorObj.put("error", e.getMessage());
            return errorObj.toMap();
        }
    }

    /**
     * La rotta risponde alle richieste http con metodo POST effettuate dagli host e ne restituisce sotto forma di mappa
     * (<a href="https://www.javadoc.io/static/org.json/json/20170516/org/json/JSONObject.html#toMap--">vedi org.json.JSONObject</a>)
     * un Feed filtrato a seconda dei filtri inseriti nel json di input.
     * <p>
     *
     * @param json input contenente i filtri
     * @return Ritorna la <a href="https://www.javadoc.io/static/org.json/json/20170516/org/json/JSONObject.html#toMap--">mappa</a> di un Feed contenente i post filtrati in base ai campi del json di input
     */
    @PostMapping("/filters")
    public Map<String, Object> postBody(@RequestBody String json) {
        try {
            return FacebookService.getFilteredResults(json).toMap();
        } catch (WrongFilterException e) {
            Logger.printErrorMessage("Impossibile filtrare i post: " + e.getMessage());
            return e.getMessageAsJson().toMap();
        } catch (HttpClientErrorException e) {
            Logger.printErrorMessage("Impossibile filtrare i post: " + e.getMessage());
            JSONObject errorObj = new JSONObject(e.getResponseBodyAsString());
            return errorObj.toMap();
        }
    }

    /**
     * La rotta risponde a qualsiasi tipo di richiesta e controlla che il token dell'applicazione sia ancora valido
     * <p>
     *
     * @return Ritorna la <a href="https://www.javadoc.io/static/org.json/json/20170516/org/json/JSONObject.html#toMap--">mappa</a>
     * di un json contente un flag che specifica se il token dell'applicativo è ancora valido (vedi condizioni:
     * <a href="https://developers.facebook.com/docs/graph-api/overview/rate-limiting">Graph api rate Limit</a>)
     */
    @RequestMapping("/tokenTest")
    public Map<String, Object> tokenTest() {
        return AccessTokenValidator.testTokenValidity().toMap();
    }
}