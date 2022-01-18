package it.univpm.studenti.noriarduini.progettonoriarduini.model;

/**
 * Dictionary Word class
 *
 * <p>
 * <p>
 * definisce una parola all'interno di un dizionario.
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class DictionaryWord {
    /**
     * parola
     */
    private String word;
    /**
     * numero di ricorrenze in una stringa
     */
    private int occurrences;

    /**
     * costruttore della classe
     *
     * @param word
     */
    public DictionaryWord(String word) {
        this.word = word;
        this.occurrences = 1;
    }

    /**
     * getter della parola
     *
     * @return parola
     */
    public String getWord() {
        return word;
    }

    /**
     * setter della parola
     *
     * @param word
     */
    private void setWord(String word) {
        this.word = word;
    }

    /**
     * getter ricorrenze
     *
     * @return int occorrenze
     */
    public int getOccurrences() {
        return occurrences;
    }

    /**
     * setter occorrenze
     *
     * @param occurrences
     */
    private void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    /**
     * incrementa le ricorrenze di uno
     */
    public void addOccurrence() {
        this.setOccurrences(this.getOccurrences() + 1);
    }
}
