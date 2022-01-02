package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.PatternSyntaxException;

public class TimeFilter implements Filter{
    @Override
    public boolean check(JSONObject requestBody) {
        // controllo se è presente innanzitutto la chiave "range"
        if (!requestBody.has("range")) {
            return false;
        }

        // controllo se è stato fornito un doppio orario; se si controllo che i due orari inclusi siano
        // validi
        String[] doubleTimeTest = this.twoItemsRange(requestBody.getString("range"));
        if (doubleTimeTest != null) {
            // doppio orario fornito, controllo entrambi gli orari
            try {
                LocalTime time1 = LocalTime.parse(doubleTimeTest[0]);
                LocalTime time2 = LocalTime.parse(doubleTimeTest[1]);
            } catch (DateTimeParseException e) {
                // orari non validi
                return false;
            }
        } else {
            // orario singolo fornito, controllo se è valido
            try {
                LocalTime time = LocalTime.parse(requestBody.getString("range"));
            } catch (DateTimeParseException e) {
                // orario fornito non valido
                return false;
            }
        }

        // l'orario/gli orari forniti sono tutti validi
        return true;
    }

    @Override
    public Feed filter(JSONObject requestBody, Feed feed) {
        // ottengo gli orari
        String[] doubleTimeTest = twoItemsRange(requestBody.getString("range"));
        LocalTime timeStart, timeEnd;

        if (doubleTimeTest != null) {
            timeStart = LocalTime.parse(doubleTimeTest[0]);
            timeEnd = LocalTime.parse(doubleTimeTest[1]);
        } else {
            timeStart = LocalTime.parse(requestBody.getString("range"));
            timeEnd = null;
        }

        // vado a scegliere i post che soddisfano il filtro
        Feed filteredFeed = new Feed();
        for (int i = 0; i < feed.getTotalPost(); i++) {
            Post p = feed.getSinglePost(i);

            if (p.getDataOraPubblicazione().isAfter(ChronoLocalDateTime.from(timeStart))) {
                if (timeEnd != null && p.getDataOraPubblicazione().isBefore(ChronoLocalDateTime.from(timeEnd)))
                    filteredFeed.addPost(p);
                else
                    filteredFeed.addPost(p);
            }
        }

        return filteredFeed;
    }

    // questa funzione riceve in input il range orario e controlla se ci sono due orari (separati da -)
    // ad esempio 20:00-22:00
    private String[] twoItemsRange(String range) {
        String[] times;

        try {
            times = range.split("-");
        } catch (PatternSyntaxException e) {
            times = null;
        }

        return times;
    }
}
