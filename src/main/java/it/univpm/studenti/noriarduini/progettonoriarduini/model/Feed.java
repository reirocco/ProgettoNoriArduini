package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import java.util.ArrayList;

public class Feed {
    private final ArrayList<Post> listaPost;

    public Feed() {
        this.listaPost = new ArrayList<>();
    }

    public void addPost(Post p) {
        for (int i = 0; i < this.listaPost.toArray().length; i++) {
            if (this.listaPost.get(i) == null) {
                this.listaPost.add(p);
                return;
            }
        }
    }

    public Post getPost(int index) {
        return this.listaPost.get(index);
    }
}
