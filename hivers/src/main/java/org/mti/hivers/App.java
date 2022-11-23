package org.mti.hivers;

import org.mti.hivers.server.RestHivers;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        /*var rest = new RestHivers();
        rest.register(RestHivers.Method.GET, "/", context ->
                        context.response(201, "Hello"))
                .port(8888)
                .start();
        */
    }
}
