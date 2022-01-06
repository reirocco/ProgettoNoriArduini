package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.AccessTokenTest;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Configuration;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;
import java.util.Map;

@RestController
public class APIController {
    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

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

    @RequestMapping("/tokenTest")
    public Map<String, Object> tokenTest() {
        return AccessTokenTest.testTokenValidity().toMap();
    }
}