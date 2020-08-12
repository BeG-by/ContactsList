package by.itechart.logic.service;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.dto.MessageRequest;
import by.itechart.logic.dto.SearchRequest;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.EmailAlreadyExistException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.exception.TemplateNotFoundException;

import java.util.List;
import java.util.Map;

public interface FacadeService {

    List<Contact> findAllWithPagination(int page, int pageLimit) throws ServiceException;

    void saveFullContact(ContactDTO contactDTO) throws ServiceException, EmailAlreadyExistException;

    void updateFullContact(ContactDTO contactDTO) throws ServiceException, EmailAlreadyExistException;

    void deleteContacts(List<Long> contactIdList) throws ServiceException;

    long countAllContacts() throws ServiceException;

    Contact findContactById(long contactId) throws ServiceException;

    Contact findByEmail(String email) throws ServiceException;

    void sendMessagesViaEmail(MessageRequest message) throws ServiceException, TemplateNotFoundException;

    Map<String, String> findAllTemplates() throws ServiceException;

    List<Contact> searchContactWithFilter(SearchRequest searchRequest, int page, int pageLimit) throws ServiceException;

    long countAllContactsWithFilter(SearchRequest searchRequest) throws ServiceException;

    String findAttachmentFile(long contactId, long attachmentId) throws ServiceException;

}
