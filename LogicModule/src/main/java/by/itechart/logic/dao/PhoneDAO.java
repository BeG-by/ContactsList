package by.itechart.logic.dao;

import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.DaoException;

import java.util.List;

public interface PhoneDAO {

    void save(List<Phone> phone) throws DaoException;

    void deleteAll(long contactId) throws DaoException;

    List<Phone> findByContactId(long contactId) throws DaoException;

}
