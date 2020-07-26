package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.logic.validator.ContactValidator;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateContactCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();
    private ContactValidator contactValidator = new ContactValidator();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            final ContactDTO contactDTO = contactService.parseMultipartRequest(req);
            final List<String> errorList = contactValidator.validateContact(contactDTO);

            if (errorList.isEmpty()) {
                contactService.updateOne(contactDTO);
                resp.setStatus(resp.SC_OK);
                resp.getWriter().write(new Gson().toJson("Contact has been updated"));

            } else {
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(new Gson().toJson(errorList));
            }

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(new Gson().toJson(e.getMessage()));
        }

        resp.setContentType("application/json");

    }

}
