package org.mti.hivers.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Context {
    private final Map<String, String> headers;
    public int statusCode;
    public String body = "";

    public Context(Set<String> headers) {
        this.headers = new HashMap<>();
        headers.stream().forEach(h -> {
            var separatedHeader = h.split(":");
            this.headers.put(separatedHeader[0], separatedHeader[1]);
        });
    }

    public Optional<String> getHeaderValue(String header) {
        var result = this.headers.get(header);
        return Optional.ofNullable(result);
    }

    public Context response(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Context response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        return this;
    }
}
