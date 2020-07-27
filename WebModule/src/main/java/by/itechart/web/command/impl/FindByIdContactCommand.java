package by.itechart.web.command.impl;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindByIdContactCommand implements Command {

    private Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(UpdateContactCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        ContactService contactService;
        try {
            contactService = new ContactServiceImpl(ConnectionFactory.createConnection());
        } catch (Exception e) {
            logger.error(e);
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable"));
            return;
        }

        final String contactId = req.getParameter("contactId");

        if (contactId == null || !contactId.matches("\\d+")) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Page and page limit must be digit !"));
            return;
        }

        try {
            final Contact contact = contactService.findById(Long.parseLong(contactId));

            if(contact == null){
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Contact not found !"));
            } else {
                resp.getWriter().write(gson.toJson(contact));
                resp.setStatus(resp.SC_OK);
            }


        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable"));
        }

    }

}
