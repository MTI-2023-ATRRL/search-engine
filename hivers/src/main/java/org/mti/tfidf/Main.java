package org.mti.tfidf;

import org.mti.tfidf.transport.TransportLayerText;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String urlPath = "https://example.com";
        String filePath = "hivers/src/test/resources/example.html";

        var indexer = new Indexer();
//        indexer.addDocument(new TransportLayerHttp(urlPath));
//        indexer.addDocument(new TransportLayerFile(filePath));
        indexer.addDocument(new TransportLayerText("Hello ! Here I am fishing"));
        indexer.addDocument(new TransportLayerText("Foraging is so nice I love to fish fish fish"));
        indexer.addDocument(new TransportLayerText("Rainbow is love and I like it"));
        indexer.addDocument(new TransportLayerText("Fishing Fishing Fishing Fishing"));
        indexer.addDocument(new TransportLayerText("Bonjoir et bienvenue dans le super jeu trop cool"));




        var retroIndex = new RetroIndex();
        for (var document : indexer.getDocuments()) {
            retroIndex.addDocument(document);
        }

        Query simpleQuery = new Query("Hello ! Here I am fishing");
        Document docQuery = simpleQuery.getDocument();
        TfIdfCalculator tfIdfCalculator = new TfIdfCalculator();
        var docs = tfIdfCalculator.createTfIdfVectorForQuery(docQuery, retroIndex);
        System.out.println(docs);
    }
}
