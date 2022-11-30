package org.mti.tfidf;

import org.junit.jupiter.api.Test;
import org.mti.tfidf.transport.TransportLayerText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TfIdfCalculatorTest {

    @Test
    public void calculateTf_shouldReturnZeroIfTokenAbsent() {
        var calculator = new TfIdfCalculator();

        List<WordFrequency> wfs = new ArrayList<>();
        wfs.add(new WordFrequency("test", 0.5, new ArrayList<>()));

        var doc = new Document("test", wfs);
        assertTrue(calculator.calculateTf("azerty", doc) == 0.0);
    }

    @Test
    public void calculateTf_shouldReturnTFIfTokenPresent() {
        var calculator = new TfIdfCalculator();

        List<WordFrequency> wfs = new ArrayList<>();
        wfs.add(new WordFrequency("test", 0.5, new ArrayList<>()));
        wfs.add(new WordFrequency("azerty", 0.25, new ArrayList<>()));

        var doc = new Document("test", wfs);
        assertTrue(calculator.calculateTf("azerty", doc) == 0.25);
    }

    @Test
    public void calculateIdf_shouldCalculateIdf() {
        var calculator = new TfIdfCalculator();
        var retroIndex = new RetroIndex();

        List<WordFrequency> wfs = new ArrayList<>();
        wfs.add(new WordFrequency("test", 0.5, new ArrayList<>()));
        wfs.add(new WordFrequency("azerty", 0.25, new ArrayList<>()));
        var doc1 = new Document("doc1", wfs);

        List<WordFrequency> wfs2 = new ArrayList<>();
        wfs2.add(new WordFrequency("qwerty", 0.68, new ArrayList<>()));
        wfs2.add(new WordFrequency("prod", 0.15, new ArrayList<>()));
        wfs2.add(new WordFrequency("test", 0.13, new ArrayList<>()));
        var doc2 = new Document("doc2", wfs2);

        retroIndex.addDocument(doc1);
        retroIndex.addDocument(doc2);

        List<WordFrequency> wfs3 = new ArrayList<>();
        wfs3.add(new WordFrequency("prod", 0.82, new ArrayList<>()));
        var query1 = new Document("query", wfs3);

        List<WordFrequency> wfs4 = new ArrayList<>();
        wfs4.add(new WordFrequency("test", 0.36, new ArrayList<>()));
        var query2 = new Document("query2", wfs4);

        var result = calculator.calculateIdf(query1, retroIndex);
        assertEquals(0.0, result);

        result = calculator.calculateIdf(query2, retroIndex);
        assertEquals(Math.log(2.0/3.0), result);
    }

    @Test
    public void calculateTfIdf() {
        var calculator = new TfIdfCalculator();
        List<Double> tfs = Arrays.asList(2.0/38.0, 14.0/4.0, 46.0/32.0);
        var idf = Math.log(2.0/3.0);

        var tfidf = calculator.calculateTfIdf(tfs, idf);
        assertEquals(tfs.size(), tfidf.size());

        for (int i = 0; i < tfs.size(); i++) {
            var tf = tfs.get(i);
            assertEquals(tf * idf, tfidf.get(i));
        }
    }

    @Test
    public void createTfIdfVectorForQuery() {
        var indexer = new Indexer();

        var resource1 = "Bonsoir et bienvenue dans le super jeu trop cool";
        var resource2 = "Hello! Here I am fishing";
        var resource3 = "Good-bye! Here I am not fishing";

        indexer.addDocument(new TransportLayerText(resource1));
        indexer.addDocument(new TransportLayerText(resource2));
        indexer.addDocument(new TransportLayerText(resource3));

        var retroIndex = new RetroIndex();
        for (var document : indexer.getDocuments()) {
            retroIndex.addDocument(document);
        }

        Query simpleQuery = new Query("Hello! Here I am fishing");
        Document docQuery = simpleQuery.getDocument();
        TfIdfCalculator tfIdfCalculator = new TfIdfCalculator();
        var docs = tfIdfCalculator.createTfIdfVectorForQuery(docQuery, retroIndex);

        assertEquals(2, docs.size());
        assertEquals(resource2, docs.get(0).getResource());
        assertEquals(resource3, docs.get(1).getResource());
    }
}