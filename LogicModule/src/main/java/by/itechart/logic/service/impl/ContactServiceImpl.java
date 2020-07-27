package by.itechart.logic.service.impl;

import by.itechart.logic.dao.AttachmentDAO;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.dao.impl.AttachmentDAOImpl;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.dao.impl.PhoneDAOImpl;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.AlreadyExistException;
import by.itechart.logic.exception.DaoException;
import by.itechart.logic.exception.NotFoundException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.util.FileManagerUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContactServiceImpl implements ContactService {

    private Connection connection;

    private ContactDAO contactDAO;
    private PhoneDAO phoneDAO;
    private AttachmentDAO attachmentDAO;

    private static final String IMG_PREFIX = "Avatar___";
    private static final String ATTACHMENT_PREFIX = "_Attachment___";

    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);


    public ContactServiceImpl() {
    }

    public ContactServiceImpl(Connection connection) {
        this.connection = connection;
        contactDAO = new ContactDAOImpl(connection);
        phoneDAO = new PhoneDAOImpl(connection);
        attachmentDAO = new AttachmentDAOImpl(connection);
    }


    @Override
    public List<Contact> findAllWithLimit(int page, int pageLimit) throws ServiceException {

        try (Connection con = connection) {
            return contactDAO.findAllWithLimit(page, pageLimit);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void saveOne(ContactDTO contactDTO) throws ServiceException, AlreadyExistException {

        final Contact contact = contactDTO.getContact();
        final byte[] avatar = contactDTO.getAvatar();
        final List<byte[]> attachments = contactDTO.getAttachments();

        try (Connection con = connection) {

            final Contact contactByEmail = contactDAO.findByEmail(contact.getEmail());
            if (contactByEmail != null) {
                throw new AlreadyExistException("Email already exists !");
            }

            try {

                connection.setAutoCommit(false);

                final long contactId = contactDAO.save(contact);

                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneDAO.save(phoneList);

                final List<Attachment> attachmentList = contact.getAttachmentList();
                attachmentList.forEach(e -> e.setContactId(contactId));
                final List<Long> idAttachments = attachmentDAO.save(attachmentList);

                final String directory = FileManagerUtil.createDirectory(contactId);

                if (avatar != null) {
                    final String imgPath = FileManagerUtil.saveImg(avatar, directory, contact.getImageName(), IMG_PREFIX);
                    logger.info("Image has been saved on the disk: " + imgPath);
                }

                int index = 0;
                for (byte[] item : attachments) {
                    final String fileName = contact.getAttachmentList().get(0).getFileName();
                    final String id = String.valueOf(idAttachments.get(index));
                    final String attPath = FileManagerUtil.saveFile(item, directory, id, fileName, ATTACHMENT_PREFIX);
                    index++;
                    logger.info("Attachment has been saved on the disk: " + attPath);
                }

                logger.info("Full contact has been saved: " + contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {
                logger.error("Save contact exception : " + e.getMessage());
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                throw new ServiceException(e.getMessage());
            }

        } catch (SQLException | DaoException e) {
            logger.error(e);
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void updateOne(ContactDTO contactDTO) throws ServiceException {

        final byte[] avatar = contactDTO.getAvatar();
        final List<byte[]> attachments = contactDTO.getAttachments();

        try (Connection con = connection) {

            try {
                connection.setAutoCommit(false);

                final Contact contact = contactDTO.getContact();
                final long contactId = contact.getId();
                contactDAO.update(contact);

                phoneDAO.deleteAll(contactId);
                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneDAO.save(phoneList);

                attachmentDAO.deleteAll(contactId);
                final List<Attachment> attachmentList = contact.getAttachmentList();
                attachmentList.forEach(e -> e.setContactId(contactId));
                attachmentDAO.save(attachmentList);

                String directory = FileManagerUtil.findDPathToFile(contactId, ATTACHMENT_PREFIX);

                if (directory == null) {
                    directory = FileManagerUtil.createDirectory(contactId);
                } else {
                    FileManagerUtil.cleanDirectory(directory);
                }

                if (avatar != null) {
//                    final String imgPath = FileManagerUtil.saveFile(avatar, directory, "AVATAR");
//                    logger.info("Image has been saved on the disk: " + imgPath);
                }

                for (byte[] bytes : attachments) {
//                    final String attPath = FileManagerUtil.saveFile(bytes, directory, "ATTACHMENT");
//                    logger.info("Attachment has been saved on the disk: " + attPath);
                }

                logger.info("Full contact has been updated " + contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {
                logger.error("Update contact exception : " + e.getMessage());
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                throw new ServiceException(e.getMessage());
            }

        } catch (SQLException e) {
            logger.error("Connection failed !", e);
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void deleteAllById(List<Long> contactList) throws ServiceException {

        try (Connection con = connection) {

            try {
                con.setAutoCommit(false);
                contactDAO.deleteAllById(contactList);
                con.commit();
                con.setAutoCommit(true);

            } catch (Exception e) {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw new ServiceException(e.getMessage());
            }

        } catch (Exception e) {
            logger.error(e);
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public long countAll() throws ServiceException {

        try (Connection con = connection) {
            return contactDAO.countAll();
        } catch (Exception e) {
            logger.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Contact findById(long id) throws ServiceException {

        try (Connection con = connection) {

            try {
                con.setAutoCommit(false);

                final Contact contact = contactDAO.findById(id);

                if (contact == null) {
                    return null;
                }

                final List<Phone> phoneList = phoneDAO.findByContactId(id);
                final List<Attachment> attachmentList = attachmentDAO.findByContactId(id);

                contact.setPhoneList(phoneList);
                contact.setAttachmentList(attachmentList);

                final String pathToFile = FileManagerUtil.findDPathToFile(id, IMG_PREFIX);

                if (pathToFile != null) {
                    contact.setImageName(FileManagerUtil.FileToBase64(pathToFile));
                }

                con.commit();
                con.setAutoCommit(true);
                return contact;

            } catch (Exception e) {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                logger.error("Finding contact failed !", e);
                throw new ServiceException(e.getMessage());
            }

        } catch (Exception e) {
            logger.error("Finding contact failed !", e);
            throw new ServiceException(e.getMessage());
        }

    }

}
