package by.itechart.web.command.impl;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindAllContactsCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        final String page = req.getParameter("page");
        final String pageLimit = req.getParameter("pageLimit");

        if (page == null || pageLimit == null || !page.matches("\\d+") ||
                !pageLimit.matches("\\d+") || Integer.parseInt(page) <= 0) {

            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Page and page limit must be digit and more then zero."));
            return;
        }

        try {
            final List<Contact> contacts = facadeService.findAllWithPagination(Integer.parseInt(page), Integer.parseInt(pageLimit));
            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(gson.toJson(contacts));

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable."));
        }

    }

}
