package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizationTest {
    @Test
    public void shouldSplitEmptyTextToTokens() {
        var tokenization = new Tokenization();
        var tokens = tokenization.textToTokens("");
        assertTrue(tokens.isEmpty());
    }

    @Test
    public void shouldSplitTextToTokens() {
        var tokenization = new Tokenization();
        String text = "The blue rabbit is fishing in a blue river.";
        var tokens = tokenization.textToTokens(text);

        var expected = List.of("instructed", "rabbit", "obs", "instructed", "riv");
        assertEquals(expected.size(), tokens.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), tokens.get(i));
        }
    }
}