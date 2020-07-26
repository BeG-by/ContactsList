package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountAllContactsCommand implements Command {

    private static final Logger logger = Logger.getLogger(UpdateContactCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ContactService contactService;
        try {
            contactService = new ContactServiceImpl(ConnectionFactory.createConnection());
        } catch (Exception e) {
            logger.error(e);
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson("Service is temporarily unavailable"));
            return;
        }


        try {
            final long count = contactService.countAll();
            resp.getWriter().write(new Gson().toJson(count));
            resp.setContentType("application/json");
            resp.setStatus(resp.SC_OK);

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson("Service is temporarily unavailable"));
        }

    }
}
