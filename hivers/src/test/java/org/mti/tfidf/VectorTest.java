package org.mti.tfidf;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    @Test
    public void shouldGetCountBasedOnTokenList() {
        var vector = new Vector();
        var tokens = List.of("blue", "rabbit", "fish", "blue", "river");

        var tokensCount = vector.count(tokens);
        var expected = List.of(
                new WordFrequency("blue", 0.4, List.of(0, 3)),
                new WordFrequency("rabbit", 0.2, List.of(1)),
                new WordFrequency("fish", 0.2, List.of(2)),
                new WordFrequency("river", 0.2, List.of(4))
        );
        assertEquals(expected.size(), tokensCount.size());
        for (int i = 0; i < expected.size(); i++) {
            var expectedToken = expected.get(i);
            var actualToken = tokensCount.get(i);
            assertEquals(expectedToken.word, actualToken.word);
            assertEquals(expectedToken.frequency, actualToken.frequency);
            assertEquals(expectedToken.indexes.size(), actualToken.indexes.size());

            for (int j = 0; j < expectedToken.indexes.size(); j++) {
                assertEquals(expectedToken.indexes.get(j), actualToken.indexes.get(j));
            }
        }
    }
}
