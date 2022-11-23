package org.mti.tfidf.indexation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Indexation {
    public static void main(String[] args) {
        Indexation ind = new Indexation();
        var text = ind.htmlToRawText("https://example.com");
        var tokens = ind.textToTokens(text);
        System.out.println(tokens.size());
        var cleanedTokens = ind.removeStopWords(tokens);
        System.out.println(cleanedTokens);
    }

    public String htmlToRawText(String url) {
        try {
            Document doc = Jsoup.parse(new URL(url), 10000);
            doc.outputSettings().prettyPrint(false);
            return doc.body().text();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> textToTokens(String text) {
        return List.of(text.toLowerCase().split("\\s+"));
    }

    public List<String> removeStopWords(List<String> tokens) {
        Set<String> stopWords = this.getStopWordsFromFile();

        return tokens.stream().filter((token) -> !stopWords.contains(token)).toList();
    }

    public List<String> 

    private Set<String> getStopWordsFromFile() {
        Set<String> stopWords = new HashSet<>();

        InputStream stream = getClass().getClassLoader().getResourceAsStream("stop_words.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stopWords;
    }
}
