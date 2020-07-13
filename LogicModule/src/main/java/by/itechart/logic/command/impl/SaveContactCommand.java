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
        resp.setStatus(resp.SC_OK);
        resp.setContentType("application/json");
        Contact contact = new Contact.Builder()
                .firstName("TEST")
                .lastName("TEST")
                .birthday(LocalDate.now())
                .address(new Address("COUNTRY", "CITY", "STREET", 220022))
                .build();

        final long contactId = contactDAO.save(contact);
        if (contactId != 1) {
            resp.getWriter().write(new Gson().toJson(String.format("Contact with id %d has been created", contactId)));
        }

    }
}
