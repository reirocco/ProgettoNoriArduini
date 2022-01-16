package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostDictionary extends Post {
    private ArrayList<DictionaryWord> dictionary;

    public PostDictionary(String message, String id, LocalDateTime dataOraPubblicazione) {
        super(message, id, dataOraPubblicazione);
        this.dictionary = new ArrayList<>();
    }

    // costruttore che converte un post normale in un post con dizionario di parole
    public PostDictionary(Post p) {
        super(p.getMessage(), p.getId(), p.getDataOraPubblicazione());
        this.dictionary = new ArrayList<>();
    }

    public void addWord(DictionaryWord word) {
        this.dictionary.add(word);
    }

    @Override
    public boolean hasKeyWords(ArrayList<String> dataset, boolean full_words, boolean case_sensitive) {
        String[] messageWords = Splitter.split(this.getMessage());
        boolean found = false;
        boolean res;
        // per ogni parola del dataset
        for (String x : dataset) {
            // versione case sensitive, per la versione case insensitive vedere Post.java
            for (String y : messageWords) {
                if (full_words) {
                    if (case_sensitive)
                        res = y.equals(x);
                    else
                        res = y.equalsIgnoreCase(x);
                } else {
                    if (case_sensitive)
                        res = y.contains(x);
                    else
                        res = StringUtils.containsIgnoreCase(y, x);
                }
                if (res) {
                    found = true;
                    // controllo che la parola trovata non sia stata già aggiunta al dizionario
                    // ottengo l'indice della parola da cercare
                    int index = this.checkOccurrenceExists(x);
                    // se la parola da cercare non è stata trovata allora la aggiungo
                    if (index == -1) this.addWord(new DictionaryWord(x));
                        // altrimenti vado a prendere l'oggetto di quell'indice e gli aumento l'occorrenza della parola
                    else this.dictionary.get(index).addOccurrence();
                }
            }
        }

        return found;
    }

    public JSONObject exportToJSONObject() {
        // oggetto che contiene gli oggetti post e dictionary
        JSONObject container = new JSONObject();

        // oggetto post
        JSONObject postObj = new JSONObject();
        postObj.put("id", this.getId());
        postObj.put("message", this.getMessage());
        postObj.put("date", this.getDataOraPubblicazione());

        // oggetto dictionary
        JSONObject dictObj = new JSONObject();
        for (DictionaryWord x : this.dictionary)
            dictObj.put(x.getWord(), x.getOccurrences());

        // metto i due oggetti post e dictionary nell'oggetto finale
        container.put("post", postObj);
        container.put("dictionary", dictObj);

        return container;
    }

    public int checkOccurrenceExists(String word) {
        int index = -1;

        for (int i = 0; i < this.dictionary.size() && i != -1; i++) {
            if (this.dictionary.get(i).getWord().contains(word))
                index = i;
        }

        return index;
    }
}
