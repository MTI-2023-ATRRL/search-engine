package org.mti.hivers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    @Test
    void helloWorld() {

        assertSame(Singleton.helloWorld(), "Hello World");
    }
}