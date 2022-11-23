package org.mti.tfidf.indexation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetroIndex {
    Map<String, List<Document>> dictionary = new HashMap<>();

    public void addDocument(Document document) {
        for (var wordFrequency : document.getWordFrequencyList()) {
            if (dictionary.containsKey(wordFrequency.word)) {
                dictionary.get(wordFrequency.word).add(document);
            } else {
                var documents = new ArrayList<Document>();
                documents.add(document);
                dictionary.put(wordFrequency.word, documents);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        dictionary.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\n\n\n"));
        return stringBuilder.toString();
    }
}
