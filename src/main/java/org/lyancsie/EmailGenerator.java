package org.lyancsie;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class EmailGenerator {
    private static final LessonAggregator lessonAggregator = new LessonAggregator();
    private static final int HOURLY_WAGE = Integer.parseInt(PropertiesLoader.getProperties().getProperty("hourly-wage"));
    private static final String EMAIL_TEMPLATE = """
        Sziasztok!
        
        Küldöm az aktuális havi órákat:
        
        Csoportos órák: %d
        Privát órák: %d
        
        Így összesen %.1f órát tartottam, ami %d Forintos óradíj mellett %d Ft + ÁFA
        kiszámlázását jelenti.
        
        Köszi és üdv:
        Csongi
        """;

    public String generateEmail(Set<Lesson> lessons) {
        final double numberOfLessons = lessonAggregator.aggregateLessons(lessons);
        log.info("Number of lessons: {}", numberOfLessons);
        log.info("Lessons: " + lessons);
        return String.format(EMAIL_TEMPLATE,
            0,
            0,
            numberOfLessons,
            HOURLY_WAGE,
            (int) (numberOfLessons * HOURLY_WAGE)
        );
    }


}
