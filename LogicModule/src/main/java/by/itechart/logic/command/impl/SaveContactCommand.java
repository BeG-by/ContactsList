package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveContactCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {

            final Contact contact = contactService.parseMultipartRequest(req);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");


    }

}
