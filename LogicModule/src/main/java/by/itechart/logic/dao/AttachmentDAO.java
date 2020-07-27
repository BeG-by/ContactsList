package by.itechart.logic.dao;

import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;

import java.util.List;

public interface AttachmentDAO {

    List<Long> save(List<Attachment> attachments) throws DaoException;

    void deleteAll(long contactId) throws DaoException;

    List<Attachment> findByContactId(long contactId) throws DaoException;

}
