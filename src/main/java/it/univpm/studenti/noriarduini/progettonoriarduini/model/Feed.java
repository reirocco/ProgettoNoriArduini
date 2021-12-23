package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import org.apache.tomcat.jni.Local;

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
}
