package org.mti.tfidf;

import java.util.List;

public class Document {
    private final String resource;
    private final List<WordFrequency> wordFrequencyList;

    public Document(String resource, List<WordFrequency> wordFrequencyList) {
        this.resource = resource;
        this.wordFrequencyList = wordFrequencyList;
    }

    public List<WordFrequency> getWordFrequencyList() {
        return wordFrequencyList;
    }

    @Override
    public String toString() {
        return resource + ": " + wordFrequencyList;
    }
}
