package org.lyancsie;

import lombok.extern.slf4j.Slf4j;
import org.lyancsie.deserializer.LessonDeserializer;
import org.lyancsie.email.EmailGenerator;
import org.lyancsie.email.EmailSender;
import org.lyancsie.lesson.Lesson;

import java.io.IOException;
import java.util.Set;

@Slf4j
public class Main {

    private static final LessonDeserializer lessonDeserializer = new LessonDeserializer();


    public static void main(String[] args) throws IOException {
        Set<Lesson> lessons = lessonDeserializer.deserializeLessons();

        log.debug("Number of lessons: {}", lessons.size());
        log.debug("Lessons: " + lessons);

        log.info(EmailGenerator.generateEmailBody(lessons));
        EmailSender emailSender = new EmailSender(lessons);
        emailSender.sendEmail();
    }
}