package by.itechart.logic.dao;

import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface AttachmentDAO {

    void save(List<Attachment> attachments, Connection connection) throws DaoException;

    void deleteAll(long contactId, Connection connection) throws DaoException;

}
