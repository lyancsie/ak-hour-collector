package org.lyancsie.lesson;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

@Slf4j
public class LessonAggregator {

    private static final String EXAM_REGEX = ".*[Ss]zintfelmérő$";
    private static final LocalDate CURRENT_DATE = LocalDate.now(ZoneId.of("CET"));


    private LessonAggregator() {
    }

    public static double aggregateLessons(Set<Lesson> lessons) {

        final var firstDayOfTheMonth = getFirstDayOfPreviousMonth();
        log.info(
            "Aggregated lessons: {}",
            lessons.stream()
                .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
                .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
                .filter(lesson -> !lesson.getTopic().matches(EXAM_REGEX)).toList().toString());

        return lessons.stream()
            .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
            .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
            .filter(lesson -> !lesson.getTopic().matches(EXAM_REGEX))
            .map(Lesson::getDuration)
            .reduce(Double::sum)
            .orElse(0.0);
    }

    //FIXME -> teachers are not working during exams!
    public static double aggregateLessons(Set<Lesson> lessons, LessonType type) {
        if (type == null) {
            return aggregateLessons(lessons);
        }
        final var firstDayOfPreviousMonth = getFirstDayOfPreviousMonth();
        for (Lesson lesson : lessons) {
            log.debug("Topic: " + lesson.getTopic() + " " + lesson.getTopic().matches(EXAM_REGEX));
        }
        log.info(
            "Aggregated {} lessons: {}",
            type,
            lessons.stream()
                .filter(lesson -> lesson.getDate().isAfter(firstDayOfPreviousMonth.minusDays(1)))
                .filter(lesson -> lesson.getDate().isBefore(firstDayOfPreviousMonth.with(TemporalAdjusters.lastDayOfMonth())))
                .filter(lesson -> lesson.getLessonType().equals(type))
                .filter(lesson -> !lesson.getTopic().matches(EXAM_REGEX)).toList().toString());

        return lessons.stream()
            .filter(lesson -> lesson.getDate().isAfter(firstDayOfPreviousMonth.minusDays(1)))
            .filter(lesson -> lesson.getDate().isBefore(firstDayOfPreviousMonth.with(TemporalAdjusters.lastDayOfMonth())))
            .filter(lesson -> lesson.getLessonType().equals(type))
            .filter(lesson -> !lesson.getTopic().matches(EXAM_REGEX))
            .map(Lesson::getDuration)
            .reduce(Double::sum)
            .orElse(0.0);
    }

    private static LocalDate getFirstDayOfPreviousMonth() {
        return LocalDate.of(CURRENT_DATE.getYear(), CURRENT_DATE.getMonth().minus(1), 1);
    }
}
