package it.univpm.studenti.noriarduini.progettonoriarduini.service;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.apache.tomcat.jni.Local;
import org.json.*;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class FacebookService {
    public static JSONObject getUserStats() {
        // ottenimento del JSON dell'intero feed
        Request request = new Request(new RestTemplateBuilder());
        JSONObject feedRaw = new JSONObject(request.plainTextGetRequest("https://graph.facebook.com/me/feed?access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken()));

        // popolo il feed con tutti i suoi post
        Feed feed = new Feed();
        JSONArray postArray = feedRaw.getJSONArray("data");

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
}