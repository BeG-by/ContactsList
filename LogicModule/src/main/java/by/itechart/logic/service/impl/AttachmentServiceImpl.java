package by.itechart.logic.service.impl;

import by.itechart.logic.dao.AttachmentDAO;
import by.itechart.logic.dao.impl.AttachmentDAOImpl;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentDAO attachmentDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    public AttachmentServiceImpl() {
    }

    public AttachmentServiceImpl(Connection connection) {
        attachmentDAO = new AttachmentDAOImpl(connection);
    }

    @Override
    public List<Long> saveAll(List<Attachment> attachmentsList) throws ServiceException {

        final ArrayList<Long> idList = new ArrayList<>();

        for (Attachment attachment : attachmentsList) {
            try {
                idList.add(attachmentDAO.save(attachment));
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.info("Attachment list has been saved {}", attachmentsList);

        return idList;
    }

    @Override
    public List<Attachment> findByContactId(long contactId) throws ServiceException {

        try {
            return attachmentDAO.findAllByContactId(contactId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteAllById(List<Long> attachmentIdList) throws ServiceException {

        for (Long id : attachmentIdList) {
            try {
                attachmentDAO.delete(id);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.info("Attachment list by id has been deleted {}", attachmentIdList);

    }

    @Override
    public void updateAll(List<Attachment> attachmentList) throws ServiceException {

        for (Attachment attachment : attachmentList) {
            try {
                attachmentDAO.update(attachment);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.info("Attachment list by id has been updated {}", attachmentList);
    }

}
