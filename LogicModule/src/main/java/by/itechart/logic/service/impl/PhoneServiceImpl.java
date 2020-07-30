package by.itechart.logic.service.impl;

import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.dao.impl.PhoneDAOImpl;
import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.DaoException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.PhoneService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class PhoneServiceImpl implements PhoneService {

    private PhoneDAO phoneDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneServiceImpl.class);

    public PhoneServiceImpl(Connection connection) {
        phoneDao = new PhoneDAOImpl(connection);
    }


    @Override
    public List<Phone> findByContactId(long contactId) throws ServiceException {

        try {
            return phoneDao.findByContactId(contactId);
        } catch (DaoException e) {
            LOGGER.error("Finding phones was failed !", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void saveAll(List<Phone> phoneList) throws ServiceException {

        for (Phone phone : phoneList) {
            try {
                phoneDao.save(phone);
            } catch (DaoException e) {
                LOGGER.error("Saving phone list was failed {} !", phoneList, e);
                throw new ServiceException(e);
            }
        }

        LOGGER.info("Phone list has been saved {}", phoneList);

    }

    @Override
    public void deleteByContactId(long contactId) throws ServiceException {

        try {
            phoneDao.deleteByContactId(contactId);
        } catch (DaoException e) {
            LOGGER.error("Deleting contacts by id={} was failed", contactId, e);
            throw new ServiceException(e);
        }

        LOGGER.info("Phones has been deleted by contact id= {}", contactId);
    }

}
