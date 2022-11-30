package org.mti.tfidf;

import org.junit.jupiter.api.Test;
import org.mti.tfidf.transport.TransportLayerText;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IndexerTest {

    @Test
    public void shouldBeAbleToCreateIndexer() {
        var indexer = new Indexer();
        assertNotNull(indexer);
    }

    @Test
    public void shouldBeAbleToAddDocument() {
        var indexer = new Indexer();
        indexer.addDocument(new TransportLayerText("Test"));
        assertEquals(indexer.getDocuments().size(), 1);
    }

}