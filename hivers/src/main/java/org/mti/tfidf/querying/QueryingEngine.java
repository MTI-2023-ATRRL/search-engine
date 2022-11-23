package org.mti.tfidf.querying;

import org.mti.tfidf.Tokenisation;
import org.mti.tfidf.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryingEngine {

    private class DocumentWithSimilarity implements Comparable
    {
        public final Document doc;
        public final double similarity;

        public DocumentWithSimilarity(Document doc, double similarity)
        {
            this.doc = doc;
            this.similarity = similarity;
        }

        @Override
        public int compareTo(Object o) {
            var other = (DocumentWithSimilarity) o;
            return Double.compare(this.similarity, other.similarity);
        }
    }
    private Map<String, List<Document>> retroIndex;

    private int corpusSize;

    public QueryingEngine()
    {
        retroIndex = new HashMap<>();
        corpusSize = 0;
    }

    public void setRetroIndex(Map<String, List<Document>> retroIndex)
    {
        this.retroIndex = retroIndex;
        AtomicInteger size = new AtomicInteger();
        this.retroIndex.forEach((e, f) -> {
            size.addAndGet(f.size());
        });

        this.corpusSize = size.get();
    }

    public List<Term> QueryCleaning(String query)
    {
        Tokenisation serviceToken = new Tokenisation();
        Vector serviceVector = new Vector();
        var tokens = serviceToken.textToTokens(query);
        var wordFrequency = serviceVector.count(tokens);

        return wordFrequency.stream().map(
                wf -> new Term(wf.getWord(), wf.getFrequency(), wf.getIndexes())
        ).toList();
    }

    public Map<String, List<Document>> Extraction(List<String> tokens)
    {
        var result = new HashMap<String, List<Document>>();

        tokens.forEach(e -> {
            if (retroIndex.containsKey(e))
                result.put(e, retroIndex.get(e));
        });

        return result;
    }

    private double CalculateIDF(int numberOfMatchingDocs)
    {
        return Math.log((double) this.corpusSize / (double) (1 + numberOfMatchingDocs));
    }

    private double ComputeTfIDF(Document doc, String token, Map<String, List<Document>> matchingDocs)
    {
        var matchingDocsForToken = matchingDocs.get(token);
        if (!matchingDocsForToken.contains(doc))
        {
            return 0.0;
        }

        var term = doc.terms.stream().filter(t -> t.value == token).toList().get(0);
        return term.frequency * CalculateIDF(matchingDocsForToken.size());
    }

    private List<Double> getTfIDFVectorFromDoc(Document doc, List<Term> cleanQuery, Map<String,
            List<Document>> matchingDocs)
    {
        List<Double> tfidf = new ArrayList<>();

        for (int i = 0; i < cleanQuery.size(); i++)
        {
            var token = cleanQuery.get(i);
            tfidf.add(ComputeTfIDF(doc, token.value, matchingDocs));
        }

        return tfidf;
    }

    public List<DocumentWithSimilarity> Compute(List<Term> cleanQuery, Map<String, List<Document>> matchingDocs, List<Document> docs)
    {
        var computeResults = new ArrayList<DocumentWithSimilarity>();

        var docQuery = new Document(cleanQuery);
        var tfidfQuery = getTfIDFVectorFromDoc(docQuery, cleanQuery, matchingDocs);

        for (int i = 0; i < docs.size(); i++)
        {
            var doc = docs.get(i);
            var tfidf = getTfIDFVectorFromDoc(doc, cleanQuery, matchingDocs);
            //var norm = NormalizeL2(tfidf);

            var similarity = ComputeSimilarity(tfidfQuery, tfidf);
            computeResults.add(new DocumentWithSimilarity(doc, similarity));
        }

        computeResults.sort(Comparator.reverseOrder());
        return computeResults;
    }

    private Double NormalizeL2(List<Double> vector)
    {
        return Math.sqrt(vector.stream().reduce(0.0, (subtotal, element) -> subtotal + element * element));
    }

    private double ComputeSimilarity(List<Double> vectorQuery, List<Double> vectorDoc)
    {

        if (vectorQuery.size() != vectorQuery.size())
            throw new ArithmeticException("Product of vectors of different size is not possible");

        var dotProduct = 0.0;
        var euclidianDist = 0.0;

        for (int i = 0; i < vectorQuery.size(); i++)
        {
            dotProduct += vectorQuery.get(i) * vectorDoc.get(i);
            var term = (vectorQuery.get(i) - vectorDoc.get(i));
            euclidianDist += term * term;
        }

        euclidianDist = Math.sqrt(euclidianDist);

        return dotProduct / euclidianDist;

    }
}
