package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DeleteAllContactsCommand implements Command {

    private ContactDAO contactDAO = new ContactDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final Gson gson = new Gson();
        final long[] listId = gson.fromJson(req.getReader().readLine(), long[].class);
        contactDAO.deleteAll(Arrays.stream(listId).boxed().collect(Collectors.toList()));
        resp.getWriter().write(gson.toJson("Contacts have been deleted"));

        resp.setStatus(resp.SC_OK);
        resp.setContentType("application/json");

    }

}
