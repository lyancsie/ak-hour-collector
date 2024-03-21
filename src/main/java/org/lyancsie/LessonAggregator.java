package org.lyancsie;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Properties;
import java.util.Set;
@Slf4j
public class LessonAggregator {

    static Properties properties = PropertiesLoader.getProperties();
    static final int YEAR = Integer.parseInt(properties.getProperty("year"));
    static final int MONTH = Integer.parseInt(properties.getProperty("month"));

    public double aggregateLessons(Set<Lesson> lessons) {
        final var firstDayOfTheMonth = LocalDate.of(YEAR, MONTH, 1);
        return lessons.stream()
            .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
            .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
            .map(Lesson::getDuration)
            .reduce(Double::sum)
            .orElse(0.0);
    }
}
