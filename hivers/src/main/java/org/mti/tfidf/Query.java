package org.mti.tfidf;

public class Query {
    private final String query;

    Query(String query) {
        this.query = query;
    }

    public Document getDocument() {
        var tokenization = new Tokenization();
        var tokens = tokenization.textToTokens(query);
        var vector = new Vector();
        var vectorCount = vector.count(tokens);
        return new Document(this.query, vectorCount);
    }
}
