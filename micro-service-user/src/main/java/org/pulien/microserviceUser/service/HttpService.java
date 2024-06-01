package org.pulien.microserviceUser.service;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HttpService {

    @Value("${gateway.baseurl}")
    private String baseUrl;

    public String sendGetRequest(String endpoint) throws HttpException {
        try {
            String url = baseUrl + endpoint;
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                throw new HttpException("GET request not worked, Response Code: " + response.statusCode());
            }
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new HttpException("GET request failed!");
        }
    }

    public String sendPostRequest(String endpoint, String payload) throws HttpException {
        try {
            String url = baseUrl + endpoint;
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new HttpException("POST request failed!");
        }
    }
}
