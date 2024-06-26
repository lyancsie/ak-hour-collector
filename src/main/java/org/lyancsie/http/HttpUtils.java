package org.lyancsie.http;

import org.lyancsie.config.PropertiesLoader;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.Properties;

public class HttpUtils {

    static final Properties properties = PropertiesLoader.getProperties();

    private HttpUtils() {
    }

    private static String getAuthString() {
        final var atlassianEmail = properties.getProperty("atlassian-email");
        final var apiKey = properties.getProperty("api-key");
        return atlassianEmail + ":" + apiKey;
    }

    private static String getEncodedAuth() {
        return Base64.getEncoder().encodeToString(getAuthString().getBytes());
    }

    public static HttpRequest getRequest(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
            .uri(new URI(url))
            .header("Accept", "application/json")
            .header("Authorization", "Basic " + getEncodedAuth())
            .GET()
            .build();
        //GET, POST, PUT, DELETE
    }

    public static HttpClient getClient() {
        return HttpClient.newBuilder().build();
    }
}
