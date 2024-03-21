package org.lyancsie.lesson;

import lombok.extern.slf4j.Slf4j;
import org.lyancsie.config.PropertiesLoader;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class LessonAggregator {

    private LessonAggregator() {
    }

    static Properties properties = PropertiesLoader.getProperties();
    static final int YEAR = Integer.parseInt(properties.getProperty("year"));
    static final int MONTH = Integer.parseInt(properties.getProperty("month"));

    public static double aggregateLessons(Set<Lesson> lessons) {
        final var firstDayOfTheMonth = LocalDate.of(YEAR, MONTH, 1);
        return lessons.stream()
            .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
            .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
            .map(Lesson::getDuration)
            .reduce(Double::sum)
            .orElse(0.0);
    }

    //FIXME -> teachers are not working during exams!
    public static double aggregateLessons(Set<Lesson> lessons, LessonType type) {
        if (type == null) {
            return aggregateLessons(lessons);
        }
        final var firstDayOfTheMonth = LocalDate.of(YEAR, MONTH, 1);
        return lessons.stream()
            .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
            .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
            .filter(lesson -> lesson.getLessonType().equals(type))
            .map(Lesson::getDuration)
            .reduce(Double::sum)
            .orElse(0.0);
    }
}
