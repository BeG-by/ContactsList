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

import static by.itechart.logic.dao.impl.ContactDAOImpl.*;


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

        StringBuilder searchQuery = new StringBuilder(String.format("SELECT * FROM contact c LEFT JOIN address a ON c.%s = a.%s WHERE ",
                ID_COL, CONTACT_ID_COL));

        String searchReady = makeSqlRequest(searchQuery, searchRequest);
        searchReady += String.format(" ORDER BY c.id LIMIT %d , %d;", (page - 1) * pageLimit, pageLimit);

        try {
            return contactDAO.findAllWithFilter(searchReady);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public long countAllWithFilter(SearchRequest searchRequest) throws ServiceException {

        StringBuilder searchQuery = new StringBuilder(String.format("SELECT count(*) FROM contact c LEFT JOIN address a ON c.%s = a.%s WHERE ",
                ID_COL, CONTACT_ID_COL));

        try {
            return contactDAO.countAllWithFilter(makeSqlRequest(searchQuery, searchRequest));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }


    private String makeSqlRequest(StringBuilder searchQuery, SearchRequest searchRequest) {

        final int length = searchQuery.length();

        if (!searchRequest.getFirstName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", FIRST_NAME_COL, searchRequest.getFirstName()));
        }

        if (!searchRequest.getLastName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", LAST_NAME_COL, searchRequest.getLastName()));
        }

        if (!searchRequest.getMiddleName().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", MIDDLE_NAME_COL, searchRequest.getMiddleName()));
        }

        if (!searchRequest.getDateBefore().isEmpty()) {
            searchQuery.append(String.format("%s < '%s' AND ", BIRTHDAY_COL, searchRequest.getDateBefore()));
        }

        if (!searchRequest.getDateAfter().isEmpty()) {
            searchQuery.append(String.format("%s > '%s' AND ", BIRTHDAY_COL, searchRequest.getDateAfter()));
        }

        if (!searchRequest.getSex().isEmpty()) {
            searchQuery.append(String.format("%s = '%s' AND ", SEX_COL, searchRequest.getSex()));
        }

        if (!searchRequest.getMaritalStatus().isEmpty()) {
            searchQuery.append(String.format("%s = '%s' AND ", MARITAL_STATUS_COL, searchRequest.getMaritalStatus()));
        }

        if (!searchRequest.getNationality().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", NATIONALITY_COL, searchRequest.getNationality()));
        }

        if (!searchRequest.getCountry().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", COUNTRY_COL, searchRequest.getCountry()));
        }

        if (!searchRequest.getCity().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", CITY_COL, searchRequest.getCity()));
        }

        if (!searchRequest.getStreet().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", STREET_COL, searchRequest.getStreet()));
        }

        if (!searchRequest.getStreet().isEmpty()) {
            searchQuery.append(String.format("%s LIKE '%%%s%%' AND ", POST_INDEX_COL, searchRequest.getPostIndex()));
        }

        String searchReady;

        if (searchQuery.length() == length) {
            searchReady = searchQuery.substring(0, searchQuery.length() - 7);
        } else {
            searchReady = searchQuery.substring(0, searchQuery.length() - 5);
        }

        return searchReady;

    }

}
