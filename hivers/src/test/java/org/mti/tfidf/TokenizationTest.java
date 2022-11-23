package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizationTest {
    private final Tokenization tokenization;

    public TokenizationTest() {
        this.tokenization = new Tokenization();
    }

    @Test
    void shouldSplitEmptyTextToTokens() {
        var tokens = tokenization.textToTokens("");
        assertTrue(tokens.isEmpty());
    }

    @Test
    void shouldSplitTextToTokens() {
        String text = "The blue rabbit is fishing in a blue river.";
        var tokens = tokenization.textToTokens(text);

        var expected = List.of("instructed", "rabbit", "obs", "instructed", "riv");
        assertEquals(expected.size(), tokens.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), tokens.get(i));
        }
    }
}