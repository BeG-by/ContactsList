package by.itechart.logic.service;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.AlreadyExistException;
import by.itechart.logic.exception.ServiceException;

import java.util.List;

public interface ContactService {


    List<Contact> findAllWithLimit(int page, int pageLimit) throws ServiceException;

    void saveOne(ContactDTO contactDTO) throws ServiceException, AlreadyExistException;

    void updateOne(ContactDTO contactDTO) throws ServiceException;

    void deleteAllById(List<Long> contactId) throws ServiceException;

    long countAll() throws ServiceException;

    Contact findById(long id) throws ServiceException;

}
