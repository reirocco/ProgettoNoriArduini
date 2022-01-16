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

@Service
public class FacebookService {
    public static JSONObject getUserStats() throws HttpClientErrorException, DateTimeException {
        // ottenimento del JSON dell'intero feed
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + "&limit=200"));
        // ritorno il tutto
        Feed feed = Feed.buildFromJsonArray(feedRaw);
        return Stats.buildStats(feed);
    }


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