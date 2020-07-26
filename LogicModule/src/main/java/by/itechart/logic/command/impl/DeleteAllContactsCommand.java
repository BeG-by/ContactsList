package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DeleteAllContactsCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final Gson gson = new Gson();
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

        resp.setContentType("application/json");

    }

}
