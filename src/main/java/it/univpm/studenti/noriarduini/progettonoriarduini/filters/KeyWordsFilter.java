package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.PostDictionary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KeyWordsFilter implements Filter {

    @Override
    public boolean check(JSONObject requestBody) {
        return requestBody.has("keywords");
    }

    @Override
    public Feed filter(JSONObject requestBody, Feed feed) {
        ArrayList<String> s;
        boolean useDict = false;
        /*
            controllo se è stato inserito il filtro per le parole intere e per il case sensitive:
            --> se è stato inserito controllo che sia stato inserito un boolean, se lo è prendo il valore di quest'ultimo,
            altrimenti imposto false;
            --> se non è stato inserito imposto il valore a false
         */
        boolean full_words = requestBody.has("full_words") && (requestBody.get("full_words").toString().equalsIgnoreCase("true") || requestBody.get("full_words").toString().equalsIgnoreCase("false") && (boolean) requestBody.get("full_words"));
        boolean case_sensitive = requestBody.has("case_sensitive") && (requestBody.get("case_sensitive").toString().equalsIgnoreCase("true") || requestBody.get("case_sensitive").toString().equalsIgnoreCase("false") && (boolean) requestBody.get("case_sensitive"));
        if (requestBody.has("keywords")) {
            JSONArray array = requestBody.getJSONArray("keywords");
            if (requestBody.has("dictionary"))
                useDict = requestBody.getBoolean("dictionary");

            // ottengo le keywords che serviranno per filtrare i post
            if (array.length() > 0) {
                s = new ArrayList<String>();
                for (int i = 0; i < array.length(); i++) {
                    s.add(array.getString(i));
                }
            } else {
                // se l'utente non ha messo nessun valore dentro l'array delle keywords le vado a prendere dal dataset
                // che è stato caricato dal programma all'avvio
                s = ProgettoNoriArduiniApplication.conf.getDataSet();
            }

            // vado a filtrare i post
            Feed res = new Feed();

            for (int i = 0; i < feed.getTotalPost(); i++) {
                Post p = feed.getSinglePost(i);

                if (useDict) {
                    PostDictionary pd = new PostDictionary(p);
                    if (pd.hasKeyWords(s, full_words, case_sensitive)) res.addPost(pd);
                } else {
                    if (p.hasKeyWords(s, full_words, case_sensitive)) res.addPost(p);
                }
            }
            return res;
        }
        return feed;
    }

}
