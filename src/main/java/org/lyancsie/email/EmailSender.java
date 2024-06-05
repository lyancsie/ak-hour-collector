package org.lyancsie.email;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lyancsie.config.PropertiesLoader;
import org.lyancsie.lesson.Lesson;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    static final String FROM = PropertiesLoader.getProperties().getProperty("sender-email");
    static final String TO = PropertiesLoader.getProperties().getProperty("recipient-email");
    private static final String UTF_8 = "UTF-8";
    private static final String SUBJECT = "Havi órák";
    private static final String EMAIL_NOT_SENT_MESSAGE = "The email was not sent to {}. Error message: ";

    private final Set<Lesson> lessons;

    public void sendEmail() {
        AmazonSimpleEmailService client =
            AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        sendEmailTo(client, FROM);
        sendEmailTo(client, TO);
    }

    private void sendEmailTo(AmazonSimpleEmailService client, String recipientEmail) {
        final String textBody = EmailGenerator.generateEmailBody(lessons);
        final String htmlBody = EmailGenerator.generateEmailBodyHtml(lessons);

        final var emailRequest = getEmailRequest(htmlBody, textBody, recipientEmail);
        try {
            client.sendEmail(emailRequest);
            log.info("Email sent to {}!", recipientEmail);
        } catch (Exception ex) {
            log.error(EMAIL_NOT_SENT_MESSAGE, recipientEmail + ex.getMessage());
        }
    }

    private static SendEmailRequest getEmailRequest(String htmlBody, String textBody, String recipientEmail) {
        return new SendEmailRequest()
            .withDestination(
                new Destination().withToAddresses(recipientEmail))
            .withMessage(new Message()
                .withBody(new Body()
                    .withHtml(new Content()
                        .withCharset(UTF_8).withData(htmlBody))
                    .withText(new Content()
                        .withCharset(UTF_8).withData(textBody)))
                .withSubject(new Content()
                    .withCharset(UTF_8).withData(SUBJECT)))
            .withSource(FROM);
    }
}
