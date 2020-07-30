package by.itechart.logic.service;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;

import java.util.List;

public interface ContactService {

    List<Contact> findAllWithLimit(int page, int pageLimit) throws ServiceException;

    long save(Contact contact) throws ServiceException;

    void update(Contact contact) throws ServiceException;

    void deleteAll(List<Long> contactListId) throws ServiceException;

    long countAll() throws ServiceException;

    Contact findById(long id) throws ServiceException;

    Contact findByEmail(String email) throws ServiceException;

}
