package it.univpm.studenti.noriarduini.progettonoriarduini.model;

public class DictionaryWord {
    private String word;
    private int occurrences;

    public DictionaryWord(String word) {
        this.word = word;
        this.occurrences = 1;
    }

    public String getWord() {
        return word;
    }

    private void setWord(String word) {
        this.word = word;
    }

    public int getOccurrences() {
        return occurrences;
    }

    private void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public void addOccurrence() {
        this.setOccurrences(this.getOccurrences() + 1);
    }
}
