package org.mti.tfidf;

import java.util.*;

public class RetroIndex {
    Map<String, List<Document>> dictionary = new HashMap<>();
    public int corpusSize = 0;

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
        corpusSize += 1;
    }

    public List<Document> getMatchingDocument(Document document) {
        Set<Document> matchingDocuments = new HashSet<>();
        for (WordFrequency wordFrequency: document.getWordFrequencyList()) {
            var word = wordFrequency.word;
            if (dictionary.containsKey(word)) {
                var documentMatchedList = dictionary.get(word);
                for (var documentMatched: documentMatchedList) {
                    matchingDocuments.add(documentMatched);
                }
            }
        }

        List<Document> documents = new ArrayList<>(matchingDocuments);
        return documents;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        dictionary.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\n\n\n"));
        return stringBuilder.toString();
    }
}
