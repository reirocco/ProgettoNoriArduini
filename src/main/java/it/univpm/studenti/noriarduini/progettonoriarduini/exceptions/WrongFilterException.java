package it.univpm.studenti.noriarduini.progettonoriarduini.exceptions;

import org.json.JSONObject;

public class WrongFilterException extends ProjectException {
    private String message;

    public WrongFilterException(String msg) {
        super();
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public JSONObject getMessageAsJson() {
        JSONObject obj = new JSONObject();
        obj.put("filter_error", this.message);
        return obj;
    }
}
