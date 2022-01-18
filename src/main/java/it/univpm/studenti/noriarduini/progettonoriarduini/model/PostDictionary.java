package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <b>PostDictionary class</b>
 * <p>
 * La classe provvede alla definizione della struttura base di un post e del dizionario delle parole
 * (<a href="https://developers.facebook.com/docs/graph-api/reference/post/?locale=it_IT">vedi Facebook API Graph Post</a>.
 *
 * <p>
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class PostDictionary extends Post {
    /**
     * array delle parole contenute nel post
     */
    private ArrayList<DictionaryWord> dictionary;

    /**
     * Costruttore della classe
     *
     * @param message              contenuto del post
     * @param id                   identificativo del post
     * @param dataOraPubblicazione data e ora di pubblicazione del post
     */
    public PostDictionary(String message, String id, LocalDateTime dataOraPubblicazione) {
        super(message, id, dataOraPubblicazione);
        this.dictionary = new ArrayList<>();
    }

    /**
     * costruttore che converte un post normale in un post con dizionario di parole
     *
     * @param p oggetto Post
     */
    public PostDictionary(Post p) {
        super(p.getMessage(), p.getId(), p.getDataOraPubblicazione());
        this.dictionary = new ArrayList<>();
    }

    /**
     * aggiunge una parola all'array delle parole
     *
     * @param word parola da inserire
     */
    public void addWord(DictionaryWord word) {
        this.dictionary.add(word);
    }

    /**
     * Controlla la ricorrenza di una determinata parola all'interno del contenuto del post e restituisce un booleano:
     * <ul>
     *     <li><b>true</b> se c'è almeno una ricorrenza;</li>
     *     <li><b>false</b> se non viene trovata</li>
     * </ul>
     * <p>
     * Esempi:<p>
     *          1)<p>
     *          messaggio --> "Oggi il tempo è bellissimo"<p>
     *          liparola da filtrare --> "belli"<p>
     *          con full_words = true<p>
     *          nessuna parola trovata<p>
     *          con full_words = false<p>
     *          parola trovata : bellissimo<p>
     *
     *          <p>
     *          2)<p>
     *          messaggio --> "Oggi il tempo è bellissimo"<p>
     *          parola da filtrare --> "Tempo"<p>
     *          con case_sensitive = true<p>
     *          nessuna parola trovata<p>
     *          parola trovata : tempo<p>
     * </p>
     *
     * @param dataset        dataset delle parole.
     *                       <p>
     * @param full_words     filtro per le parole intere
     *                                            <ul>
     *                                               <li><b>true</b> - ricerca se è presente una specifica sequenza di caratteri</li>
     *                                               <li><b>false</b> - ricerca se è presente una specifica sequenza di caratteri è contenuta anche all'interno di altre parole</li>
     *                                            </ul>
     *
     *                       <p>
     *                       <p>
     * @param case_sensitive filtro per la ricerca usando il case sensitive
     *                                                                   <ul>
     *                                                                          <li><b>true</b> - ricerca case sensitive</li>
     *                                                                          <li><b>false</b> - ricerca case insensitive</li>
     *                                                                   </ul>
     *                       <p>
     * @return trovato booleano
     */
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

    /**
     * metodo per creare un json object dall'oggetto PostDictionary
     *
     * @return JSONObject json object con le informazioni sul post
     */
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

    /**
     * metodo per controllare le ricorrenze di una sequenza di caratteri all'interno di una stringa
     *
     * @param word sequenza da ricercare
     * @return numero di ricorrenze
     */
    public int checkOccurrenceExists(String word) {
        int index = -1;

        for (int i = 0; i < this.dictionary.size() && i != -1; i++) {
            if (this.dictionary.get(i).getWord().contains(word))
                index = i;
        }

        return index;
    }
}
