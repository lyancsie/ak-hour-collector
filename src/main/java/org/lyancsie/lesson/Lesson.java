package org.lyancsie.lesson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class Lesson {
    private final LessonType lessonType;
    private final LocalDate date;
    private final double duration;
    private final String topic;
}
