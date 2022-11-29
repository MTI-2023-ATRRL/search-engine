package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyTest {
    @Test
    void shouldBeAbleToCreateWordFrequency() {
        var wf = new WordFrequency("Test", 1, new ArrayList<>());
        assertNotNull(wf);
        assertEquals(wf.word, "Test");
        assertEquals(wf.frequency, 1);
        assertEquals(wf.indexes.size(), 0);
        assertEquals(wf.toString(), "Test : 1.0 []");
    }
}