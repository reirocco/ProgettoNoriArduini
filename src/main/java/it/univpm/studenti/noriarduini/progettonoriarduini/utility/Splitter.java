package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

/**
 * Splitter class
 *
 * <p>
 * <p>
 * la classe fornisce metodi statici per il parsing delle stringhe
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class Splitter {
    /**
     * splitta la stringa in base ai regex specificati
     *
     * @param input stringa da splittare
     * @return array di stringhe
     */
    public static String[] split(String input) {
        return input.split("[\\s@&.,!?$+-]+");
    }
}
