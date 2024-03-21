package org.lyancsie.extractor;

import org.lyancsie.lesson.Lesson;

import java.util.Set;

public interface LessonHtmlExtractorStrategy<T extends Lesson> {
    Set<T> extract(String lessonHtml);
}
