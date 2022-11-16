package org.mti.hivers.server;

import spark.Response;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static spark.Spark.*;

public class RestHivers {

    public enum Method {
        GET,
        POST,
        PUT,
        DELETE,
    }

    private class Tuple {
        String route;
        Function<Context, Context> callback;

        public Tuple(String route, Function<Context, Context> callback) {
            this.route = route;
            this.callback = callback;
        }
    }

    private Map<Method, List<Tuple>> routes = new HashMap<>();
    private int port = 6437;

    public Map<Method, List<Tuple>> getRoutes() {
        return this.routes;
    }

    public RestHivers register(RestHivers.Method verb, String path, Function<Context, Context> callback) {

        var tuple = new Tuple(path, callback);
        var methodList = routes.get(verb);
        if (methodList == null) {
            methodList = new ArrayList<>();
        }

        methodList.add(tuple);

        routes.put(verb, methodList);

        return this;
    }

    public RestHivers port(int port) {
        this.port = port;
        return this;
    }

    private Response restCallback(Response res, Tuple tuple) {
        var context = tuple.callback.apply(new Context());
        res.status(context.statusCode);
        res.body(context.body);
        return res;
    }

    public void start() {
        Spark.port(this.port);

        routes.forEach((method, tuples) -> {
            switch (method) {
                case GET -> tuples.forEach(tuple -> get(tuple.route, (req, res) -> {
                    res = restCallback(res, tuple);
                    return res;
                }));
                case PUT -> tuples.forEach(tuple -> put(tuple.route, (req, res) -> {
                    res = restCallback(res, tuple);
                    return res;
                }));
                case POST -> tuples.forEach(tuple -> post(tuple.route, (req, res) -> {
                    res = restCallback(res, tuple);
                    return res;
                }));
                case DELETE -> tuples.forEach(tuple -> delete(tuple.route, (req, res) -> {
                    res = restCallback(res, tuple);
                    return res;
                }));
            }
        });
    }

    public void shutdown() {
        stop();
    }

}