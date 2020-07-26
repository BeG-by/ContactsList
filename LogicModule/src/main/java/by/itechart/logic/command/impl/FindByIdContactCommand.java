package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindByIdContactCommand implements Command {

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
            resp.getWriter().write(new Gson().toJson("Service is temporarily unavailable"));
            return;
        }

        final String contactId = req.getParameter("contactId");
        // ...

    }
}
