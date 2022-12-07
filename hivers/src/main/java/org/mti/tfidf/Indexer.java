package org.mti.tfidf;

import org.mti.tfidf.transport.TransportLayer;

import java.util.ArrayList;
import java.util.List;

public class Indexer {
    private final List<Document> documents;

    public Indexer() {
        this.documents = new ArrayList<>();
    }

    public Document addDocument(TransportLayer transportLayer) {
        try {
            var body = transportLayer.getText();

            var tokenization = new Tokenization();
            var tokens = tokenization.textToTokens(body);

            var vector = new Vector();
            var vectorCount = vector.count(tokens);
            var doc = new Document(body, vectorCount);
            documents.add(doc);

            return doc;

        } catch (Exception e) {
            System.err.println("transport error");
            return null;
        }
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
