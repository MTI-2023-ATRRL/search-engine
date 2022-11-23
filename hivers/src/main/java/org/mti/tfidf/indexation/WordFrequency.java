package org.mti.tfidf.indexation;

import java.util.List;

public class WordFrequency {
    String word;
    double frequency;
    List<Integer> indexes;

    public WordFrequency(String word, double frequency, List<Integer> indexes) {
        this.word = word;
        this.frequency = frequency;
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return word + " : " + frequency + " " + indexes;
    }
}