package com.mixplus.library.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;



public class Http {
    private static  final Gson gson = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    private Http() {

    }

    public static Response request(String url) throws IOException, InterruptedException {
        return request(url, Duration.ofSeconds(5));
    }

    public static Response request(String url, Duration timeout) throws IOException, InterruptedException {
        return request(URI.create(url), timeout);
    }

    public static Response request(URI uri, Duration timeout) throws IOException, InterruptedException {
        return get(uri, timeout, Map.of());
    }

    public static Response get(
            URI uri,
            Duration timeout,
            Map<String, String> header
    ) throws IOException, InterruptedException{

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(timeout);


        header.forEach(builder::header);

        HttpRequest request = builder.GET().build();

        return send(request);
    }

    public static Response post(
            URI uri,
            Duration timeout,
            Map<String, Object> data
    ) throws IOException, InterruptedException {

        return post(uri, timeout, data, Map.of());
    }

    public static Response post(
            URI uri,
            Duration timeout,
            Map<String, Object> data,
            Map<String, String> header
    ) throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)));

        header.forEach(builder::header);

        HttpRequest request = builder.build();

        return send(request);
    }

    public static Response send(
            HttpRequest request
    ) throws IOException, InterruptedException {

        HttpResponse<String> response =
                CLIENT.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        Map<String, Object> body =
                gson.fromJson(
                        response.body(),
                        new TypeToken<Map<String, Object>>() {}.getType()
                );


        return new Response(
                response.statusCode(),
                body
        );
    }
}
