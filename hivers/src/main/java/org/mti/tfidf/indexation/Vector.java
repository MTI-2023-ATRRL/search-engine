package org.mti.tfidf.indexation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TokenWithCount {
    String word;
    double frequency;
    List<Integer> indexes = new ArrayList<>();

    public TokenWithCount(String word, double frequency, Integer index) {
        this.word = word;
        this.frequency = frequency;
        indexes.add(index);
    }
}

public class Vector {
    public static void main(String[] args) {
        Tokenisation indexation = new Tokenisation();
    }

    private Map<String, TokenWithCount> Count(Tokenisation indexation) {
        var tokensFrequency = new HashMap<String, TokenWithCount>();

        var tokens = indexation.documentToTokens();
        var tokensLength = tokens.size();
        for (int i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            var mapItem = tokensFrequency.get(token);
            if (mapItem == null) {
                tokensFrequency.put(token, new TokenWithCount(token, 1.0 / tokensLength, i));
            }
        }

        return tokensFrequency;
    }
}
