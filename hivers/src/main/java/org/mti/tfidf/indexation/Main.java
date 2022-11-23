package org.mti.tfidf.indexation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String urlPath = "https://example.com";
        String filePath = "C:\\Users\\casne\\Desktop\\Example Domain.html";

        //var transportLayer = new TransportLayer(urlPath, TransportLayer.TRANSPORT_PROTOCOL.BY_URL);
        var transportLayer = new TransportLayer(filePath, TransportLayer.TRANSPORT_PROTOCOL.BY_PATH);
        var body = transportLayer.getBody();

        var tokenisation = new Tokenisation();
        var tokens = tokenisation.textToTokens(body);

        var vector = new Vector();
        var vectorCount = vector.count(tokens);
        System.out.println(vectorCount);
    }
}
