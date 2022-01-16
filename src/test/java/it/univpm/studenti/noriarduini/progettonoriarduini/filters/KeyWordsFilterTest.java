package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifica la funzionalità del filtraggio per parole chiave.
 */
class KeyWordsFilterTest {
    private JSONObject requestBody;
    private Feed inputTestFeed;
    private Feed outputTestFeed;
    private KeyWordsFilter keyWordsFilter;

    /**
     * Impostazione di tutto l'ambiente di testing
     */
    @BeforeEach
    void setUp() {
        this.requestBody = new JSONObject();

        // istanzio oggetti per il test
        this.inputTestFeed = new Feed();
        this.outputTestFeed = new Feed();
        this.keyWordsFilter = new KeyWordsFilter();

        // popolo feed con 4 post di test
        Post p1 = new Post("politica salvini umanità renzi", "1", LocalDateTime.now());
        Post p2 = new Post("salvini", "2", LocalDateTime.now());
        Post p3 = new Post("umanità", "3", LocalDateTime.now());
        Post p4 = new Post("politica", "4", LocalDateTime.now());

        // popolo feed di input
        this.inputTestFeed.addPost(p1);
        this.inputTestFeed.addPost(p2);
        this.inputTestFeed.addPost(p3);
        this.inputTestFeed.addPost(p4);

        // popolo feed di output
        this.outputTestFeed.addPost(p1);
        this.outputTestFeed.addPost(p2);
        this.outputTestFeed.addPost(p3);
    }

    /**
     * Verifica la funzionalità del filtraggio per parole chiave, utilizzando un array di parole chiave già preimpostato.
     * @throws JSONException
     */
    @Test
    void filterWithExternalWords() throws JSONException {
        JSONArray array = new JSONArray();

        array.put("salvini");
        array.put("umanità");

        this.requestBody.put("keywords", (Object) array);
        Feed res = keyWordsFilter.filter(this.requestBody, this.outputTestFeed);

        // controllo se il feed restituito dal metodo ha lo stesso contenuto dell'oggetto feed di output che ho dichiarato.
        assertEquals(Feed.fromFeedToJsonArray(res).toString(), Feed.fromFeedToJsonArray(this.outputTestFeed).toString());
    }
}