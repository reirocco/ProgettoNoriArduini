package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import org.json.JSONObject;

public interface Filter {
    // metodo che controlla se il filtro è presente ed è stato scritto correttamente
    public boolean check(JSONObject requestBody);

    // metodo che filtra i post del feed
    public Feed filter(JSONObject requestBody, Feed feed);
}