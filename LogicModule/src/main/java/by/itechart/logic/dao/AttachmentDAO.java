package by.itechart.logic.dao;

import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;

import java.util.List;

public interface AttachmentDAO {

    Long save(Attachment attachment) throws DaoException;

    void delete(long attachmentId) throws DaoException;

    void update(Attachment attachment) throws DaoException;

    List<Attachment> findAllByContactId(long contactId) throws DaoException;


}
