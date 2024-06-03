package org.lyancsie.email;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lyancsie.config.PropertiesLoader;
import org.lyancsie.lesson.Lesson;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    static final String FROM = PropertiesLoader.getProperties().getProperty("sender-email");
    static final String TO = PropertiesLoader.getProperties().getProperty("recipient-email");
    private static final String UTF_8 = "UTF-8";
    private static final String SUBJECT = "Havi órák";

    private final Set<Lesson> lessons;

    public void sendEmail() throws IOException {
        try {
            final String textBody = EmailGenerator.generateEmailBody(lessons);
            final String htmlBody = EmailGenerator.generateEmailBodyHtml(lessons);
            AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                    .withRegion(Regions.EU_CENTRAL_1).build();
            SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                    new Destination().withToAddresses(TO))
                .withMessage(new Message()
                    .withBody(new Body()
                        .withHtml(new Content()
                            .withCharset(UTF_8).withData(htmlBody))
                        .withText(new Content()
                            .withCharset(UTF_8).withData(textBody)))
                    .withSubject(new Content()
                        .withCharset(UTF_8).withData(SUBJECT)))
                .withSource(FROM);
            client.sendEmail(request);
            log.info("Email sent!");
        } catch (Exception ex) {
            log.error("The email was not sent. Error message: "
                + ex.getMessage());
        }
    }
}
