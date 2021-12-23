package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class APIController {
    @RequestMapping("/hello")
    public String greeting() {
        return "Hello!";
    }

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

    @RequestMapping("/stats")
    public Map<String, Object> stats() {
        return FacebookService.getUserStats().toMap();
    }
}