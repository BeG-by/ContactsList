package by.itechart.logic.service.impl;

import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.util.NameGeneratorUtil;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContactServiceImpl implements ContactService {

    private final ContactDAO contactDAO = new ContactDAOImpl();

    private final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    private static final String JSON_CONTACT_FIELD_NAME = "jsonContact";
    private static final String AVATAR_FIELD_NAME = "avatar";
    private static final String ATTACHMENT_FIELD_NAME = "attachment";
    private static final String COMMENT_FIELD_NAME = "comment";

    private static final String PROPERTIES_PATH = "/diskPath.properties";
    private static String directoryPath;


    static {
        loadProperties();
    }

    private static void loadProperties() {

        Properties properties = new Properties();
        try {
            InputStream in = ConnectionFactory.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);

            directoryPath = properties.getProperty("directory.path");

        } catch (IOException e) {
            logger.error("Database properties haven't been loaded !\n" + e.getMessage());
        }
    }

    @Override
    public Contact parseMultipartRequest(HttpServletRequest req) throws ServiceException {

        try {
            List<FileItem> items = createServletFileUpload(req).parseRequest(req);

            Contact contact = null;
            FileItem avatar = null;
            List<FileItem> attachments = new ArrayList<>();
            String[] comments = null;

            for (FileItem item : items) {
                if (item != null) {

                    final String fieldName = item.getFieldName();

                    if (fieldName.startsWith(JSON_CONTACT_FIELD_NAME)) {
                        contact = gson.fromJson(item.getString(), Contact.class);
                    } else if (fieldName.startsWith(AVATAR_FIELD_NAME)) {
                        avatar = item;
                    } else if (fieldName.startsWith(ATTACHMENT_FIELD_NAME)) {
                        attachments.add(item);
                    } else if (fieldName.startsWith(COMMENT_FIELD_NAME)) {
                        comments = gson.fromJson(item.getString(), String[].class);
                    }

                }
            }

            if (contact != null) {

                final String directoryPathContact = createDirectory();
                contact.setDirectoryPath(directoryPathContact);

                if (avatar != null) {
                    final String imgPath = saveFile(avatar, directoryPathContact, AVATAR_FIELD_NAME);
                    logger.info("Image has been saved on the disk " + imgPath);
                }

                if (!attachments.isEmpty()) {
                    contact.setAttachmentList(new ArrayList<>());
                    int countOfComments = 0;
                    for (FileItem item : attachments) {
                        final String attPath = saveFile(item, directoryPathContact, ATTACHMENT_FIELD_NAME);
                        final Attachment attachment = new Attachment(attPath, LocalDate.now(), comments[countOfComments]);
                        contact.getAttachmentList().add(attachment);
                        logger.info("Attachment has been saved on the disk " + attPath);
                    }
                }

                logger.info("Multipart request has been parsed " + contact);
                return contact;

            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error("Parse multipart request failed !\n" + e.getMessage());
            throw new ServiceException(e);
        }

    }

    private ServletFileUpload createServletFileUpload(HttpServletRequest req) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        File repository = (File) req.getServletContext().getAttribute("javax.servlet.context.tempdir");
        diskFileItemFactory.setRepository(repository);
        return new ServletFileUpload(diskFileItemFactory);
    }

    private String saveFile(FileItem fileItem, String pathToDirectory, String start) throws Exception {
        final String name = NameGeneratorUtil.generate(start);
        String path = pathToDirectory + File.separator + name + fileItem.getName();
        fileItem.write(new File(path));
        return path;
    }

    private String createDirectory() throws IOException {
        final String name = NameGeneratorUtil.generate();
        final String pathToDirectory = directoryPath + File.separator + name;
        Files.createDirectory(Paths.get(pathToDirectory));
        return pathToDirectory;
    }


}
