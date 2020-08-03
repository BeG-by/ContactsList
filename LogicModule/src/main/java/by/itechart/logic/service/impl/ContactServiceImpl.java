package by.itechart.logic.service.impl;

import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.dto.SearchRequest;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;


public class ContactServiceImpl implements ContactService {


    private ContactDAO contactDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    public ContactServiceImpl(Connection connection) {
        contactDAO = new ContactDAOImpl(connection);
    }

    @Override
    public List<Contact> findAllWithLimit(int page, int pageLimit) throws ServiceException {

        try {
            return contactDAO.findAllWithLimit(page, pageLimit);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long save(Contact contact) throws ServiceException {

        try {
            final long id = contactDAO.save(contact);
            contact.setId(id);
            LOGGER.info("Contact has been saved {}", contact);
            return id;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void update(Contact contact) throws ServiceException {

        try {
            contactDAO.update(contact);
            LOGGER.info("Contact has been updated {}", contact);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteAll(List<Long> contactListId) throws ServiceException {

        for (Long id : contactListId) {
            try {
                contactDAO.delete(id);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.info("Contact list has been deleted {}", contactListId);

    }

    @Override
    public long countAll() throws ServiceException {

        try {
            return contactDAO.countAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Contact findById(long id) throws ServiceException {

        try {
            return contactDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Contact findByEmail(String email) throws ServiceException {

        try {
            return contactDAO.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Contact> findByTodayDate(LocalDate birthday) throws ServiceException {

        try {
            return contactDAO.findByTodayDate(birthday);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Contact> searchContact(SearchRequest searchRequest, int page, int pageLimit) throws ServiceException {

        StringBuilder searchQuery = new StringBuilder("SELECT * FROM contact WHERE ");

        System.out.println(searchRequest);

        if (!searchRequest.getFirstName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.FIRST_NAME_COL, searchRequest.getFirstName()));
        }

        if (!searchRequest.getLastName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.LAST_NAME_COL, searchRequest.getLastName()));
        }

        if (!searchRequest.getMiddleName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.MIDDLE_NAME_COL, searchRequest.getMiddleName()));
        }

        if (!searchRequest.getDateBefore().isEmpty()) {
            searchQuery.append(String.format("%s < '%s' AND ", ContactDAOImpl.BIRTHDAY_COL, searchRequest.getDateBefore()));
        }

        if (!searchRequest.getDateAfter().isEmpty()) {
            searchQuery.append(String.format("%s > '%s' AND ", ContactDAOImpl.BIRTHDAY_COL, searchRequest.getDateAfter()));
        }

        if (!searchRequest.getSex().isEmpty()) {
            searchQuery.append(String.format("%s = '%s' AND ", ContactDAOImpl.SEX_COL, searchRequest.getSex()));
        }

        if (!searchRequest.getMaritalStatus().isEmpty()) {
            searchQuery.append(String.format("%s = '%s' AND ", ContactDAOImpl.MARITAL_STATUS_COL, searchRequest.getMaritalStatus()));
        }

        if (!searchRequest.getNationality().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.NATIONALITY_COL, searchRequest.getNationality()));
        }

        if (!searchRequest.getCountry().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.COUNTRY_COL, searchRequest.getCountry()));
        }

        if (!searchRequest.getCity().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.CITY_COL, searchRequest.getCity()));
        }

        if (!searchRequest.getStreet().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.STREET_COL, searchRequest.getStreet()));
        }

        if (!searchRequest.getStreet().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", ContactDAOImpl.POST_INDEX_COL, searchRequest.getPostIndex()));
        }


        System.out.println(searchQuery);
        String searchReady = searchQuery.substring(0, searchQuery.length() - 5);
        System.out.println(searchReady);

        return null;

    }

}
