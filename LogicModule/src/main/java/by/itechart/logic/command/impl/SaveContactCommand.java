package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class SaveContactCommand implements Command {

    private ContactDAO contactDAO = new ContactDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        final String jsonContact = req.getReader().readLine();
        final Gson gson = new Gson();
        Contact contact = gson.fromJson(jsonContact, Contact.class);

        final long contactId = contactDAO.save(contact);

        if (contactId != -1) {
            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(gson.toJson(String.format("Contact with %d has been created !", contactId)));
        } else {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Incorrect data."));
        }

    }
}
