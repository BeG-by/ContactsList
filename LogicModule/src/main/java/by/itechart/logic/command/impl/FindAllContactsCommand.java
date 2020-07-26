package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindAllContactsCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String page = req.getParameter("page");
        final String pageLimit = req.getParameter("pageLimit");

        if (page == null || pageLimit == null || !page.matches("\\d+") || !pageLimit.matches("\\d+")) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(new Gson().toJson("Page and page limit must be digit !"));
            return;
        }

        try {
            final List<Contact> contacts = contactService.findAllWithLimit(Integer.parseInt(page), Integer.parseInt(pageLimit));
            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(new Gson().toJson(contacts));

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson("Service is temporarily unavailable"));
        }

        resp.setContentType("application/json");

    }

}
