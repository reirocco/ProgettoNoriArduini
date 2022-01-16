package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.PatternSyntaxException;

/**
 * Permette di filtrare il feed dell'utente, mostrando solamente i post che soddisfano delle condizioni temporali.
 *
 * <h2>Composizione del JSON dei filtri</h2>
 * Per applicare i filtri sull'orario il JSON dei filtri viene composto così:<br><br>
 * <code>
 *     {
 *         "range": 10:00-12:30
 *     }
 * </code><br>
 * In questo caso viene indicato che il filtro prenderà tutti i post che sono stati pubblicati in un orario compreso
 * tra i due orari indicati dentro <code>range</code>.<br><br>
 *
 * <code>
 *     {
 *         "range": 10:00
 *     }
 * </code><br>
 * In questo caso viene indicato che il filtro prenderà tutti i post che sono stati pubblicati a partire dall'orario
 * indicato in <code>range</code>.
 *
 * <strong>Gli orari sono scritti secondo il formato <code>HH:mm</code> (ore:minuti).</strong>
 */
public class TimeFilter implements Filter{
    /**
     * Permette di verificare se il filtro per la fascia oraria è stato inserito correttamente. Per informazioni sulla
     * composizione del filtro della fascia oraria, vedere la classe <code>TimeFilter</code>.
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @return <code>true</code> se il filtro è stato composto correttamente, <code>false</code> in caso contrario
     * @throws WrongFilterException se gli orari contenuti in <code>since</code> non sono scritti correttamente.
     * @see TimeFilter
     */
    @Override
    public boolean check(JSONObject requestBody) throws WrongFilterException {
        // controllo se è presente innanzitutto la chiave "range"
        if (!requestBody.has("range")) {
            return false;
        }

        // controllo se è stato fornito un doppio orario; se si controllo che i due orari inclusi siano
        // validi
        String[] doubleTimeTest = this.twoItemsRange(requestBody.getString("range"));
        if (doubleTimeTest != null && doubleTimeTest.length > 1) {
            // doppio orario fornito, controllo entrambi gli orari
            try {
                LocalTime time1 = LocalTime.parse(doubleTimeTest[0]);
                LocalTime time2 = LocalTime.parse(doubleTimeTest[1]);
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("Gli orari forniti nel filtro della fascia oraria (range) non sono corretti.");
            }
        } else {
            // orario singolo fornito, controllo se è valido
            try {
                LocalTime time = LocalTime.parse(requestBody.getString("range"));
            } catch (DateTimeParseException e) {
                throw new WrongFilterException("L'orario nel filtro della fascia oraria (range) non è corretto.");
            }
        }

        // l'orario/gli orari forniti sono tutti validi
        return true;
    }

    /**
     * Applica il filtro per la fascia oraria di pubblicazione dei post.
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @param feed Feed in input
     * @return <code>Feed</code> contenente i post che soddisfano le condizioni del filtro.
     */
    @Override
    public Feed filter(JSONObject requestBody, Feed feed) {
        // ottengo gli orari
        String[] doubleTimeTest = twoItemsRange(requestBody.getString("range"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeStart, timeEnd;

        if (doubleTimeTest != null && doubleTimeTest.length > 1) {
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
            // faccio il parsing dell'orario di pubblicazione del post a partire dalla sua data e ora completa
            LocalTime postTime = LocalTime.parse(p.getDataOraPubblicazione().format(formatter));

            if (postTime.isAfter(timeStart)) {
                if (timeEnd != null) {
                    if (postTime.isBefore(timeEnd))
                        filteredFeed.addPost(p);
                } else {
                    filteredFeed.addPost(p);
                }
            }
        }

        return filteredFeed;
    }

    /**
     * Permette di stabilire se in una stringa sono inseriti uno o due orari.
     *
     * @param range Stringa che contiene l'orario/gli orari (separati dal "-")
     * @return array di stringhe che contiene in posizione 0 il primo orario e in posizione 1 il secondo. In caso di orario
     * singolo ritorna un array di stringhe con solo la posizione 0.
     */
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
