package com.mixplus.library.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;



public class Https {
    private static  final Gson gson = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    private Https() {

    }

    public static Response request(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
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
