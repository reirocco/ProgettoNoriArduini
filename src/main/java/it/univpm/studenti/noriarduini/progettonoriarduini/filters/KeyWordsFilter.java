package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.PostDictionary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Permette di filtrare il feed dell'utente, mostrando solamente i post che contengono determinate parole chiave.
 *
 * <h2>Composizione del JSON dei filtri</h2>
 * Per applicare i filtri sulle parole chiave il JSON dei filtri viene composto così: (nell'esempio vengono indicati i valori
 * di default per le chiavi booleane)<br><br>
 * <code>
 *     {
 *         "keywords": ["word1", "word2", ...],
 *         "dictionary": false,
 *         "full_words": false,
 *         "case_sensitive": false
 *     }
 * </code>
 *
 * <ul>
 *     <li>
 *         <code>keywords</code>:
 *         contiene la lista (Array) delle parole chiave che devono essere contenute nei post. Se lasciato vuoto prende
 *         le parole chiave dal dataset che viene caricato all'avvio del programma e che è contenuto nel file
 *         <code>config/dataset.json</code>
 *     </li>
 *     <li>
 *         <code>dictionary</code>:
 *         se impostato a <code>true</code>, indica che verrà usato un dizionario per la ricerca delle parole.
 *         Utilizzando questo metodo, ad ogni post che soddisfa il filtro verrà visualizzata anche una lista delle
 *         parole chiave contenute in quel post e la quantità di esse.
 *     </li>
 *     <li>
 *         <code>full_words</code>:
 *         se impostato a <code>true</code>, indica che la ricerca includerà solamente parole intere, e non anche porzioni
 *         di parole.
 *     </li>
 *     <li>
 *         <code>case_sensitive</code>:
 *         se impostato a <code>true</code>, indica che la ricerca deve essere fatta utilizzando il paradigma case-sensitive,
 *         ovvero che viene fatta la distinzione tra lettere maiuscole e minuscole.
 *     </li>
 * </ul>
 */
public class KeyWordsFilter implements Filter {

    /**
     * Permette di verificare se il filtro sulle parole chiave è stato inserito correttamente. Per informazioni sulla
     * composizione del filtro delle parole chiave, vedere la classe <code>KeyWordsFilter</code>.
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @return <code>true</code> se il filtro è stato inserito correttamente, <code>false</code> in caso contrario.
     * @see KeyWordsFilter
     */
    @Override
    public boolean check(JSONObject requestBody) {
        return requestBody.has("keywords");
    }

    /**
     * Applica il filtro per le parole chiave sul feed.
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @param feed Feed in input
     * @return <code>Feed</code> contenente i post che soddisfano il filtro.
     */
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
