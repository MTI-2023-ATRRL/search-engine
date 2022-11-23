package org.mti.tfidf.indexation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Indexation {
    public static void main(String[] args) {
        Indexation ind = new Indexation();
        var text = ind.htmlToRawText("https://example.com");
        var tokens = ind.textToTokens(text);
        var cleanedTokens = ind.removeStopWords(tokens);
        var tokensReplacedWithSynonyms = ind.replaceSynonyms(cleanedTokens);
        var tokensStemmed = ind.getWordsStem(tokensReplacedWithSynonyms);
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

    public List<String> replaceSynonyms(List<String> tokens) {
        var synonyms = this.getSynonyms();

        return tokens.stream().map((token) -> {
            var synonym = synonyms.get(token);
            if (synonym == null) return token;
            return synonym;
        }).toList();
    }

    public List<String> getWordsStem(List<String> tokens) {
        var stemmings = this.getStemmings();

        return tokens.stream().map((token) -> {
            for (var stem : stemmings) {
                if (token.endsWith(stem)) {
                    return token.substring(0, token.length() - stem.length());
                }
            }
            return token;
        }).toList();
    }

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

    private Map<String, String> getSynonyms() {
        Map<String, String> synonyms = new HashMap<>();

        InputStream stream = getClass().getClassLoader().getResourceAsStream("synonyms.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                var words = line.split(" ");
                var mainSynonym = words[0];

                for (var word : words) {
                    synonyms.put(word, mainSynonym);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return synonyms;
    }

    private Set<String> getStemmings() {
        Set<String> stemmings = new HashSet<>();

        InputStream stream = getClass().getClassLoader().getResourceAsStream("stemmings.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stemmings.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stemmings;
    }
}
