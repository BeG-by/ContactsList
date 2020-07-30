package by.itechart.logic.service.impl;

import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
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

}
