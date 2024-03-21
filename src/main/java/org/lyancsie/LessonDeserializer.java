package org.lyancsie;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class LessonDeserializer {

    private static final String APPLICATION_YML_PATH = "src/main/resources/application.yml";
    private final List<String> lessonUrls = PropertiesLoader.getUrlsFromYaml(APPLICATION_YML_PATH);

//    private final LessonHtmlExtractorStrategy lessonHtmlExtractorStrategy;
//
//    private final LessonDeserializerStrategy lessonDeserializerStrategy;

    public final Set<Lesson> deserializeLessons() {
//        List<Lesson> lessons = new ArrayList<>();
//        for (String lessonUrl : lessonUrls) {
//            final var lessonJson = getLessonJson(lessonUrl);
//            final var lessonHtml = getHtml(lessonJson);
//            lessons.addAll(getLessonListFromHtml(lessonHtml));
//        }
//        return lessons;
        return lessonUrls.stream()
            .map(this::getLessonJson)
            .map(this::getHtml)
            .map(this::getLessonListFromHtml)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    private Set<? extends Lesson> getLessonListFromHtml(String lessonHtml) {
        final var lessonType = identifyLessonType(lessonHtml);
        switch (lessonType) {
            case PRIVATE -> {
                return new GroupLessonHtmlExtractorStrategy().extract(lessonHtml);
            }
            case GROUP -> {
                return new GroupLessonHtmlExtractorStrategy().extract(lessonHtml);
            }
            default -> throw new IllegalStateException("Unexpected value: " + lessonType);
        }
    }

    private Set<Lesson> getGroupLessons(String lessonHtml) {
        return null;
    }

    private LessonType identifyLessonType(String lessonHtml) {
        return LessonType.PRIVATE;
    }

    private String getHtml(JsonObject lessonJson) {
        final var html = lessonJson.getAsJsonObject("body")
            .getAsJsonObject("storage")
            .get("value")
            .getAsString();
        //log.debug("HTML is: {}", html);
        return StringEscapeUtils.unescapeHtml4(html);
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
            //log.info("Response: {}", response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response.body(), JsonObject.class);
    }
}
