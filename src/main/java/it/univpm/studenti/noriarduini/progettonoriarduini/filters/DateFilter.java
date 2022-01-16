package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Permette di filtrare il feed dell'utente, mostrando solamente i post che soddisfano delle condizioni sulle date.
 *
 * <h2>Composizione del JSON dei filtri</h2>
 * Per applicare i filtri sulle date il JSON dei filtri viene composto così:<br><br>
 * <code>
 *     {
 *         "since": 2021-12-24,
 *         "until": 2022-01-17
 *     }
 * </code>
 *
 * <ul>
 *     <li><code>since</code>: contiene la data iniziale (post pubblicati a partire da quella data).</li>
 *     <li><code>until</code>: contiene la data finale (post pubblicati entro quella data).</li>
 * </ul>
 *
 * <strong>Le date sono scritte secondo il formato <code>YYYY-MM-DD</code> (anno-mese-giorno).</strong>
 */
public class DateFilter implements Filter{
    /**
     * Permette di controllare se il filtro per le date è stato inserito correttamente. Per informazioni sulla
     * composizione del filtro delle date, vedere la classe <code>DateFilter</code>.
     *
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters
     * @param requestBody JSON dei filtri
     * @return <code>true</code> se il filtro è stato composto correttamente, <code>false</code> in caso contrario
     * @throws WrongFilterException se le date contenute in <code>since</code> e/o in <code>until</code> sono composte
     * in modo errato
     */
    @Override
    public boolean check(JSONObject requestBody) throws WrongFilterException {
        // controllo se la data su since è presente ed è corretta. in caso dovesse essere errata o mancante
        // il filtro verrà considerato non valido e quindi non verrà applicato alcun filtro nel feed
        if (requestBody.has("since")) {
            try {
                LocalDate dateSince = LocalDate.parse(requestBody.getString("since"));
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("La data di inizio (since) per il filtro sui giorni è scritta in modo scorretto");
            }
        }

        // controllo se la data su until è presente e corretta. se non dovesse essere presente o corretta il filtro
        // verrà ugualmente applicato ma solo utilizzando since
        if (requestBody.has("until")) {
            try {
                LocalDate dateSince = LocalDate.parse(requestBody.getString("until"));
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("La data di fine (until) per il filtro sui giorni è scritta in modo scorretto");
            }
        }

        return requestBody.has("since") || requestBody.has("until");
    }

    /**
     * Applica il filtro per le date di pubblicazione sul feed.
     * @param requestBody JSON dei filtri
     * @param feed Feed iniziale (ancora non filtrato). In questo caso specifico <strong>è necessario che questo parametro abbia
     *                     come valore <code>null.</code></strong>
     * @return <code>Feed</code> filtrato (contiene solamente i post che soddisfano le condizioni imposte dal filtro)
     * @throws WrongFilterException se le date contenute in <code>since</code> e/o in <code>until</code> sono composte
     *                              in modo errato
     */
    @Override
    public Feed filter(JSONObject requestBody, Feed feed) throws WrongFilterException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Locale loc = new Locale("en", "it");
        String payload = "";

        if (requestBody.has("since")) {
            try {
                LocalDate dateTime = LocalDate.parse(requestBody.getString("since"), formatter);
                payload += "&since=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("Impossibile ottenere la data di inizio (since) del filtro sui giorni: " + e.getMessage());
            }
        }

        if (requestBody.has("until")) {
            try {
                LocalDate dateTime = LocalDate.parse(requestBody.getString("until"), formatter);
                payload += "&until=" + dateTime.getDayOfMonth() + "+" + dateTime.getMonth().getDisplayName(TextStyle.FULL, loc) + "+" + dateTime.getYear();
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("Impossibile ottenere la data di fine (until) del filtro sui giorni: " + e.getMessage());
            }
        }

        // assemblo richiesta http e la invio alle API di facebook graph
        Request request = new Request(new RestTemplateBuilder());
        JSONArray feedRaw = new JSONArray(request.jsonArrayGetRequest("https://graph.facebook.com/me/feed?limit=100&access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken() + payload));

        return Feed.buildFromJsonArray(feedRaw);
    }
}
