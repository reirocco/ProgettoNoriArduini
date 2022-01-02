package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.Request;
import it.univpm.studenti.noriarduini.progettonoriarduini.view.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFilter implements Filter{
    @Override
    public boolean check(JSONObject requestBody) {
        // controllo se la data su since è presente ed è corretta. in caso dovesse essere errata o mancante
        // il filtro verrà considerato non valido e quindi non verrà applicato alcun filtro nel feed
        if (requestBody.has("since")) {
            try {
                LocalDate dateSince = LocalDate.parse(requestBody.getString("since"));
            } catch (DateTimeParseException e) {
                // sollevare eccezione personalizzata - filtro composto male
                return false;
            }
        } else {
            return false;
        }

        // controllo se la data su until è presente e corretta. se non dovesse essere presente o corretta il filtro
        // verrà ugualmente applicato ma solo utilizzando since
        if (requestBody.has("until")) {
            try {
                LocalDate dateSince = LocalDate.parse(requestBody.getString("until"));
            } catch (DateTimeParseException e) {
                // sollevare eccezione personalizzata - filtro composto male
                return false;
            }
        }

        return true;
    }

    public Feed filter(JSONObject requestBody, Feed fake) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Locale loc = new Locale("en", "it");
        String payload = "";

        if (requestBody.has("since")) {
            try {
                LocalDate dateTime = LocalDate.parse(requestBody.getString("since"), formatter);
                payload += "&since=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                Logger.printErrorMessage(e.getMessage());
                throw e;
            }
        }

        if (requestBody.has("until")) {
            try {
                LocalDate dateTime = LocalDate.parse(requestBody.getString("until"), formatter);
                payload += "&until=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                Logger.printErrorMessage(e.getMessage());
            }
        }

        // assemblo richiesta http e la invio alle API di facebook graph
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + payload));

        return Feed.buildFromJsonArray(feedRaw);
    }
}
