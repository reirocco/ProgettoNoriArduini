package it.univpm.studenti.noriarduini.progettonoriarduini.exceptions;

import org.json.JSONObject;

/**
 * Classe astratta che funge da modello per le eccezioni personalizzate del progetto.
 *
 * @author Rocco Nori, Federico Arduini
 * @version 1.0
 * @see it.univpm.studenti.noriarduini.progettonoriarduini.exceptions.WrongFilterException
 */
public abstract class ProjectException extends Exception {
    private String message;

    /**
     * Ottiene il messaggio di errore, ovvero il motivo per cui è stata sollevata l'eccezione.
     * @return messaggio di errore.
     */
    public abstract String getMessage();

    /**
     * Ottiene il messaggio di errore in <code>JSONObject</code>.
     * @return <code>JSONObject</code> del messaggio di errore.
     */
    public abstract JSONObject getMessageAsJson();
}
