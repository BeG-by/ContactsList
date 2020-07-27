package by.itechart.web.util;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.web.exception.RequestParseException;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MultipartParserUtil {

    private static Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(MultipartParserUtil.class);

    private static final String JSON_CONTACT_FIELD_NAME = "jsonContact";
    private static final String AVATAR_FIELD_NAME = "avatar";
    private static final String ATTACHMENT_FIELD_NAME = "attachment";

    public static ContactDTO parseMultipartRequest(HttpServletRequest req) throws RequestParseException {

        try {
            List<FileItem> items = createServletFileUpload(req).parseRequest(req);

            Contact contact = null;
            byte[] avatar = null;
            List<byte[]> attachments = new ArrayList<>();

            for (FileItem item : items) {
                if (item != null) {

                    final String fieldName = item.getFieldName();

                    if (fieldName.startsWith(JSON_CONTACT_FIELD_NAME)) {
                        contact = gson.fromJson(new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8), Contact.class);
                    } else if (fieldName.startsWith(AVATAR_FIELD_NAME)) {
                        avatar = item.get();
                    } else if (fieldName.startsWith(ATTACHMENT_FIELD_NAME)) {
                        attachments.add(item.get());
                    }
                }
            }

            logger.info("Contact has been parsed: " + contact);

            return new ContactDTO(contact, avatar, attachments);

        } catch (Exception e) {
            logger.error("Parse multipart request failed ! ", e);
            throw new RequestParseException("Incorrect request data !", e);
        }

    }


    private static ServletFileUpload createServletFileUpload(HttpServletRequest req) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        File repository = (File) req.getServletContext().getAttribute("javax.servlet.context.tempdir");
        diskFileItemFactory.setRepository(repository);
        return new ServletFileUpload(diskFileItemFactory);
    }

}
