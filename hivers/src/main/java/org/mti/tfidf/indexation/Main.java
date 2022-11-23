package org.mti.tfidf.indexation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String urlPath = "https://example.com";
        String filePath = "C:\\Users\\casne\\Desktop\\Example Domain.html";

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
