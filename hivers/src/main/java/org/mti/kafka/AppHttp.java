package org.mti.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mti.hivers.Hivers;
import org.mti.hivers.server.Context;
import org.mti.hivers.server.RestHivers;
import org.mti.kafka.topic.TopicResult;

public class AppHttp {
    private final Kafka kafka;
    private final ObjectMapper objectMapper;

    AppHttp() {
        this.kafka = new Kafka();
        this.objectMapper = new ObjectMapper();
    }

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
                }).start();
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
}