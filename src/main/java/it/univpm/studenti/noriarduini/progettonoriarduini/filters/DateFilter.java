package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import org.json.JSONObject;

public class DateFilter implements Filter{
    @Override
    public boolean check(JSONObject requestBody) {
        // controllo se la data su since è presente...
        return true;
    }

    @Override
    public Feed filter(JSONObject requestBody, Feed feed) {
        return null;
    }
}
