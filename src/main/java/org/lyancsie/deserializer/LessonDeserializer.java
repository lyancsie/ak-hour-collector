package org.lyancsie.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.lyancsie.lesson.Lesson;
import org.lyancsie.lesson.LessonType;
import org.lyancsie.config.PropertiesLoader;
import org.lyancsie.extractor.GroupLessonHtmlExtractorStrategy;
import org.lyancsie.extractor.PrivateLessonHtmlExtractorStrategy;
import org.lyancsie.http.HttpUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class LessonDeserializer {

    private static final List<String> lessonUrls = PropertiesLoader.getUrlsFromYaml();

    public final Set<Lesson> deserializeLessons() {
        List<Lesson> lessons = new ArrayList<>();
        for (String lessonUrl : lessonUrls) {
            final var lessonJson = getLessonJson(lessonUrl);
            final var lessonHtml = getHtmlFromJson(lessonJson);
            lessons.addAll(getLessonListFromHtml(lessonHtml, lessonJson));
        }
        return Set.copyOf(lessons);
//        return lessons;
//        return lessonUrls.stream()
//            .map(this::getLessonJson)
//            .map(this::getHtmlFromJson)
//            .map(this::getLessonListFromHtml)
//            .flatMap(Collection::stream)
//            .collect(Collectors.toSet());
    }

    private Set<? extends Lesson> getLessonListFromHtml(String lessonHtml, JsonObject lessonJson) {
        final var lessonType = identifyLessonType(lessonJson);
        switch (lessonType) {
            case PRIVATE -> {
                return new PrivateLessonHtmlExtractorStrategy().extract(lessonHtml);
            }
            case GROUP -> {
                return new GroupLessonHtmlExtractorStrategy().extract(lessonHtml);
            }
            default -> throw new IllegalStateException("Unexpected value: " + lessonType);
        }
    }

    private LessonType identifyLessonType(JsonObject lessonJson) {
        String title = getJsonTitle(lessonJson);
        switch (title) {
            case "Haladási napló" -> {
                return LessonType.GROUP;
            }
            case "Magánórák" -> {
                return LessonType.PRIVATE;
            }
            default -> throw new IllegalArgumentException("Unknown lesson type");
        }
    }

    private String getHtmlFromJson(JsonObject lessonJson) {
        final var html = lessonJson.getAsJsonObject("body")
            .getAsJsonObject("storage")
            .get("value")
            .getAsString();
        return StringEscapeUtils.unescapeHtml4(html);
    }

    private String getJsonTitle(JsonObject lessonJson) {
        return lessonJson
            .get("title")
            .getAsString();
    }

    private JsonObject getLessonJson(String lessonUrl) {
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
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response.body(), JsonObject.class);
    }
}
