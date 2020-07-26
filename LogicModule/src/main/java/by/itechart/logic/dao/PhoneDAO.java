package by.itechart.logic.dao;

import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface PhoneDAO {

    void save(List<Phone> phone, Connection connection) throws DaoException;

    void deleteAll(long contactId, Connection connection) throws DaoException;

}
