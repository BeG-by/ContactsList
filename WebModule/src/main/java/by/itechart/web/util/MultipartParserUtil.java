package by.itechart.web.util;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.entity.Contact;
import by.itechart.web.exception.RequestParseException;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MultipartParserUtil {

    private static Gson gson = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipartParserUtil.class);

    private static final String JSON_CONTACT_FIELD_NAME = "jsonContact";
    private static final String AVATAR_FIELD_NAME = "avatar";
    private static final String ATTACHMENT_FIELD_NAME = "attachment";

    public static ContactDTO parseMultipartRequest(HttpServletRequest req) throws RequestParseException {

        try {
            List<FileItem> items = createServletFileUpload(req).parseRequest(req);

            Contact contact = null;
            byte[] avatar = null;
            Map<Long, byte[]> attachments = new LinkedHashMap<>();

            for (FileItem item : items) {
                if (item != null) {

                    final String fieldName = item.getFieldName();

                    if (fieldName.startsWith(JSON_CONTACT_FIELD_NAME)) {
                        contact = gson.fromJson(new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8), Contact.class);
                    } else if (fieldName.startsWith(AVATAR_FIELD_NAME)) {
                        avatar = item.get();
                    } else if (fieldName.startsWith(ATTACHMENT_FIELD_NAME)) {
                        final String id = fieldName.substring(fieldName.lastIndexOf(ATTACHMENT_FIELD_NAME) + ATTACHMENT_FIELD_NAME.length());
                        attachments.put(Long.parseLong(id), item.get());
                    }
                }
            }

            LOGGER.info("Contact has been parsed: {}", contact);

            return new ContactDTO(contact, avatar, attachments);

        } catch (Exception e) {
            LOGGER.error("Parse multipart request failed ! ", e);
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
