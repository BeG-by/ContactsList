package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.impl.ContactDAOImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindAllContactsCommand implements Command {

    private ContactDAO contactDAO = new ContactDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(resp.SC_OK);
        resp.getWriter().write(new Gson().toJson(contactDAO.findAll()));
    }

}
