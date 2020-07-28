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
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.util.FileManagerUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactServiceImpl implements ContactService {

    private Connection connection;

    private ContactDAO contactDAO;
    private PhoneDAO phoneDAO;
    private AttachmentDAO attachmentDAO;

    private static final String IMG_PREFIX = "Avatar_";
    private static final String IMG_FIND_PREFIX = "Avatar";
    private static final String ATTACHMENT_POSTFIX = "_";

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
        final Map<Long, byte[]> attachments = contactDTO.getAttachments();

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
                phoneDAO.saveAll(phoneList);

                final List<Attachment> attachmentList = contact.getAttachmentList();
                attachmentList.forEach(e -> e.setContactId(contactId));
                final List<Long> idAttachments = attachmentDAO.saveAll(attachmentList);

                final String directory = FileManagerUtil.createDirectory(contactId);

                if (avatar != null) {
                    final String imgPath = FileManagerUtil.saveImg(avatar, directory, contact.getImageName(), IMG_PREFIX);
                    logger.info("Image has been saved on the disk: " + imgPath);
                }


                int index = 0;
                for (Map.Entry<Long, byte[]> entry : attachments.entrySet()) {
                    final String fileName = contact.getAttachmentList().get(index).getFileName();
                    final String id = String.valueOf(idAttachments.get(index));
                    final String attPath = FileManagerUtil.saveFile(entry.getValue(), directory, id, fileName, ATTACHMENT_POSTFIX);
                    index++;
                    logger.info("Attachment has been saved on the disk: " + attPath);
                }


                logger.info("Full contact has been saved: " + contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {
                logger.error("Save contact exception", e);
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


        final Contact contact = contactDTO.getContact();
        final long contactId = contact.getId();

        final byte[] avatar = contactDTO.getAvatar();
        final Map<Long, byte[]> attachmentsMap = contactDTO.getAttachments();

        try (Connection con = connection) {

            try {
                connection.setAutoCommit(false);

                contactDAO.update(contact);

                phoneDAO.deleteAllByContactId(contactId);
                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneDAO.saveAll(phoneList);


                final List<Attachment> attachmentsDb = attachmentDAO.findAllByContactId(contactId);
                final List<Attachment> attachmentList = contact.getAttachmentList();
                final List<Long> attachmentListId = attachmentList.stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList());

                final List<Long> deleteId = attachmentsDb.stream()
                        .filter(e -> e.getId() > 0 && !attachmentListId.contains(e.getId()))
                        .map(Attachment::getId)
                        .collect(Collectors.toList());


                final List<Attachment> updateAttachment = attachmentList.stream()
                        .filter(e -> e.getId() > 0)
                        .peek(e -> e.setContactId(contactId))
                        .collect(Collectors.toList());


                final List<Attachment> insertAttachment = attachmentList.stream()
                        .filter(e -> e.getId() < 0)
                        .peek(e -> e.setContactId(contactId))
                        .collect(Collectors.toList());


                attachmentDAO.deleteAllById(deleteId);
                attachmentDAO.updateAll(updateAttachment);
                final List<Long> saveIdList = attachmentDAO.saveAll(insertAttachment);


                if (avatar != null) {
                    String pathToAvatar = FileManagerUtil.findPathToFile(contactId, IMG_FIND_PREFIX);
                    if (pathToAvatar != null) {
                        FileManagerUtil.deleteFile(pathToAvatar);
                    }
                    String pathToDirectory = FileManagerUtil.findDirectory(contactId);
                    final String imgPath = FileManagerUtil.saveImg(avatar, pathToDirectory, contact.getImageName(), IMG_PREFIX);
                    logger.info("Image has been saved on the disk: " + imgPath);

                }

                for (Long id : deleteId) {
                    String pathToFile = FileManagerUtil.findPathToFile(contactId, String.valueOf(id));
                    if (pathToFile != null) {
                        FileManagerUtil.deleteFile(pathToFile);
                        logger.info("Attachment has been removed from the disk: " + pathToFile);
                    }
                }


                for (Attachment attachment : updateAttachment) {
                    final Long id = attachment.getId();
                    if (attachmentsMap.containsKey(id)) {
                        String pathToFile = FileManagerUtil.findPathToFile(contactId, String.valueOf(id));
                        FileManagerUtil.deleteFile(pathToFile);
                        final String directory = FileManagerUtil.findDirectory(contactId);
                        final String newFile = FileManagerUtil.saveFile(attachmentsMap.get(id), directory, String.valueOf(id), attachment.getFileName(), ATTACHMENT_POSTFIX);
                        logger.info("Attachment file has been updated " + newFile);
                    }
                }

                int index = 0;
                for (Attachment attachment : insertAttachment) {
                    final Long id = saveIdList.get(index);
                    final String directory = FileManagerUtil.findDirectory(contactId);
                    final String newFile = FileManagerUtil.saveFile(attachmentsMap.get(attachment.getId()), directory, String.valueOf(id), attachment.getFileName(), ATTACHMENT_POSTFIX);
                    index++;
                    logger.info("Attachment file has been saved " + newFile);
                }

                logger.info("Full contact has been updated " + contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {
                logger.error("Update contact exception", e);
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

                for (Long id: contactList) {
                    FileManagerUtil.deleteDirectory(id);
                }

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
                final List<Attachment> attachmentList = attachmentDAO.findAllByContactId(id);

                contact.setPhoneList(phoneList);
                contact.setAttachmentList(attachmentList);

                final String pathToFile = FileManagerUtil.findPathToFile(id, IMG_FIND_PREFIX);

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
