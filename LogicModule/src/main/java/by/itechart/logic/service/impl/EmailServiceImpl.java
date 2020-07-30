package by.itechart.logic.service.impl;

import by.itechart.logic.exception.LoadPropertiesException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final String PROPERTIES_PATH = "/mail.properties";

    private String userEmail;
    private String password;


    @Override
    public void sendEmail(String to, String subject, String text) throws ServiceException, LoadPropertiesException {

        final Properties properties = loadProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, password);
            }
        });

        Message message = new MimeMessage(session);

        try {

            message.setFrom(new InternetAddress(userEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new ServiceException(e);
        }

    }

    private Properties loadProperties() throws LoadPropertiesException {

        Properties properties = new Properties();
        try {
            InputStream in = EmailServiceImpl.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);

            userEmail = properties.getProperty("user.email");
            password = properties.getProperty("user.password");

            return properties;
        } catch (IOException e) {
            throw new LoadPropertiesException();
        }

    }

}
