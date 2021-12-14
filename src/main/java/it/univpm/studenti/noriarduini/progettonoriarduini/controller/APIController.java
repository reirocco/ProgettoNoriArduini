package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import java.util.concurrent.atomic.AtomicLong;


import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
    @RequestMapping("/ciao")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Ciao! :)";
    }
}