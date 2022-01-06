package it.univpm.studenti.noriarduini.progettonoriarduini.exceptions;

import org.json.JSONObject;

public class WrongFilterException extends Exception {
    private String message;

    public WrongFilterException(String msg) {
        super();
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public JSONObject getMessageAsJson() {
        JSONObject obj = new JSONObject();
        obj.put("filter_error", this.message);
        return obj;
    }
}
