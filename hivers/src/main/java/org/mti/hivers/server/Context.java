package org.mti.hivers.server;

import spark.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context {
    private final Map<String, String> headers;
    private final Map<String, String> pathParams;
    private final Map<String, String> queryParams;

    public String getRequestBody() {
        return requestBody;
    }

    private final String requestBody;

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private int statusCode;
    private String responseBody = "";

    public Context(Request request) {
        this.requestBody = request.body();
        this.pathParams = request.params();

        this.queryParams = new HashMap<>();
        var queryParams = request.queryParams();
        queryParams.forEach(queryParam -> {
            var queryParamValue = request.queryParams(queryParam);
            this.queryParams.put(queryParam, queryParamValue);
        });

        this.headers = new HashMap<>();
        var headers = request.headers();
        headers.forEach(header -> {
            var headerValue = request.headers(header);
            this.headers.put(header, headerValue);
        });
    }

    public Optional<String> getHeaderValue(String header) {
        var result = this.headers.get(header);
        return Optional.ofNullable(result);
    }

    public Optional<String> getPathParamValue(String param) {
        var result = this.pathParams.get(param);
        return Optional.ofNullable(result);
    }

    public Optional<String> getQueryParamValue(String param) {
        var result = this.queryParams.get(param);
        return Optional.ofNullable(result);
    }

    public void response(int statusCode) {
        this.statusCode = statusCode;
    }

    public void response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.responseBody = body;
    }
}
