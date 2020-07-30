package by.itechart.logic.service.impl;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.dto.MessageRequest;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.AlreadyExistException;
import by.itechart.logic.exception.LoadPropertiesException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.*;
import by.itechart.logic.service.util.FileManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FacadeServiceImpl implements FacadeService {

    private ContactService contactService;
    private PhoneService phoneService;
    private AttachmentService attachmentService;
    private EmailService emailService;

    private static final String IMG_PREFIX = "Avatar_";
    private static final String IMG_FIND_PREFIX = "Avatar";
    private static final String ATTACHMENT_POSTFIX = "_";

    private static final Logger LOGGER = LoggerFactory.getLogger(FacadeServiceImpl.class);

    @Override
    public List<Contact> findAllWithPagination(int page, int pageLimit) throws ServiceException {

        try (Connection connection = ConnectionFactory.createConnection()) {
            contactService = new ContactServiceImpl(connection);
            return contactService.findAllWithLimit(page, pageLimit);
        } catch (Exception e) {
            LOGGER.error("Finding contact was failed", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void saveFullContact(ContactDTO contactDTO) throws ServiceException, AlreadyExistException {

        final Contact contact = contactDTO.getContact();
        final byte[] avatar = contactDTO.getAvatar();
        final Map<Long, byte[]> attachments = contactDTO.getAttachments();

        try (Connection connection = ConnectionFactory.createConnection()) {

            contactService = new ContactServiceImpl(connection);
            phoneService = new PhoneServiceImpl(connection);
            attachmentService = new AttachmentServiceImpl(connection);

            final Contact contactByEmail = contactService.findByEmail(contact.getEmail());

            if (contactByEmail != null) {
                throw new AlreadyExistException("Email already exists !");
            }

            try {
                connection.setAutoCommit(false);

                final long contactId = contactService.save(contact);

                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneService.saveAll(phoneList);

                final List<Attachment> attachmentList = contact.getAttachmentList();
                attachmentList.forEach(e -> e.setContactId(contactId));
                final List<Long> idAttachments = attachmentService.saveAll(attachmentList);

                final String directory = FileManagerUtil.createDirectory(contactId);

                if (avatar != null) {
                    final String imgPath = FileManagerUtil.saveImg(avatar, directory, contact.getImageName(), IMG_PREFIX);
                    LOGGER.info("Image has been saved on the disk: {}", imgPath);
                }


                int index = 0;
                for (Map.Entry<Long, byte[]> entry : attachments.entrySet()) {
                    final String fileName = contact.getAttachmentList().get(index).getFileName();
                    final String id = String.valueOf(idAttachments.get(index));
                    final String attPath = FileManagerUtil.saveFile(entry.getValue(), directory, id, fileName, ATTACHMENT_POSTFIX);
                    index++;
                    LOGGER.info("Attachment has been saved on the disk: {}", attPath);
                }


                LOGGER.info("Full contact has been saved: {}", contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {

                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw new ServiceException(e);
            }

        } catch (SQLException | LoadPropertiesException | ServiceException e) {
            LOGGER.error("Full contact saving was failed", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void updateFullContact(ContactDTO contactDTO) throws ServiceException {

        final Contact contact = contactDTO.getContact();
        final long contactId = contact.getId();

        final byte[] avatar = contactDTO.getAvatar();
        final Map<Long, byte[]> attachmentsMap = contactDTO.getAttachments();

        try (Connection connection = ConnectionFactory.createConnection()) {

            contactService = new ContactServiceImpl(connection);
            phoneService = new PhoneServiceImpl(connection);
            attachmentService = new AttachmentServiceImpl(connection);

            try {
                connection.setAutoCommit(false);

                contactService.update(contact);

                phoneService.deleteByContactId(contactId);
                final List<Phone> phoneList = contact.getPhoneList();
                phoneList.forEach(e -> e.setContactId(contactId));
                phoneService.saveAll(phoneList);


                final List<Attachment> attachmentsDb = attachmentService.findByContactId(contactId);
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


                attachmentService.deleteAllById(deleteId);
                attachmentService.updateAll(updateAttachment);
                final List<Long> saveIdList = attachmentService.saveAll(insertAttachment);


                if (avatar != null) {
                    String pathToAvatar = FileManagerUtil.findPathToFile(contactId, IMG_FIND_PREFIX);
                    if (pathToAvatar != null) {
                        FileManagerUtil.deleteFile(pathToAvatar);
                    }
                    String pathToDirectory = FileManagerUtil.findDirectory(contactId);

                    if (pathToDirectory == null) {
                        pathToDirectory = FileManagerUtil.createDirectory(contactId);
                    }

                    final String imgPath = FileManagerUtil.saveImg(avatar, pathToDirectory, contact.getImageName(), IMG_PREFIX);
                    LOGGER.info("Image has been saved on the disk: {}", imgPath);

                }

                for (Long id : deleteId) {
                    String pathToFile = FileManagerUtil.findPathToFile(contactId, String.valueOf(id));
                    if (pathToFile != null) {
                        FileManagerUtil.deleteFile(pathToFile);
                        LOGGER.info("Attachment has been removed from the disk: {}", pathToFile);
                    }
                }


                for (Attachment attachment : updateAttachment) {
                    final Long id = attachment.getId();
                    if (attachmentsMap.containsKey(id)) {
                        String pathToFile = FileManagerUtil.findPathToFile(contactId, String.valueOf(id));
                        FileManagerUtil.deleteFile(pathToFile);
                        final String directory = FileManagerUtil.findDirectory(contactId);
                        final String newFile = FileManagerUtil.saveFile(attachmentsMap.get(id), directory, String.valueOf(id), attachment.getFileName(), ATTACHMENT_POSTFIX);
                        LOGGER.info("Attachment file has been updated: {}", newFile);
                    }
                }

                int index = 0;
                for (Attachment attachment : insertAttachment) {
                    final Long id = saveIdList.get(index);
                    String directory = FileManagerUtil.findDirectory(contactId);
                    if (directory == null) {
                        directory = FileManagerUtil.createDirectory(contactId);
                    }
                    final String newFile = FileManagerUtil.saveFile(attachmentsMap.get(attachment.getId()), directory, String.valueOf(id), attachment.getFileName(), ATTACHMENT_POSTFIX);
                    index++;
                    LOGGER.info("Attachment file has been saved: {} ", newFile);
                }

                LOGGER.info("Full contact has been updated: {} ", contact);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {

                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                throw new ServiceException(e);
            }

        } catch (SQLException | LoadPropertiesException | ServiceException e) {
            LOGGER.error("Full contact updating was failed", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteContacts(List<Long> contactIdList) throws ServiceException {

        try (Connection connection = ConnectionFactory.createConnection()) {

            contactService = new ContactServiceImpl(connection);

            try {
                connection.setAutoCommit(false);
                contactService.deleteAll(contactIdList);

                for (Long id : contactIdList) {
                    FileManagerUtil.deleteDirectory(id);
                }

                connection.commit();
                connection.setAutoCommit(true);

            } catch (Exception e) {

                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw new ServiceException(e);
            }

        } catch (Exception e) {
            LOGGER.error("Deleting contacts was failed", e);
            throw new ServiceException(e);
        }


    }

    @Override
    public long countAllContacts() throws ServiceException {

        try (Connection connection = ConnectionFactory.createConnection()) {

            contactService = new ContactServiceImpl(connection);
            return contactService.countAll();

        } catch (Exception e) {
            LOGGER.error("Counting contacts was failed", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public Contact findContactById(long contactId) throws ServiceException {

        try (Connection connection = ConnectionFactory.createConnection()) {

            contactService = new ContactServiceImpl(connection);
            phoneService = new PhoneServiceImpl(connection);
            attachmentService = new AttachmentServiceImpl(connection);

            try {

                connection.setAutoCommit(false);

                final Contact contact = contactService.findById(contactId);

                if (contact == null) {
                    return null;
                }

                final List<Phone> phoneList = phoneService.findByContactId(contactId);
                final List<Attachment> attachmentList = attachmentService.findByContactId(contactId);

                contact.setPhoneList(phoneList);
                contact.setAttachmentList(attachmentList);

                final String pathToFile = FileManagerUtil.findPathToFile(contactId, IMG_FIND_PREFIX);

                if (pathToFile != null) {
                    contact.setImageName(FileManagerUtil.FileToBase64(pathToFile));
                }

                connection.commit();
                connection.setAutoCommit(true);
                return contact;

            } catch (Exception e) {

                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw new ServiceException(e);
            }

        } catch (Exception e) {
            LOGGER.error("Finding contact was failed !", e);
            throw new ServiceException(e);
        }


    }

    @Override
    public Contact findByEmail(String email) throws ServiceException {

        try (Connection connection = ConnectionFactory.createConnection()) {
            contactService = new ContactServiceImpl(connection);
            return contactService.findByEmail(email);

        } catch (Exception e) {
            LOGGER.error("Finding contact by email was failed", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void sendMessagesViaEmail(MessageRequest message) throws ServiceException {

        emailService = new EmailServiceImpl();

        for (String email : message.getEmails()) {
            try {
                emailService.sendEmail(email, message.getSubject(), message.getText());
                LOGGER.info("Message has been sent {}", email);
            } catch (Exception e) {
                LOGGER.error("Sending message to {} was failed", email, e);
                throw new ServiceException(e);
            }
        }

    }

}
