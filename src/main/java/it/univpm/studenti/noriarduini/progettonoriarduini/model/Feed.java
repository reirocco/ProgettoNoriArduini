package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Feed class
 *
 * <p>
 * <p>
 * definisce l'insieme dei post di un utente
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class Feed {
    /**
     * array dei post
     */
    private final ArrayList<Post> listaPost;

    /**
     * coustruttore della classe
     */
    public Feed() {
        this.listaPost = new ArrayList<>();
    }

    /**
     * aggiunge un post all'array
     *
     * @param p Post post da inserire
     */
    public void addPost(Post p) {
        this.listaPost.add(p);
    }

    /**
     * ritorna il post richiesto
     *
     * @param index numero del post
     * @return post ritornato
     */
    public Post getSinglePost(int index) {
        return this.listaPost.get(index);
    }

    /**
     * ritorna il numero dei post nell'array
     *
     * @return int numero dei post
     */
    public int getTotalPost() {
        return this.listaPost.size();
    }

    /**
     * ritorna il numero di un post in base alla sua data
     *
     * @param startDateTime data di inizio
     * @param endDateTime   data di fine
     * @return int numero del post
     */
    public int getPostNumberByTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int n = 0;

        for (Post x : this.listaPost) {
            if (x.getDataOraPubblicazione().isAfter(startDateTime) && x.getDataOraPubblicazione().isBefore(endDateTime))
                n++;
        }

        return n;
    }

    /**
     * converte un JSONArray in un oggetto feed
     *
     * @param postArray json recuperato dalle API
     * @return Feed ritorna un feed
     */
    public static Feed buildFromJsonArray(JSONArray postArray) {
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

    /**
     * converte l'oggetto feed in un JSONArray
     *
     * @param feed oggetto feed
     * @return json JSONArray di ritorno
     */
    public static JSONArray fromFeedToJsonArray(Feed feed) {
        JSONArray j = new JSONArray();

        for (int i = 0; i < feed.getTotalPost(); i++) {
            j.put(feed.getSinglePost(i).exportToJSONObject());
        }

        return j;
    }
}
