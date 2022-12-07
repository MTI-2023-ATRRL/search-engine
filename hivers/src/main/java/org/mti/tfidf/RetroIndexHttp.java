package org.mti.tfidf;

import org.mti.hivers.Hivers;
import org.mti.hivers.server.RestHivers;

public class RetroIndexHttp {

    public static void main(String[] args) {
        var hivers = new Hivers();
        var retroIndex = new RetroIndex();


        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.GET, "/retroindex/:query", context -> {
                    var pathParam = context.getPathParamValue(":query");
                    if (pathParam.isEmpty()) {
                        context.response(400, "No query path has been specified !");
                        return;
                    }

                    var query = new Query(pathParam.get());
                    var document = query.getDocument();
                    var documents = retroIndex.getMatchingDocument(document);
                    context.response(200, documents.toString());
                }).start();
    }
}
