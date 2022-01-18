package it.univpm.studenti.noriarduini.progettonoriarduini.model;

import it.univpm.studenti.noriarduini.progettonoriarduini.utility.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * <b>Post class</b>
 * <p>
 * La classe provvede alla definizione della struttura base di un post
 * (<a href="https://developers.facebook.com/docs/graph-api/reference/post/?locale=it_IT">vedi Facebook API Graph Post</a>.
 *
 * <p>
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class Post {
    /**
     * contenuto del post
     */
    private String message;
    /**
     * identificativo del post
     */
    private String id;
    /**
     * data e ora della pubblicazione del post
     */
    private LocalDateTime dataOraPubblicazione;

    /**
     * Costruttore della classe.
     * <p>
     * Riceve in input:
     *
     * @param message              contenuto del post
     *                             <p>
     * @param id                   identificativo del post (con API Facebook V12 l'id del post è composto da:
     *                             {@code <id_utente_utente>_<id_post>}
     *                             <p>
     * @param dataOraPubblicazione oggetto LocalDateTime
     * @see LocalDateTime
     */
    public Post(String message, String id, LocalDateTime dataOraPubblicazione) {
        this.message = message;
        this.id = id;
        this.dataOraPubblicazione = dataOraPubblicazione;
    }

    /**
     * Secondo costruttore della classe.
     * <p>
     * Riceve in input:
     *
     * @param message              contenuto del post
     *                             <p>
     * @param id                   identificativo del post (con API Facebook V12 l'id del post è composto da:
     *                             {@code <id_utente_utente>_<id_post>}
     *                             <p>
     * @param dataOraPubblicazione Stringa contenente la data da parsare
     * @see LocalDateTime#parse(CharSequence, DateTimeFormatter)
     */
    public Post(String message, String id, String dataOraPubblicazione) {
        this.message = message;
        this.id = id;

        // conversione della data di pubblicazione da String a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        this.dataOraPubblicazione = LocalDateTime.parse(dataOraPubblicazione, formatter);
    }

    /**
     * Ritorna il contenuto del post
     *
     * @return message contenuto del post
     */
    public String getMessage() {
        return message;
    }

    /**
     * Imposta il contenuto del post
     *
     * @param message contenuto del post
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Ritorna l'identificativo del post
     *
     * @return id identificativo del post
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'identificativo del post
     *
     * @param id identificativo del post
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Ritorna l'ora di pubblicazione del post
     *
     * @return getDataOraPubblicazione ora di pubblicazione
     */
    public LocalDateTime getDataOraPubblicazione() {
        return dataOraPubblicazione;
    }

    /**
     * Imposta la data di pubblicazione
     *
     * @param dataOraPubblicazione ora di pubblicazione
     */
    public void setDataOraPubblicazione(LocalDateTime dataOraPubblicazione) {
        this.dataOraPubblicazione = dataOraPubblicazione;
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
     * @return res true se la parola è resente, false altrimenti
     */
    public boolean hasKeyWords(ArrayList<String> dataset, boolean full_words, boolean case_sensitive) {
        boolean found = false;

        Iterator datasetIterator = Arrays.stream(dataset.toArray()).iterator();
        while (!found && datasetIterator.hasNext()) {
            String next = datasetIterator.next().toString();

            boolean res = false;
            // controllo parole intere
            if (full_words) {
                String[] messageWords = Splitter.split(this.message);
                Iterator iter = Arrays.stream(messageWords).iterator();
                while (!res && iter.hasNext()) {
                    if (case_sensitive)
                        res = iter.next().toString().equals(next);
                    else
                        res = iter.next().toString().equalsIgnoreCase(next);
                }
            } else {
                if (case_sensitive)
                    res = this.getMessage().contains(next);
                else {
                    res = StringUtils.containsIgnoreCase(this.message, next);
                }
            }
            if (!found && res) found = true;
        }

        return found;
    }

    /**
     * Esporta l'oggetto sotto formato JSON
     *
     * @return JSONObject dell'oggetto Post
     */
    public JSONObject exportToJSONObject() {
        JSONObject j = new JSONObject();

        j.put("id", this.getId());
        j.put("message", this.getMessage());
        j.put("date", this.getDataOraPubblicazione());

        return j;
    }

    /**
     * confronta due oggetti Post in base all'id.
     *
     * @param obj oggetto generico
     * @return res true se i post sono uguali, altrimenti false
     */
    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj.getClass() == Post.class) {
            if (this.id.equals(((Post) obj).id)) {
                res = true;
            }
        }
        return res;
    }

}
