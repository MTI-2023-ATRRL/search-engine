package org.mti.tfidf;

import java.util.List;

public class WordFrequency {
    public String getWord() {
        return word;
    }

    public double getFrequency() {
        return frequency;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

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