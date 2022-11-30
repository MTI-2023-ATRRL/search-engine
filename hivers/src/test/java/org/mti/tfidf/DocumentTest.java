package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {
    @Test
    public void shouldBeAbleToCreateDocument() {
        var doc = new Document("test", new ArrayList<>());
        assertNotNull(doc);
    }

    @Test
    public void shouldBeAbleToGetListWordFrequency() {
        var doc = new Document("test", new ArrayList<>());
        assertTrue(doc.getWordFrequencyList().isEmpty());
    }

    @Test
    public void shouldBeAbleToSearchInTheMap() {
        List<WordFrequency> wfs = new ArrayList<>();
        wfs.add(new WordFrequency("test", 0.5, new ArrayList<>()));

        var doc = new Document("test", wfs);
        assertTrue(doc.wordFrequencyMap.containsKey("test"));
    }

}