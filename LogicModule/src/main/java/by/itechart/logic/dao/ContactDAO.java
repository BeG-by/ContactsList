package by.itechart.logic.dao;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface ContactDAO {

    List<Contact> findAllWithLimit(int page, int pageLimit) throws DaoException;

    long save(Contact contact, Connection connection) throws DaoException;

    void update(Contact contact, Connection connection) throws DaoException;

    void deleteAllById(List<Long> idList) throws DaoException;

    long countAll() throws DaoException;

}
