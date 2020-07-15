package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.dao.impl.PhoneDAOImpl;
import by.itechart.logic.entity.Contact;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveContactCommand implements Command {

    private ContactDAO contactDAO = new ContactDAOImpl();
    private PhoneDAO phoneDAO = new PhoneDAOImpl();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String jsonContact = req.getReader().readLine();
        final Gson gson = new Gson();
        Contact contact = gson.fromJson(jsonContact, Contact.class);

        System.out.println(contact);

        final long contactId = contactDAO.save(contact);


        if (contactId != -1) {

            String message = String.format("Contact with id = %d has been created without phoneList !", contactId);

            if (contact.getPhoneList() != null && !contact.getPhoneList().isEmpty()) {
                contact.getPhoneList().forEach(c -> c.setContactId(contactId));

                if (phoneDAO.save(contact.getPhoneList())) {
                    message = String.format("Contact with id = %d has been created !", contactId);
                }
            }

            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(gson.toJson(message));

        } else {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Incorrect data."));
        }

    }
}
