package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import org.json.JSONObject;

/**
 * Modello per la realizzazione dei filtri.
 *
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter
 */
public interface Filter {
    /**
     * Controlla se il filtro è presente ed è scritto correttamente. Per informazioni sulla corretta scrittura di un certo filtro,
     * leggere la documentazione della classe di tale filtro.
     *
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @return <code>true</code> se il filtro è composto correttamente, <code>false</code> in caso contrario.
     * @throws WrongFilterException se nella scrittura del filtro vi sono errori di sintassi o parti mancanti.
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter
     * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter
     */
    boolean check(JSONObject requestBody) throws WrongFilterException;

    /**
     * Filtra il feed, creandone uno nuovo che contiene solamente i post che soddisfano le condizioni del filtro.
     *
     * @param requestBody JSON dei filtri, che è nel body della richiesta.
     * @param feed Feed in input
     * @return <code>Feed</code> con i post che soddisfano le condizioni del filtro.
     * @throws WrongFilterException se nella scrittura del filtro vi sono errori di sintassi o parti mancanti.
     */
    Feed filter(JSONObject requestBody, Feed feed) throws WrongFilterException;
}