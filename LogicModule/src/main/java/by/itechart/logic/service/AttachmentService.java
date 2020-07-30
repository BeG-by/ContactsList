package by.itechart.logic.service;

import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;
import by.itechart.logic.exception.ServiceException;

import java.util.List;

public interface AttachmentService {

    List<Long> saveAll(List<Attachment> attachmentsList) throws ServiceException;

    List<Attachment> findByContactId(long contactId) throws ServiceException;

    void deleteAllById(List<Long> attachmentIdList) throws ServiceException;

    void updateAll(List<Attachment> attachmentList) throws ServiceException;
}
