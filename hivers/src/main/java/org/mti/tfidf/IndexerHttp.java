package org.mti.tfidf;

import org.mti.hivers.Hivers;
import org.mti.hivers.server.RestHivers;
import org.mti.tfidf.transport.TransportLayerHttp;
import org.mti.tfidf.transport.TransportLayerText;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class IndexerHttp {

    private final Indexer indexer;

    public IndexerHttp()
    {
        this.indexer = new Indexer();
    }

    public static void main(String[] args) throws IOException {
        var hivers = new Hivers();

        var indexerHttp = new IndexerHttp();

        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.POST, "/index", context ->
                {
                   // Allo ? Kafka ?
                   // Oui ? Stiti
                   // Quoi ? Feur
                    List<String> str = null;

                    str.forEach(s -> {

                        try {
                            TransportLayerHttp transport = new TransportLayerHttp(s);
                            indexerHttp.indexer.addDocument(transport);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }

                    });
                    var docs = indexerHttp.indexer.getDocuments();

                    // Allo Kafka ? Oui ici Doc
                    // Doc qui ? *tardis intensifies*


                    context.response(200);

                }).start();
    }
}
