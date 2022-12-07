package org.mti.hivers.server;

import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static spark.Spark.*;

public class RestHivers implements Extension {

    public enum Method implements Extension.Method {
        GET,
        POST,
        PUT,
        DELETE,
    }

    private class Tuple {
        String route;
        Consumer<Context> callback;

        public Tuple(String route, Consumer<Context> callback) {
            this.route = route;
            this.callback = callback;
        }
    }

    private final Map<Method, List<Tuple>> routes = new HashMap<>();
    private int port = 6437;

    public Map<Method, List<Tuple>> getRoutes() {
        return this.routes;
    }

    /*@Override
    public RestHivers register(Extension.Method verb, String path, Function<Context, Context> callback) {

        var tuple = new Tuple(path, callback);
        var methodList = routes.get(verb);
        if (methodList == null) {
            methodList = new ArrayList<>();
        }

        methodList.add(tuple);

        routes.put((Method) verb, methodList);

        return this;
    }*/

    @Override
    public RestHivers register(Extension.Method verb, String path, Consumer<Context> callback) {
        var tuple = new Tuple(path, callback);
        var methodList = routes.get(verb);
        if (methodList == null) {
            methodList = new ArrayList<>();
        }

        methodList.add(tuple);

        routes.put((Method) verb, methodList);

        return this;
    }

    public RestHivers port(int port) {
        this.port = port;
        return this;
    }

    private void restCallback(Request req, Response res, Tuple tuple) {
        var context = new Context(req);
        tuple.callback.accept(context);

        res.status(context.getStatusCode());
        res.body(context.getResponseBody());
    }

    public void start() {
        Spark.port(this.port);

        routes.forEach((method, tuples) -> {
            switch (method) {
                case GET -> tuples.forEach(tuple -> get(tuple.route, (req, res) -> {
                    restCallback(req, res, tuple);
                    return res.body();
                }));
                case PUT -> tuples.forEach(tuple -> put(tuple.route, (req, res) -> {
                    restCallback(req, res, tuple);
                    return res.body();
                }));
                case POST -> tuples.forEach(tuple -> post(tuple.route, (req, res) -> {
                    restCallback(req, res, tuple);
                    return res.body();
                }));
                case DELETE -> tuples.forEach(tuple -> delete(tuple.route, (req, res) -> {
                    restCallback(req, res, tuple);
                    return res.body();
                }));
            }
        });
    }

    public void shutdown() {
        stop();
    }

}