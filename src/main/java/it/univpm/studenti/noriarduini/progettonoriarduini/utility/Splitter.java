package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

public class Splitter {
    public static String[] split(String input) {
        return input.split("[\\s@&.,!?$+-]+");
    }
}
