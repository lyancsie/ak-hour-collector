package org.lyancsie;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class PrivateLesson extends Lesson {
    private final String studentName;
    private final String topic;

    public PrivateLesson(String studentName, LocalDate date, double duration, String topic) {
        super(LessonType.PRIVATE, date, duration);
        this.studentName = studentName;
        this.topic = topic;
    }
}
