package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.dao.impl.PhoneDAOImpl;
import by.itechart.logic.entity.Contact;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveContactCommand implements Command {

    private ContactDAO contactDAO = new ContactDAOImpl();
    private PhoneDAO phoneDAO = new PhoneDAOImpl();

    private static final String JSON_CONTACT = "jsonContact";
    private static final String AVATAR = "avatar";
    private static final String ATTACHMENT = "attachment";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//        final String jsonContact = req.getReader().readLine();


        try {
            final Gson gson = new Gson();
            List<FileItem> items = createServletFileUpload(req).parseRequest(req);

            Contact contact = null;
            FileItem avatar = null;
            FileItem attachment = null;


            for (FileItem item : items) {
                System.out.println(item);
                if (item != null) {
                    switch (item.getFieldName()) {
                        case JSON_CONTACT:
                            contact = gson.fromJson(item.getString(), Contact.class);
                            break;
                        case AVATAR:
                            avatar = item;
                            final String property = System.getProperty("user.home");
                            avatar.write(new File(property + File.separator + "desktop" + File.separator + item.getName()));
                            break;
                        case ATTACHMENT:
                            attachment = item;

                    }
                }

            }

            System.out.println(avatar);
            System.out.println(contact);
            System.out.println(attachment);

        } catch (Exception e) {
            e.printStackTrace();
        }


//        final long contactId = contactDAO.save(contact);
//
//
//        if (contactId != -1) {
//
//            String message = String.format("Contact with id = %d has been created without phoneList !", contactId);
//
//            if (contact.getPhoneList() != null && !contact.getPhoneList().isEmpty()) {
//                contact.getPhoneList().forEach(c -> c.setContactId(contactId));
//
//                if (phoneDAO.save(contact.getPhoneList())) {
//                    message = String.format("Contact with id = %d has been created !", contactId);
//                }
//            }
//
//            resp.setStatus(resp.SC_CREATED);
//            resp.getWriter().write(gson.toJson(message));
//
//        } else {
//            resp.setStatus(resp.SC_BAD_REQUEST);
//            resp.getWriter().write(gson.toJson("Incorrect data."));
//        }
//
//        resp.setContentType("application/json");

    }

    private ServletFileUpload createServletFileUpload(HttpServletRequest req) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        File repository = (File) req.getServletContext().getAttribute("javax.servlet.context.tempdir");
        diskFileItemFactory.setRepository(repository);
        return new ServletFileUpload(diskFileItemFactory);
    }


}
