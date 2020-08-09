package by.itechart.web.command.impl;

import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static by.itechart.web.command.ConstantMessages.*;

public class DeleteAllContactsCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private final Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final long[] listId = gson.fromJson(req.getReader().readLine(), long[].class);

        if (listId.length != 0) {

            try {
                facadeService.deleteContacts(Arrays.stream(listId).boxed().collect(Collectors.toList()));
                resp.getWriter().write(gson.toJson(CONTACT_DELETE));
                resp.setStatus(resp.SC_OK);

            } catch (ServiceException e) {
                resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
            }

        } else {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(CONTACT_LIST_EMPTY));
        }

    }

}
