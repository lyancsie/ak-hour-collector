package org.lyancsie.email;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.lyancsie.config.PropertiesLoader;
import org.lyancsie.lesson.Lesson;
import org.lyancsie.lesson.LessonAggregator;
import org.lyancsie.lesson.LessonType;

import java.util.Set;

@Slf4j
@UtilityClass
//TODO: refactor to use a template engine
public class EmailGenerator {
    private static final int HOURLY_WAGE = Integer.parseInt(PropertiesLoader.getProperties().getProperty("hourly-wage"));
    private static final boolean IS_VAT = Boolean.parseBoolean(PropertiesLoader.getProperties().getProperty("vat"));
    private static final String NAME = PropertiesLoader.getProperties().getProperty("name");
    private static final String EMAIL_TEMPLATE =
        IS_VAT ?
            """
                Szia, Dóri!
                      
                Küldöm az aktuális havi órákat:
                        
                Csoportos órák: %.0f
                Privát órák: %.0f
                        
                Így összesen %.0f órát tartottam, ami %d Forintos óradíj mellett %d Ft + ÁFA
                kiszámlázását jelenti.
                        
                Köszi és üdv:
                %s
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
                %s
                """;

    private static final String HTML_EMAIL_TEMPLATE = """
        <html>
            <body>
                <h1>Szia, Dóri!</h1>
                <p>Küldöm az aktuális havi órákat:</p>
                <ul>
                    <li>Csoportos órák: %.0f</li>
                    <li>Privát órák: %.0f</li>
                </ul>
                <p>Így összesen %.0f órát tartottam, ami %d Forintos óradíj mellett %d Ft + ÁFA kiszámlázását jelenti.</p>
                <p>Köszi és üdv:</p>
                <p>%s</p>
            </body>
        </html>""";

    public static String generateEmailBody(Set<Lesson> lessons) {
        //JSP, Thymeleaf, FreeMarker
        return getFormattedText(lessons, EMAIL_TEMPLATE);
    }

    public static String generateEmailBodyHtml(Set<Lesson> lessons) {
        return getFormattedText(lessons, HTML_EMAIL_TEMPLATE);
    }

    private static String getFormattedText(Set<Lesson> lessons, String template) {
        final double numberOfLessons = LessonAggregator.aggregateLessons(lessons);
        final double numberOfGroupLessons = LessonAggregator.aggregateLessons(lessons, LessonType.GROUP);
        final double numberOfPrivateLessons = LessonAggregator.aggregateLessons(lessons, LessonType.PRIVATE);
        log.debug("Number of lessons: {}", numberOfLessons);
        return String.format(template,
            numberOfGroupLessons,
            numberOfPrivateLessons,
            numberOfLessons,
            HOURLY_WAGE,
            (int) (numberOfLessons * HOURLY_WAGE),
            NAME
        );
    }
}
