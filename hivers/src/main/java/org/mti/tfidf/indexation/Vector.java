package org.mti.tfidf.indexation;

interface TokenWithCount {
    String word;
}

public class Vector {
    public static void main(String[] args) {
        Indexation indexation = new Indexation();
        var tokens = indexation.documentToTokens();
    }

    private
}
