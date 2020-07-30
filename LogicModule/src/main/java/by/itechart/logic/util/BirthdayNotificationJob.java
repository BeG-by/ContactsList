package by.itechart.logic.util;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.LoadPropertiesException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.EmailService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.logic.service.impl.EmailServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class BirthdayNotificationJob implements Job {

    private ContactService contactService;
    private EmailService emailService;

    private String adminEmail;

    private static final String PROPERTIES_PATH = "/mail.properties";
    private static final String SUBJECT = "List of birthday";

    private static final Logger LOGGER = LoggerFactory.getLogger(BirthdayNotificationJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        try (Connection connection = ConnectionFactory.createConnection()) {

            loadProperties();
            contactService = new ContactServiceImpl(connection);
            emailService = new EmailServiceImpl();

            final List<Contact> contacts = contactService.findByTodayDate(LocalDate.now());

            if (!contacts.isEmpty()) {

                StringBuilder textMessage = new StringBuilder("List of contacts birthday\n");

                contacts.forEach(c -> {
                    textMessage.append(c.getId()).append(" ");
                    textMessage.append(c.getFirstName()).append(" ");
                    textMessage.append(c.getLastName()).append(" ");
                    textMessage.append(c.getMiddleName()).append(" ");
                    textMessage.append(c.getBirthday()).append("\n");
                });

                emailService.sendEmail(adminEmail, SUBJECT, textMessage.toString());
                LOGGER.info("Birthday notification has sent list of contacts: {}", contacts);

            }


        } catch (Exception e) {
            LOGGER.error("Birthday notification was failed !", e);
        }

    }

    private void loadProperties() throws LoadPropertiesException {

        Properties properties = new Properties();
        try {
            InputStream in = EmailServiceImpl.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);

            adminEmail = properties.getProperty("user.email");

        } catch (IOException e) {
            throw new LoadPropertiesException();
        }

    }

}
