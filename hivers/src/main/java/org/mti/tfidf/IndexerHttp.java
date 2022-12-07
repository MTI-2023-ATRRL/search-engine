package org.mti.tfidf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mti.hivers.Hivers;
import org.mti.hivers.server.RestHivers;
import org.mti.kafka.AppHttp;
import org.mti.tfidf.transport.TransportLayerHttp;
import org.mti.tfidf.transport.TransportLayerText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class IndexerHttp {

    private final Indexer indexer;
    private final ObjectMapper objectMapper;

    private class ResponseDto
    {
        String body;
        int status;

        private ResponseDto(String body, int status)
        {
            this.body = body;
            this.status = status;
        }
    }

    public IndexerHttp()
    {
        this.indexer = new Indexer();
        this.objectMapper = new ObjectMapper();
    }

    private ResponseDto connect(AppHttp.ConsumeDto consume)
    {

        return this.query("connect", consume);

    }

    private void createTopics()
    {
        var indexerDto = new AppHttp.CreateTopicDto();
        indexerDto.topicName = "indexer";
        indexerDto.numberOfPartition = 8;

        this.query("topic", indexerDto);

        var retroIndexDto = new AppHttp.CreateTopicDto();
        retroIndexDto.topicName = "retroindexer";
        retroIndexDto.numberOfPartition = 8;

        this.query("topic", retroIndexDto);

    }

    private ResponseDto consume(AppHttp.ConsumeDto consumeDto)
    {
        return this.query("consume", consumeDto);

    }

    private ResponseDto supply(AppHttp.SupplyDto supply)
    {
        return this.query("supply", supply);
    }


    private ResponseDto query(String path, Object obj)
    {
        URL url = null;
        try {
            url = new URL("http://localhost:3333/" + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonSerialized = objectMapper.writeValueAsString(obj);

            OutputStream os = con.getOutputStream();
            byte[] input = jsonSerialized.getBytes("utf-8");
            os.write(input, 0, input.length);

            os.close();

            BufferedReader  br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String resp = null;
            while ((resp = br.readLine()) != null) {
                response.append(resp.trim());
            }

            var strResponse = response.toString();

            int status = con.getResponseCode();

            var responseDto = new ResponseDto(strResponse, status);

            return responseDto;


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        var hivers = new Hivers();

        var indexerHttp = new IndexerHttp();

        var consume = new AppHttp.ConsumeDto();
        consume.identity = "indexer";
        consume.topicName = "retroindexer";

        indexerHttp.createTopics();

        var resultConnect = indexerHttp.connect(consume);

        if (resultConnect.status >= 400)
            throw new RuntimeException("Could not connect to Kafka");

        hivers.register(new RestHivers());
        hivers.extension(RestHivers.class)
                .register(RestHivers.Method.POST, "/index", context ->
                {

                    var resp = indexerHttp.consume(consume);
                    if (resp.status >= 400)
                    {
                        context.response(400, "Error while processing");
                        return;
                    }

                    try {
                        var respDto = indexerHttp.objectMapper
                                .readValue(resp.body, AppHttp.ConsumeResponseDto.class);

                        while (respDto.status == "Success")
                        {
                            TransportLayerHttp transport = new TransportLayerHttp(respDto.content);
                            var doc = indexerHttp.indexer.addDocument(transport);

                            var supply = new AppHttp.SupplyDto();
                            supply.messageContent = indexerHttp.objectMapper.writeValueAsString(doc);
                            supply.messageId = UUID.randomUUID().toString();
                            supply.topicName = "retroindexer";

                            indexerHttp.supply(supply);

                            respDto = indexerHttp.objectMapper
                                    .readValue(resp.body, AppHttp.ConsumeResponseDto.class);
                        }

                    } catch (JsonProcessingException e) {
                        context.response(400);
                        return;
                    } catch (MalformedURLException e) {
                        context.response(400);
                        return;
                    }

                    context.response(200, "Documents indexed");

                }).start();
    }
}
