package org.mti.tfidf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TfIdfCalculator {
    public class DocumnentWithScore {
        public Document document;
        public double score;

        public DocumnentWithScore(Document document, double score) {
            this.document = document;
            this.score = score;
        }
    }

    public double calculateTf(String token, Document document) {
        if (!document.wordFrequencyMap.containsKey(token)) {
            return 0.0;
        }

        return document.wordFrequencyMap.get(token).frequency;
    }

    public double calculateIdf(Document document, RetroIndex retroIndex) {
        var corpusSize = (double) retroIndex.corpusSize;
        var matchedDocument = retroIndex.getMatchingDocument(document);
        double idf = Math.log(corpusSize / (1.0 + matchedDocument.size()));

        return idf;
    }

    public List<Double> calculateTfIdf(List<Double> tfs, double idf) {
        return tfs.stream().map(tf -> tf * idf).toList();
    }

    public List<Document> createTfIdfVectorForQuery(Document queryDocument, RetroIndex retroIndex) {
        var idf = calculateIdf(queryDocument, retroIndex);

        List<Double> tfOfTheQueryDocument = new ArrayList<>();
        for (var term: queryDocument.getWordFrequencyList()) {
            tfOfTheQueryDocument.add(calculateTf(term.word, queryDocument));
        }
        List<Double> tfIdfOfTheQueryDocument =  calculateTfIdf(tfOfTheQueryDocument, idf);

        var bestDocsWithComputation = new ArrayList<DocumnentWithScore>();
        for (var matchedDoc: retroIndex.getMatchingDocument(queryDocument)) {
            List<Double> tfs = new ArrayList<>();
            for (var term: queryDocument.getWordFrequencyList()) {
                tfs.add(calculateTf(term.word, matchedDoc));
            }
            List<Double> vectorTfIdf = calculateTfIdf(tfs, idf);
            double computedCosSim = ComputeSimilarity(vectorTfIdf, tfIdfOfTheQueryDocument);
            bestDocsWithComputation.add(new DocumnentWithScore(matchedDoc, computedCosSim));
        }

        Collections.sort(bestDocsWithComputation, (d1, d2) -> {
            if (d1.score < d2.score) {
                return 0;
            }
            return 1;
        });

        return bestDocsWithComputation.stream().map((d) -> d.document).toList();
    }

    private double ComputeSimilarity(List<Double> vectorQuery, List<Double> vectorDoc)
    {

        if (vectorQuery.size() != vectorQuery.size())
            throw new ArithmeticException("Product of vectors of different size is not possible");

        var dotProduct = 0.0;
        var euclideanDist = 0.0;

        for (int i = 0; i < vectorQuery.size(); i++)
        {
            dotProduct += vectorQuery.get(i) * vectorDoc.get(i);
            var term = (vectorQuery.get(i) - vectorDoc.get(i));
            euclideanDist += term * term;
        }

        euclideanDist = Math.sqrt(euclideanDist);

        if (euclideanDist == 0) {
            return 0;
        }

        return dotProduct / euclideanDist;

    }

    private double normalizeL2(List<Double> vector) {
        return Math.sqrt(vector.stream().reduce(0.0, (subtotal, element) -> subtotal + element * element));
    }
}
