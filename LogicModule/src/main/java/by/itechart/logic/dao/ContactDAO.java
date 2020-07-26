package by.itechart.logic.dao;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;

import java.util.List;

public interface ContactDAO {

    List<Contact> findAllWithLimit(int page, int pageLimit) throws DaoException;

    long save(Contact contact) throws DaoException;

    void update(Contact contact) throws DaoException;

    void deleteAllById(List<Long> idList) throws DaoException;

    long countAll() throws DaoException;

    Contact findById(long contactId) throws DaoException;

}
