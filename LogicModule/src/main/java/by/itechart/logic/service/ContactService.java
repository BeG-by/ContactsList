package by.itechart.logic.service;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface ContactService {

    ContactDTO parseMultipartRequest(HttpServletRequest req) throws ServiceException;

    long saveContact(ContactDTO contactDTO) throws ServiceException;

}
