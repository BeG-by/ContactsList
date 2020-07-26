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
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.util.FileManagerUtil;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService {

    private Connection connection;

    private ContactDAO contactDAO;
    private PhoneDAO phoneDAO;
    private AttachmentDAO attachmentDAO;

    private final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    private static final String JSON_CONTACT_FIELD_NAME = "jsonContact";
    private static final String AVATAR_FIELD_NAME = "avatar";
    private static final String ATTACHMENT_FIELD_NAME = "attachment";

    public ContactServiceImpl() {
    }

    public ContactServiceImpl(Connection connection) {
        this.connection = connection;
        contactDAO = new ContactDAOImpl(connection);
        phoneDAO = new PhoneDAOImpl(connection);
        attachmentDAO = new AttachmentDAOImpl(connection);
    }

    @Override
    public ContactDTO parseMultipartRequest(HttpServletRequest req) throws ServiceException {

        try {
            List<FileItem> items = FileManagerUtil.createServletFileUpload(req).parseRequest(req);

            Contact contact = null;
            FileItem avatar = null;
            List<FileItem> attachments = new ArrayList<>();

            for (FileItem item : items) {
                if (item != null) {

                    final String fieldName = item.getFieldName();

                    if (fieldName.startsWith(JSON_CONTACT_FIELD_NAME)) {
                        contact = gson.fromJson(new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8), Contact.class);
                    } else if (fieldName.startsWith(AVATAR_FIELD_NAME)) {
                        avatar = item;
                    } else if (fieldName.startsWith(ATTACHMENT_FIELD_NAME)) {
                        attachments.add(item);
                    }
                }
            }

            if (contact != null && avatar != null) {
                contact.setImageName(avatar.getName());
            }

            logger.info("Contact has been parsed: " + contact);

            return new ContactDTO(contact, avatar, attachments);

        } catch (Exception e) {
            logger.error("Parse multipart request failed ! " + e.getMessage());
            throw new ServiceException("Incorrect contact data !", e);
        }
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
    public void saveOne(ContactDTO contactDTO) throws ServiceException {

        final FileItem avatar = contactDTO.getAvatar();
        final List<FileItem> attachments = contactDTO.getAttachments();

        try (Connection con = connection) {

            try {
                connection.setAutoCommit(false);

                final Contact contact = contactDTO.getContact();
                final long contactId = contactDAO.save(contact);

                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneDAO.save(phoneList);

                final List<Attachment> attachmentList = contact.getAttachmentList();
                attachmentList.forEach(e -> e.setContactId(contactId));
                attachmentDAO.save(attachmentList);

                final String directory = FileManagerUtil.createDirectory(contact.getFirstName(), contact.getLastName(), contactId);

                if (avatar != null) {
                    final String imgPath = FileManagerUtil.saveFile(avatar, directory, AVATAR_FIELD_NAME);
                    logger.info("Image has been saved on the disk: " + imgPath);
                }

                for (FileItem item : attachments) {
                    final String attPath = FileManagerUtil.saveFile(item, directory, ATTACHMENT_FIELD_NAME);
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

        } catch (SQLException e) {
            logger.error("Connection failed !", e);
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void updateOne(ContactDTO contactDTO) throws ServiceException {

        final FileItem avatar = contactDTO.getAvatar();
        final List<FileItem> attachments = contactDTO.getAttachments();

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

                String directory = FileManagerUtil.findDirectoryPath(contactId);

                if (directory == null) {
                    directory = FileManagerUtil.createDirectory(contact.getFirstName(), contact.getLastName(), contactId);
                } else {
                    FileManagerUtil.cleanDirectory(directory);
                }

                if (avatar != null) {
                    final String imgPath = FileManagerUtil.saveFile(avatar, directory, AVATAR_FIELD_NAME);
                    logger.info("Image has been saved on the disk: " + imgPath);
                }

                for (FileItem item : attachments) {
                    final String attPath = FileManagerUtil.saveFile(item, directory, ATTACHMENT_FIELD_NAME);
                    logger.info("Attachment has been saved on the disk: " + attPath);
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
                final List<Phone> phoneList = phoneDAO.findByContactId(id);
                final List<Attachment> attachmentList = attachmentDAO.findByContactId(id);

                contact.setPhoneList(phoneList);
                contact.setAttachmentList(attachmentList);

                con.commit();
                con.setAutoCommit(true);
                return contact;

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

}
