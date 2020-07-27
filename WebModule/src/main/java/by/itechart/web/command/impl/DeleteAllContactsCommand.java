package by.itechart.web.command.impl;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DeleteAllContactsCommand implements Command {

    private final Gson gson = new Gson();
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

        final long[] listId = gson.fromJson(req.getReader().readLine(), long[].class);

        if (listId.length != 0) {

            try {
                contactService.deleteAllById(Arrays.stream(listId).boxed().collect(Collectors.toList()));
                resp.getWriter().write(gson.toJson("Contacts have been deleted"));
                resp.setStatus(resp.SC_OK);
            } catch (ServiceException e) {
                resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(gson.toJson("Service is temporarily unavailable"));
            }

        } else {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Contacts list is empty !"));
        }

    }

}
