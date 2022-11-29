package org.mti.tfidf.transport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TransportLayerHttp implements TransportLayer {
    private final URL url;

    public TransportLayerHttp(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    public String getText() throws IOException {
        return this.urlToRawText(url);
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
