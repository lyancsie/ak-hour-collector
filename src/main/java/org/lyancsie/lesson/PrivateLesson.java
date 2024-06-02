package org.lyancsie.lesson;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString(callSuper = true)
public class PrivateLesson extends Lesson {
    private final String studentName;

    public PrivateLesson(String studentName, LocalDate date, double duration, String topic) {
        super(LessonType.PRIVATE, date, duration, topic);
        this.studentName = studentName;
    }
}
