package it.univpm.studenti.noriarduini.progettonoriarduini.stats;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    private Random rand;

    @BeforeEach
    void setUp() {
        // istanzio classi per il numero random
        this.rand = new Random();
    }

    @Test
    void buildStats() throws JSONException {
        JSONArray array = new JSONArray();
        Feed testFeed = new Feed();
        int max = this.rand.nextInt(20);

        for (int i = 0; i < max; i++) { // numero randomico da 1 a 19
            //  creo la data random
            long minDay = LocalDate.of(2000, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2020, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            // creo il post
            Post p = new Post("Test message", String.valueOf(i), randomDate.atStartOfDay());

            // creo statistiche corrette

            // aggiungo il post al testFeed di test
            testFeed.addPost(p);

        }

        // conto quanti post sono stati fatti nei vari archi temporali e metto questi risultati dentro un json
        JSONObject result = new JSONObject();

        LocalDateTime today = LocalDate.now().atStartOfDay();                            // 2021-23-12T00:00:00
        LocalDateTime yesterday = today.withDayOfMonth(today.getDayOfMonth() - 1);       // 2021-22-12T00:00:00
        LocalDateTime thisWeek = today.with(DayOfWeek.MONDAY);                           // 2021-20-12T00:00:00
        LocalDateTime thisMonth = today.withDayOfMonth(1);                               // 2021-01-12T00:00:00
        LocalDateTime thisYear = today.withDayOfYear(1);                                 // 2021-01-01T00:00:00
        LocalDateTime prevYear = today.withYear(today.getYear() - 1).withDayOfYear(1);   // 2020-01-01T00:00:00

        result.put("total", testFeed.getTotalPost());
        result.put("today", testFeed.getPostNumberByTime(today, LocalDateTime.now()));
        result.put("yesterday", testFeed.getPostNumberByTime(yesterday, yesterday.withHour(23).withMinute(59).withSecond(59)));
        result.put("this_week", testFeed.getPostNumberByTime(thisWeek, LocalDateTime.now()));
        result.put("this_month", testFeed.getPostNumberByTime(thisMonth, LocalDateTime.now()));
        result.put("this_year", testFeed.getPostNumberByTime(thisYear, LocalDateTime.now()));
        result.put("prev_year", testFeed.getPostNumberByTime(prevYear, prevYear.withDayOfYear(364)));

        assertEquals(result.toString(), Stats.buildStats(testFeed).toString());
    }

}