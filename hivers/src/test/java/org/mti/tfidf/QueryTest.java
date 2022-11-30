package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTest {

    @Test
    public void shouldBeAbleToCreateQuery() {
        var query = new Query("test");
        assertNotNull(query);
    }

    @Test
    public void shouldBeAbleToGetDocumentFromQuery() {
        var query = new Query("test");
        var doc = query.getDocument();
        assertEquals(doc.wordFrequencyMap.size(), 1);
    }
}