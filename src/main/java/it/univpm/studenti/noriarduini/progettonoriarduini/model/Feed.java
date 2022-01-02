package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Feed {
    private final ArrayList<Post> listaPost;

    public Feed() {
        this.listaPost = new ArrayList<>();
    }

    public void addPost(Post p) {
        this.listaPost.add(p);
    }

    public Post getSinglePost(int index) {
        return this.listaPost.get(index);
    }

    public int getTotalPost() {
        return this.listaPost.size();
    }

    public int getPostNumberByTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int n = 0;

        for (Post x : this.listaPost) {
            if (x.getDataOraPubblicazione().isAfter(startDateTime) && x.getDataOraPubblicazione().isBefore(endDateTime))
                n++;
        }

        return n;
    }

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
}
