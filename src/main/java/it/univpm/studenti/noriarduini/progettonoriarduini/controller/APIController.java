package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
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
        /*Object res;
        try{
            res = FacebookService.getFilteredresults(json).toString();
        }catch (Exception e){
            Logger.printErrorMessage(e.getLocalizedMessage());
            res = e.getMessage();
        }*/

        return FacebookService.getFilteredResults(json).toMap();
    }
}