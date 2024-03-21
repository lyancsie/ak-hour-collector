package org.lyancsie;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class GroupLessonDeserializerStrategy implements LessonDeserializerStrategy<GroupLesson> {
    @Override
    public JsonObject getJsonFromURL(String lessonUrl) {
        Gson gson = new Gson();
        HttpRequest request = null;
        try {
            request = HttpUtils.getRequest(lessonUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpClient client = HttpUtils.getClient();
        final HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Response: {}", response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response.body(), JsonObject.class);
    }
}
