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



public class Https {
    private static  final Gson gson = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    private Https() {

    }

    public static Response request(String url) {
        return request(url, Duration.ofSeconds(5));
    }

    public static Response request(String url, Duration timeout) {
        return request(URI.create(url), timeout);
    }

    public static Response request(URI uri, Duration timeout) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(timeout)
                    .GET()
                    .build();

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

        } catch (InterruptedException | IOException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            throw new RuntimeException(
                    "Request Exception: " + e.getMessage(),
                    e
            );
        }
    }
}
