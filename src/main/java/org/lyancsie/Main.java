package org.lyancsie;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.Set;

@Slf4j
public class Main {

    //private static final String URL;
    private static final int YEAR;
    private static final int MONTH;
    private static final Properties properties;

    private static LessonDeserializer lessonDeserializer;

    static {
        properties = PropertiesLoader.getProperties();
        YEAR = Integer.parseInt(properties.get("year").toString());
        MONTH = Integer.parseInt(properties.get("month").toString());
        lessonDeserializer = new LessonDeserializer();
    }


    public static void main(String[] args) {
        Set<Lesson> lessons = lessonDeserializer.deserializeLessons();

        log.debug("Number of lessons: {}", lessons.size());
        log.info("Lessons: " + lessons);

//        final var firstDayOfTheMonth = LocalDate.of(YEAR, MONTH, 1);
//        lessons.stream()
//            .filter(lesson -> lesson.getDate().isAfter(firstDayOfTheMonth.minusDays(1)))
//            .filter(lesson -> lesson.getDate().isBefore(firstDayOfTheMonth.with(TemporalAdjusters.lastDayOfMonth())))
//            .map(Lesson::getDuration)
//            .reduce(Double::sum)
//            .ifPresent(amount -> log.info(amount.toString()));

        EmailGenerator emailGenerator = new EmailGenerator();
        log.info(emailGenerator.generateEmail(lessons));
    }
}