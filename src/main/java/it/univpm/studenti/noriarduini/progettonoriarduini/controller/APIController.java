package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.AccessTokenTest;
import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class APIController {
    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

    @RequestMapping("/stats")
    public Map<String, Object> stats() {
        return FacebookService.getUserStats().toMap();
    }

    @PostMapping("/filters")
    public Map<String, Object> postBody(@RequestBody String json) {
        return FacebookService.getFilteredResults(json).toMap();
    }

    @RequestMapping("/tokenTest")
    public Map<String, Object> tokenTest() {
        return AccessTokenTest.testTokenValidity().toMap();
    }
}