package by.itechart.logic.service;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ContactService {

    ContactDTO parseMultipartRequest(HttpServletRequest req) throws ServiceException;

    List<Contact> findAllWithLimit(int page, int pageLimit) throws ServiceException;

    void saveOne(ContactDTO contactDTO) throws ServiceException;

    void updateOne(ContactDTO contactDTO) throws ServiceException;

    void deleteAllById(List<Long> contactId) throws ServiceException;

    long countAll() throws ServiceException;

}
