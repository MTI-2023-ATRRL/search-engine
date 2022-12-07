package org.mti.tfidf.transport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TransportLayerFile implements TransportLayer {
    private final Path path;

    public TransportLayerFile(String path) {
        this.path = Paths.get(path);
    }


    @Override
    public String getText() throws IOException {
        return this.fileToRawText(path);
    }

    @Override
    public List<String> getLinksInDocument() {
        return null;
    }

    private String fileToRawText(Path path) throws IOException {
        var body = Files.readString(path);
        Document doc = Jsoup.parse(body);
        return this.getBodyFromDocument(doc);
    }

    private String getBodyFromDocument(Document doc) {
        doc.outputSettings().prettyPrint(false);
        return doc.body().text();
    }
}
