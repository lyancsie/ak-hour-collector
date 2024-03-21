package org.lyancsie;

import com.google.gson.JsonObject;

import java.util.Set;

public interface LessonHtmlExtractorStrategy<T extends Lesson> {
    Set<T> extract(String lessonHtml);
}
