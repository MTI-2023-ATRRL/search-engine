package org.mti.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mti.hivers.Hivers;
import org.mti.hivers.server.Context;
import org.mti.hivers.server.RestHivers;
import org.mti.kafka.consumer.Consumer;
import org.mti.kafka.consumer.ConsumerConnectResult;
import org.mti.kafka.consumer.ConsumerResult;
import org.mti.kafka.message.Message;
import org.mti.kafka.supplier.Supplier;
import org.mti.kafka.supplier.SupplyResult;
import org.mti.kafka.topic.TopicResult;

public class AppHttp {
    public static void main(String[] args) {
        var hivers = new Hivers();
        var app = new AppHttp();

        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.POST, "/topic", context -> {
                    try {
                        app.AddTopic(context);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .register(RestHivers.Method.POST, "/supply",  context -> {
                    try {
                        app.supply(context);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .register(RestHivers.Method.POST, "/connect",  context -> {
                    try {
                        app.connect(context);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .register(RestHivers.Method.POST, "/disconnect",  context -> {
                    try {
                        app.disconnect(context);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .register(RestHivers.Method.POST, "/consume",  context -> {
                    try {
                        app.consume(context);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .port(3333)
                .start();
    }

    private final Kafka kafka;
    private final ObjectMapper objectMapper;

    AppHttp() {
        this.kafka = new Kafka();
        this.objectMapper = new ObjectMapper();
    }

    public static class CreateTopicDto {
        public String topicName;
        public int numberOfPartition;

        public CreateTopicDto() {
        }

        public CreateTopicDto(String topicName, int numberOfPartition) {
            this.topicName = topicName;
            this.numberOfPartition = numberOfPartition;
        }
    }

    public void AddTopic(Context context) throws JsonProcessingException {
        String json = context.getRequestBody();
        var createTopicDto = objectMapper.readValue(json, CreateTopicDto.class);

        var result = kafka.addTopic(createTopicDto.topicName, createTopicDto.numberOfPartition);
        if (result.status == TopicResult.TopicResultStatus.ALREADY_EXIST) {
            context.response(400, "Topic Already exist");
            return;
        }
        context.response(200, "Topic created");
    }

    public static class SupplyDto {
        public String messageId;
        public String messageContent;
        public String topicName;

        public SupplyDto() {
        }
    }

    public void supply(Context context) throws JsonProcessingException {
        String json = context.getRequestBody();
        var supplyDto = objectMapper.readValue(json, SupplyDto.class);

        var supply = new Supplier(supplyDto.topicName, new Message(supplyDto.messageId, supplyDto.messageContent));

        var result = kafka.supply(supply);
        if (result.status() == SupplyResult.SupplyStatus.TOPIC_DOES_NOT_EXIST) {
            context.response(400, "Topic does not exist");
            return;
        }
        context.response(200, "Message sent");
    }

    public static class ConsumeDto {
       public String identity;
       public String topicName;

       public ConsumeDto() {}
    }
    public void connect(Context context) throws JsonProcessingException {
        String json = context.getRequestBody();
        var connectDto = objectMapper.readValue(json, ConsumeDto.class);
        var consumer = new Consumer(connectDto.topicName, connectDto.identity);

        var result = kafka.connect(consumer);
        if (result.status == ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST) {
            context.response(400, "Topic does not exist");
            return;
        }

        if (result.status == ConsumerConnectResult.ConsumerConnectStatus.ALREADY_CONNECTED) {
            context.response(400, "You are already connected");
            return;
        }

        if (result.status == ConsumerConnectResult.ConsumerConnectStatus.UNABLE_TO_CONNECT) {
            context.response(400, "Unable to connect");
            return;
        }

        context.response(200, "Connected !");
    }

    public void disconnect(Context context) throws JsonProcessingException {
        String json = context.getRequestBody();
        var connectDto = objectMapper.readValue(json, ConsumeDto.class);
        var consumer = new Consumer(connectDto.topicName, connectDto.identity);

        var result = kafka.disconnect(consumer);
        if (result.status == ConsumerConnectResult.ConsumerConnectStatus.TOPIC_DOES_NOT_EXIST) {
            context.response(400, "Topic does not exist");
            return;
        }

        if (result.status == ConsumerConnectResult.ConsumerConnectStatus.NOT_CONNECTED) {
            context.response(400, "You are not connected");
            return;
        }

        context.response(200, "Disconnected !");
    }

    public class ConsumeResponseDto {
        public String status;
        public String id;
        public String content;

        public ConsumeResponseDto() {}
    }

    public void consume(Context context) throws JsonProcessingException {
        String json = context.getRequestBody();
        var connectDto = objectMapper.readValue(json, ConsumeDto.class);
        var consumer = new Consumer(connectDto.topicName, connectDto.identity);

        var result = kafka.consume(consumer);
        System.out.println(result);
        if (result.status() == ConsumerResult.ConsumeStatus.TOPIC_DOES_NOT_EXIST) {
            context.response(400, "Topic does not exist");
            return;
        }
        if (result.status() == ConsumerResult.ConsumeStatus.NO_CONNECTED_TO_THIS_TOPIC) {
            context.response(400, "You are no connected to this Topic");
            return;
        }

        var consumeDto = new ConsumeResponseDto();
        consumeDto.content = result.content();
        consumeDto.id = result.id();
        if (result.status() == ConsumerResult.ConsumeStatus.NO_MESSAGE_AVAILABLE) {
            consumeDto.status = "No message available";
        } else {
            consumeDto.status = "Success";
        }

        String resultInJson = objectMapper.writeValueAsString(consumeDto);
        context.response(200, resultInJson);
    }
}
