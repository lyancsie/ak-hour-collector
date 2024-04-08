package org.lyancsie;

import lombok.extern.slf4j.Slf4j;
import org.lyancsie.deserializer.LessonDeserializer;
import org.lyancsie.lesson.Lesson;
import org.lyancsie.email.EmailGenerator;

import java.net.URISyntaxException;
import java.util.Set;

@Slf4j
public class Main {

    private static final LessonDeserializer lessonDeserializer = new LessonDeserializer();


    public static void main(String[] args) throws URISyntaxException {
        Set<Lesson> lessons = lessonDeserializer.deserializeLessons();

        log.debug("Number of lessons: {}", lessons.size());
        log.info("Lessons: " + lessons);

        EmailGenerator emailGenerator = new EmailGenerator();
        log.info(emailGenerator.generateEmail(lessons));

        /*
        A gmail küldéshez nyissak majd egy új package-t?
         */
    }
}