package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {
    @Test
    void shouldBeAbleToCreateDocument() {
        var doc = new Document("test", new ArrayList<>());
        assertNotNull(doc);
    }

    @Test
    void shouldBeAbleToGetListWordFrequency() {
        var doc = new Document("test", new ArrayList<>());
        assertTrue(doc.getWordFrequencyList().isEmpty());
    }

    @Test
    void shouldBeAbleToSearchInTheMap() {
        List<WordFrequency> wfs = new ArrayList<>();
        wfs.add(new WordFrequency("test", 0.5, new ArrayList<>()));

        var doc = new Document("test", wfs);
        assertTrue(doc.wordFrequencyMap.containsKey("test"));
    }

}