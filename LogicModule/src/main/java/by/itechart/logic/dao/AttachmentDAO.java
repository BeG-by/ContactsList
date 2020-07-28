package by.itechart.logic.dao;

import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;

import java.util.List;

public interface AttachmentDAO {

    List<Long> saveAll(List<Attachment> attachments) throws DaoException;

    void deleteAllById(List<Long> attachmentIdList) throws DaoException;

    void updateAll(List<Attachment> attachments) throws DaoException;

    List<Attachment> findAllByContactId(long contactId) throws DaoException;


}
