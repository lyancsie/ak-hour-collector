package org.lyancsie.lesson;

import lombok.ToString;
import java.time.LocalDate;

@ToString(callSuper = true)
public class GroupLesson extends Lesson {
    public GroupLesson(LocalDate date, double duration, String topic) {
        super(LessonType.GROUP, date, duration, topic);
    }
}
