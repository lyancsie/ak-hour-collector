package org.lyancsie;

import com.google.gson.JsonObject;

public interface LessonDeserializerStrategy<T> {
    JsonObject getJsonFromURL(String lessonUrl);
}
