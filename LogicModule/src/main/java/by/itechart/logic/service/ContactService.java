package by.itechart.logic.service;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface ContactService {

    Contact parseMultipartRequest(HttpServletRequest req) throws ServiceException;

}
