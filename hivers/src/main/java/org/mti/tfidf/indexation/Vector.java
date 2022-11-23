package org.mti.tfidf.indexation;

import java.util.*;

public class Vector {
    public List<WordFrequency> count(List<String> tokens) {
        Map<String, List<Integer>> tokensIndexes = this.getTokensIndexes(tokens);

        var tokensLength = tokens.size();
        var tokensCount = new ArrayList<WordFrequency>();

        tokensIndexes.forEach((key, value) -> tokensCount.add(new WordFrequency(key, (double) value.size() / tokensLength, value)));
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

