package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void shouldBeAbleToCreateQuery() {
        var query = new Query("test");
        assertNotNull(query);
    }

    @Test
    void shouldBeAbleToGetDocumentFromQuery() {
        var query = new Query("test");
        var doc = query.getDocument();
        assertEquals(doc.wordFrequencyMap.size(), 1);
    }
}