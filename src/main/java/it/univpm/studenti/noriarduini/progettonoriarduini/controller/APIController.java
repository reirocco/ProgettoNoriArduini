package it.univpm.studenti.noriarduini.progettonoriarduini.controller;

import java.util.concurrent.atomic.AtomicLong;
import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Configuration;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.FacebookService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String stats() {
        return "ok";
    }
}