package org.mti.tfidf;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String urlPath = "https://example.com";
        String filePath = "hivers/src/test/resources/example.html";

        var indexer = new Indexer();
        indexer.addDocument(urlPath, TransportProtocol.BY_URL);
        indexer.addDocument(filePath, TransportProtocol.BY_PATH);

        var retroIndex = new RetroIndex();
        for (var document : indexer.getDocuments()) {
            retroIndex.addDocument(document);
        }

        System.out.println(retroIndex);
    }
}
