package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class FacebookService {
    public static JSONObject getUserStats() {
        // ottenimento del JSON dell'intero feed
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken()));

        Feed feed = JSONArrayToFeed(feedRaw);

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

    public static JSONObject getFilteredResults(String json) {
        // ottengo tutto il feed dell'utente
        Request request = new Request(new RestTemplateBuilder());
        JSONObject requestBody = new JSONObject(json);
        JSONArray feedArray = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken()));
        Feed feed = JSONArrayToFeed(feedArray);

        // controllo se i filtri sono validi
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Locale loc = new Locale("en", "it");
        String payload = "";*/

        /* compongo il payload della mia richiesta in GET in base alle key del json
         * si possono verificare 3 casi:
         *  -> viene passata solo la chiave "since" : la richiesta restituisce tutti i post pubblicati da un utente a partire da una data specifica fino alla fine;
         *  -> viene passata solo la chiave "until" : la richiesta restituisce tutti i post pubblicati da un utente fino a una data specifica partendo dal primo;
         *  -> vengono passate entrambe le chiavi : la richiesta restituisce i post pubblicati fra le due date
         */
        /*if (j.has("since")) {
            try {
                LocalDate dateTime = LocalDate.parse(j.getString("since"), formatter);
                payload += "&since=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                Logger.printErrorMessage(e.getMessage());
                throw e;
            }
        }
        if (j.has("until"))
            try {
                LocalDate dateTime = LocalDate.parse(j.getString("until"), formatter);
                payload += "&until=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                Logger.printErrorMessage(e.getMessage());
            }

        // assemblo richiesta http e la invio alle API di facebook graph
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + payload));

        JSONObject result = new JSONObject();
        result.put("post", feedRaw);
        return result;*/
        return null;
    }

    private static Feed JSONArrayToFeed(JSONArray postArray) {
        // popolo il feed con tutti i suoi post
        Feed feed = new Feed();

        for (Object x : postArray) {
            JSONObject postRaw = (JSONObject) x;

            String postMsg;
            try {
                postMsg = postRaw.getString("message");
            } catch (JSONException e) {
                postMsg = "- empty -";
            }

            Post post = new Post(postMsg, postRaw.getString("id"), postRaw.getString("created_time"));
            feed.addPost(post);
        }
        return feed;
    }


}