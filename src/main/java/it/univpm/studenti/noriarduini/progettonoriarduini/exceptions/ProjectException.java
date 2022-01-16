package it.univpm.studenti.noriarduini.progettonoriarduini.exceptions;

import org.json.JSONObject;

public abstract class ProjectException extends Exception {
    private String message;

    public abstract String getMessage();

    public abstract JSONObject getMessageAsJson();
}
