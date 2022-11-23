package org.mti.tfidf;

import java.util.ArrayList;
import java.util.List;

public class Indexer {
    private final List<Document> documents;

    public Indexer() {
        this.documents = new ArrayList<>();
    }

    public void addDocument(String resource, TransportProtocol protocol) {
        try {
            var transportLayer = new TransportLayer(resource, protocol);
            var body = transportLayer.getBody();

            var tokenisation = new Tokenisation();
            var tokens = tokenisation.textToTokens(body);

            var vector = new Vector();
            var vectorCount = vector.count(tokens);
            documents.add(new Document(resource, vectorCount));
        } catch (Exception e) {
            System.err.println("transport error");
        }
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
