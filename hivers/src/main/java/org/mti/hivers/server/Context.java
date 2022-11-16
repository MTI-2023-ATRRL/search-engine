package org.mti.hivers.server;

public class Context {

    public int statusCode;
    public String body = "";

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
