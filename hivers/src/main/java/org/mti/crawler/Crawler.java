package org.mti.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class Crawler {
    private final Queue<String> queue;
    private final Set<String> visitedUrls;

    public Crawler() {
        this.queue = new LinkedList<>();
        this.visitedUrls = new HashSet<>();
    }

    public void getAllLinksInURL(String baseUrl) throws IOException, URISyntaxException, InterruptedException {
        queue.add(baseUrl);
        var urlToCheck = queue.poll();

        while (urlToCheck != null) {
            System.out.println("Checking URL: " + urlToCheck);
            URL url = new URL(urlToCheck);
            this.getLinksInDocument(url);

            this.supplyURL(urlToCheck);

            visitedUrls.add(url.toString());
            urlToCheck = queue.poll();
        }
    }

    private void getLinksInDocument(URL url) {
        Document doc = null;
        try {
            doc = Jsoup.parse(url, 10000);
        } catch (IOException e) {
            System.out.println("ahah la page existe pas ^^");
            return;
        }

        var linksAsElements = doc.select("a");
        linksAsElements.stream()
                .map(element -> element.attr("href"))
                .forEach(l -> {
                    if (!visitedUrls.contains(l) && l.startsWith("http")) queue.add(l);
                });
    }

    private void supplyURL(String url) throws IOException, URISyntaxException, InterruptedException {
        Random ran = new Random();
        int messageId = ran.nextInt(35);

        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3333/supply"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"topicName\": \"indexer\"," +
                                " \"messageId\": " + messageId +
                                ", \"messageContent\": \"" + url + "\"}"))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void addTopicsToApplication(String topicName) throws IOException, URISyntaxException, InterruptedException {
        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3333/topic"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"topicName\": \"" + topicName + "\", \"numberOfPartition\": 15}"))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        var crawler = new Crawler();
        crawler.addTopicsToApplication("indexer");

        crawler.getAllLinksInURL("https://fr.wikipedia.org/wiki/Coupe_du_monde_de_football_2022");
    }
}