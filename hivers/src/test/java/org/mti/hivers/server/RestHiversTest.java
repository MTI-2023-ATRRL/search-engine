package org.mti.hivers.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestHiversTest {

    @Test
    public void shouldRegisterRoute() {
        var rest = new RestHivers();
        assertNotNull(rest);

        rest = rest.register(RestHivers.Method.GET, "/", context ->
                context.response(200, "Hello"));

        var routes = rest.getRoutes();
        var getRoutes = routes.get(RestHivers.Method.GET);

        assertNotNull(getRoutes);
        assertEquals(getRoutes.size(), 1);
    }

    /*
    @Test
    void shouldAccessRoute() {
        var rest = new RestHivers();
        rest.register(RestHivers.Method.GET, "/", context ->
                context.response(200, "Hello"))
                .port(8000)
                .start();
    }
     */
}
