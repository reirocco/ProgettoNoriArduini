package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FacebookService {
    public static JSONObject getUserStats() throws HttpClientErrorException, DateTimeException {
        // ottenimento del JSON dell'intero feed
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + "&limit=200"));

        Feed feed = Feed.buildFromJsonArray(feedRaw);

        // conto quanti post sono stati fatti nei vari archi temporali e metto questi risultati dentro un json
        JSONObject result = new JSONObject();

        LocalDateTime today = LocalDate.now().atStartOfDay();                            // 2021-23-12T00:00:00
        LocalDateTime yesterday = today.withDayOfMonth(today.getDayOfMonth() - 1);       // 2021-22-12T00:00:00
        LocalDateTime thisWeek = today.with(DayOfWeek.MONDAY);                           // 2021-20-12T00:00:00
        LocalDateTime thisMonth = today.withDayOfMonth(1);                               // 2021-01-12T00:00:00
        LocalDateTime thisYear = today.withDayOfYear(1);                                 // 2021-01-01T00:00:00
        LocalDateTime prevYear = today.withYear(today.getYear() - 1).withDayOfYear(1);   // 2020-01-01T00:00:00

        result.put("total", feed.getTotalPost());
        result.put("today", feed.getPostNumberByTime(today, LocalDateTime.now()));
        result.put("yesterday", feed.getPostNumberByTime(yesterday, yesterday.withHour(23).withMinute(59).withSecond(59)));
        result.put("this_week", feed.getPostNumberByTime(thisWeek, LocalDateTime.now()));
        result.put("this_month", feed.getPostNumberByTime(thisMonth, LocalDateTime.now()));
        result.put("this_year", feed.getPostNumberByTime(thisYear, LocalDateTime.now()));
        result.put("prev_year", feed.getPostNumberByTime(prevYear, prevYear.withDayOfYear(364)));

        // ritorno il tutto
        return result;
    }

    public static JSONObject getFilteredResults(String json) throws HttpClientErrorException, WrongFilterException {
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
        else
            Logger.printInfoMessage("Il filtro per data non verrà applicato.");

        if (timeFilter.check(requestBody))
            feed = timeFilter.filter(requestBody, feed);
        else
            Logger.printInfoMessage("Il filtro per fascia oraria non verrà applicato.");

        if (keyWordsFilter.check(requestBody))
            feed = keyWordsFilter.filter(requestBody, feed);
        else
            Logger.printInfoMessage("Il filtro per parole chiave non verrà applicato");

        // ritorno il feed filtrato
        JSONObject result = new JSONObject();
        result.put("data", Feed.fromFeedToJsonArray(feed));
        return result;
    }
}