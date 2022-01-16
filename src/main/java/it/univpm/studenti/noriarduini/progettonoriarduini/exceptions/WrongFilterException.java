package it.univpm.studenti.noriarduini.progettonoriarduini.exceptions;

import org.json.JSONObject;

/**
 * Eccezione che viene sollevata quando il filtro per il feed viene composto male (ad es.: errore di sintassi o elementi mancanti).
 *
 * @author Rocco Nori, Federico Arduini
 * @version 1.0
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.DateFilter
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.TimeFilter
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.filters.KeyWordsFilter
 */
public class WrongFilterException extends ProjectException {
    private String message;

    /**
     * Crea un oggetto <code>WrongFilterException</code> con un messaggio di errore preimpostato.
     * @param msg Messaggio di errore (motivo per cui viene sollevata l'eccezione)
     */
    public WrongFilterException(String msg) {
        super();
        this.message = msg;
    }

    /**
     * Ottiene il messaggio di errore dell'eccezione.
     * @return Messaggio di errore
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Ottiene il messaggio di errore dell'eccezione in <code>JSONObject</code>.
     * @return <code>JSONObject</code> contenente il messaggio di errore
     */
    @Override
    public JSONObject getMessageAsJson() {
        JSONObject obj = new JSONObject();
        obj.put("filter_error", this.message);
        return obj;
    }
}
