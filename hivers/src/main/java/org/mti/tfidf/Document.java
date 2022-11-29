package org.mti.tfidf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Document {
    private final String resource;
    private final List<WordFrequency> wordFrequencyList;
    public Map<String, WordFrequency> wordFrequencyMap;

    public Document(String resource, List<WordFrequency> wordFrequencyList) {
        this.resource = resource;
        this.wordFrequencyList = wordFrequencyList;
        this.wordFrequencyMap = new HashMap<>();
        for (var wf: wordFrequencyList) {
            wordFrequencyMap.put(wf.word, wf);
        }
    }

    public List<WordFrequency> getWordFrequencyList() {
        return wordFrequencyList;
    }

    @Override
    public String toString() {
        return resource + ": " + wordFrequencyList;
    }
}
