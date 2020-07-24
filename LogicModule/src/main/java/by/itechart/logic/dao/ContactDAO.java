package by.itechart.logic.dao;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface ContactDAO {

    List<Contact> findAll(int page, int pageLimit);

    long save(Contact contact , Connection connection) throws DaoException;

    void deleteAll(List<Long> idList);

    long countAll();

}
