package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountAllContactsCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
