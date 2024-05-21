package org.lyancsie.email;

import lombok.extern.slf4j.Slf4j;
import org.lyancsie.config.PropertiesLoader;
import org.lyancsie.lesson.Lesson;
import org.lyancsie.lesson.LessonAggregator;
import org.lyancsie.lesson.LessonType;

import java.util.Set;

@Slf4j
public class EmailGenerator {
    private static final int HOURLY_WAGE = Integer.parseInt(PropertiesLoader.getProperties().getProperty("hourly-wage"));
    private static final boolean IS_VAT = Boolean.parseBoolean(PropertiesLoader.getProperties().getProperty("vat"));
    private static final String EMAIL_TEMPLATE =
        IS_VAT ?
            """
                Sziasztok!
                        
                Küldöm az aktuális havi órákat:
                        
                Csoportos órák: %.0f
                Privát órák: %.0f
                        
                Így összesen %.0f órát tartottam, ami %d Forintos óradíj mellett %d Ft + ÁFA
                kiszámlázását jelenti.
                        
                Köszi és üdv:
                Csongi
                """
            :
            """
                Sziasztok!
                           
                Küldöm az aktuális havi órákat:
                           
                Csoportos órák: %.0f
                Privát órák: %.0f
                           
                Így összesen %.0f órát tartottam, ami %d Forintos óradíj mellett %d Ft
                kiszámlázását jelenti.
                           
                Köszi és üdv:
                Csongi
                """;

    public String generateEmail(Set<Lesson> lessons) {
        //JSP, Thymeleaf, FreeMarker
        final double numberOfLessons = LessonAggregator.aggregateLessons(lessons);
        final double numberOfGroupLessons = LessonAggregator.aggregateLessons(lessons, LessonType.GROUP);
        final double numberOfPrivateLessons = LessonAggregator.aggregateLessons(lessons, LessonType.PRIVATE);
        log.debug("Number of lessons: {}", numberOfLessons);
        return String.format(EMAIL_TEMPLATE,
            numberOfGroupLessons,
            numberOfPrivateLessons,
            numberOfLessons,
            HOURLY_WAGE,
            (int) (numberOfLessons * HOURLY_WAGE)
        );
    }


}
