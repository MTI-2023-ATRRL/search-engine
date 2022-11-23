package org.mti.tfidf.indexation;

import java.util.*;

class TokenWithCount {
    String word;
    double frequency;
    List<Integer> indexes;

    public TokenWithCount(String word, double frequency, List<Integer> indexes) {
        this.word = word;
        this.frequency = frequency;
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return word + " : " + frequency + " " + indexes;
    }
}

public class Vector {
    public List<TokenWithCount> count(List<String> tokens) {
        Map<String, List<Integer>> tokensIndexes = this.getTokensIndexes(tokens);

        var tokensLength = tokens.size();
        var tokensCount = new ArrayList<TokenWithCount>();

        tokensIndexes.forEach((key, value) -> tokensCount.add(new TokenWithCount(key, (double) value.size() / tokensLength, value)));
        return tokensCount;
    }

    private Map<String, List<Integer>> getTokensIndexes(List<String> tokens) {
        var tokensIndexes = new HashMap<String, List<Integer>>();
        for (int i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);
            if (tokensIndexes.containsKey(token)) {
                var tokenFrequency = tokensIndexes.get(token);
                tokenFrequency.add(i);
            } else {
                var indexes = new ArrayList<Integer>();
                indexes.add(i);
                tokensIndexes.put(token, indexes);
            }
        }

        return tokensIndexes;
    }
}

