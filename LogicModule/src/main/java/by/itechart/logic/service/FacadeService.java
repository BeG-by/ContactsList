package by.itechart.logic.service;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.dto.MessageRequest;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.AlreadyExistException;
import by.itechart.logic.exception.ServiceException;

import java.util.List;

public interface FacadeService {

    List<Contact> findAllWithPagination(int page, int pageLimit) throws ServiceException;

    void saveFullContact(ContactDTO contactDTO) throws ServiceException, AlreadyExistException;

    void updateFullContact(ContactDTO contactDTO) throws ServiceException;

    void deleteContacts(List<Long> contactIdList) throws ServiceException;

    long countAllContacts() throws ServiceException;

    Contact findContactById(long contactId) throws ServiceException;

    Contact findByEmail(String email) throws ServiceException;

    void sendMessagesViaEmail(MessageRequest message) throws ServiceException;

}
