package by.itechart.logic.service;

import by.itechart.logic.exception.LoadPropertiesException;
import by.itechart.logic.exception.ServiceException;

public interface EmailService {

    void sendEmail(String to, String subject, String text) throws ServiceException, LoadPropertiesException;

}
