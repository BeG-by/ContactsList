package by.itechart.web.command.impl;

import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountAllContactsCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private final Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        try {
            final long count = facadeService.countAllContacts();
            resp.getWriter().write(gson.toJson(count));
            resp.setStatus(resp.SC_OK);

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable."));
        }

    }

}
