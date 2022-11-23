package org.mti.tfidf;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TransportLayer {
    private final String resource;
    private final TransportProtocol protocol;

    public TransportLayer(String resource, TransportProtocol protocol) {
        this.resource = resource;
        this.protocol = protocol;
    }

    public String getBody() throws IOException {
        if (this.protocol == TransportProtocol.BY_URL) {
            return this.urlToRawText(new URL(this.resource));
        }
        return this.fileToRawText(Paths.get(this.resource));
    }

    private String fileToRawText(Path path) throws IOException {
        var body = Files.readString(path);
        Document doc = Jsoup.parse(body);
        return this.getBodyFromDocument(doc);
    }

    private String urlToRawText(URL url) throws IOException {
        Document doc = Jsoup.parse(url, 10000);
        return this.getBodyFromDocument(doc);
    }

    private String getBodyFromDocument(Document doc) {
        doc.outputSettings().prettyPrint(false);
        return doc.body().text();
    }
}
