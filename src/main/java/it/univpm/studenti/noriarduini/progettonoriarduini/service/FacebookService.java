package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.stats.Stats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;

/**
 * Contiene i metodi principali per il funzionamento del programma.
 *
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.stats.Stats
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.Filter
 */
@Service
public class FacebookService {

    /**
     * Ottiene le statistiche sui post dell'utente in base alla frequenza temporale di pubblicazione.
     *
     * @return <code>JSONObject</code> che contiene il numero di post pubblicati dall'utente nelle varie fascie temporali
     * @throws HttpClientErrorException errore che si genera durante la richiesta HTTPS verso le API di Facebook
     * @throws DateTimeException errore che si genera in caso di errori con il calcolo delle date
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.stats.Stats
     */
    public static JSONObject getUserStats() throws HttpClientErrorException, DateTimeException {
        // ottenimento del JSON dell'intero feed
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + "&limit=200"));
        // ritorno il tutto
        Feed feed = Feed.buildFromJsonArray(feedRaw);
        return Stats.buildStats(feed);
    }

    /**
     * Filtra il feed dell'utente in base ai filtri che vengono inseriti nel corpo della richiesta.
     * Per specificare in base a cosa bisogna filtrare i post del feed (e quindi quali filtri utilizzare è necessario
     * specificare queste informazioni dentro un JSON che va inserito nel corpo della richiesta. La sintassi del JSON
     * è la seguente:<br><br>
     *
     * <code>
     *     {
     *         "since": "2021-12-24",
     *         "until": "2022-01-17",
     *         "range": "12:00-19:45",
     *         "keywords": ["word1", "word2", ...],
     *         "dictionary": false,
     *         "full_words": false,
     *         "case_sensitive": false
     *     }
     * </code><br><br>
     *
     * Per ulteriori informazioni sui dati da inserire in ciascuno dei campi precedenti, vedere rispettivamente le classi
     * <code>DateFilter</code>, <code>TimeFilter</code>, <code>KeyWordsFilter</code>.
     *
     * @param json corpo della richiesta (in formato JSON) che contiene i filtri da applicare e i vari parametri.
     * @return <code>JSONObject</code> contenente i post del feed filtrati.
     * @throws WrongFilterException se i filtri dovessero essere composti in modo errato.
     * @throws HttpClientErrorException in caso di errore durante la richiesta HTTPS verso le API di Facebook.
     *
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException
     */
    public static JSONObject getFilteredResults(String json) throws WrongFilterException, HttpClientErrorException {
        // ottengo tutto il feed dell'utente
        Request request = new Request(new RestTemplateBuilder());
        JSONObject requestBody = new JSONObject(json);
        JSONArray feedArray = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken()));
        Feed feed = Feed.buildFromJsonArray(feedArray);

        DateFilter dateFilter = new DateFilter();
        TimeFilter timeFilter = new TimeFilter();
        KeyWordsFilter keyWordsFilter = new KeyWordsFilter();

        // controllo se i filtri sono validi - se lo sono filtro i post
        if (dateFilter.check(requestBody))
            feed = dateFilter.filter(requestBody, feed);
        if (timeFilter.check(requestBody))
            feed = timeFilter.filter(requestBody, feed);
        if (keyWordsFilter.check(requestBody))
            feed = keyWordsFilter.filter(requestBody, feed);

        // ritorno il feed filtrato
        JSONObject result = new JSONObject();
        result.put("data", Feed.fromFeedToJsonArray(feed));
        return result;
    }
}