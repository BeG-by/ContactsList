package by.itechart.logic.dao;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface ContactDAO {

    List<Contact> findAllWithLimit(int page, int pageLimit) throws DaoException;

    long save(Contact contact) throws DaoException;

    void update(Contact contact) throws DaoException;

    void delete(long contactId) throws DaoException;

    long countAll() throws DaoException;

    Contact findById(long contactId) throws DaoException;

    Contact findByEmail(String email) throws DaoException;

    List<Contact> findByTodayDate(LocalDate date) throws DaoException;

    List<Contact> findAllWithFilter(String searchSql) throws DaoException;

    long countAllWithFilter(String sql) throws DaoException;

}
