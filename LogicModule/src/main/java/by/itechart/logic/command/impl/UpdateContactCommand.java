package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.dao.impl.AttachmentDAOImpl;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.logic.validator.ContactValidator;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateContactCommand implements Command {

    private ContactValidator contactValidator = new ContactValidator();
    private static final Logger logger = Logger.getLogger(UpdateContactCommand.class);


    public UpdateContactCommand() {
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        ContactService contactService;
        try {
            contactService = new ContactServiceImpl(ConnectionFactory.createConnection());
        } catch (Exception e) {
            logger.error(e);
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new Gson().toJson("Service is temporarily unavailable"));
            return;
        }


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

    }

}
