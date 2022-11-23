package org.mti.tfidf.indexation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;

public class Indexation {
    public static void main(String[] args) {
        Indexation ind = new Indexation();
        ind.htmlToRawText();
    }

    public String htmlToRawText() {
        try {
            Document doc = Jsoup.parse(new URL("https://example.com"), 10000);
            doc.outputSettings().prettyPrint(false);
            return doc.body().text();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
