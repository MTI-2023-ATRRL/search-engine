package org.mti.kafka;

public class Message {
    public final String id;
    public final String content;

    public Message(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
