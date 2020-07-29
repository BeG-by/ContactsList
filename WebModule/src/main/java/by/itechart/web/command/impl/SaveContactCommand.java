package by.itechart.web.command.impl;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.exception.AlreadyExistException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import by.itechart.logic.validator.ContactValidator;
import by.itechart.web.command.Command;
import by.itechart.web.exception.RequestParseException;
import by.itechart.web.util.MultipartParserUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SaveContactCommand implements Command {

    private ContactValidator contactValidator = new ContactValidator();
    private Gson gson = new Gson();

    private static final Logger logger = Logger.getLogger(SaveContactCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        ContactService contactService;
        try {
            contactService = new ContactServiceImpl(ConnectionFactory.createConnection());
        } catch (Exception e) {
            logger.error(e);
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable"));
            return;
        }

        try {
            final ContactDTO contactDTO = MultipartParserUtil.parseMultipartRequest(req);
            final List<String> errorList = contactValidator.validateContact(contactDTO);

            if (errorList.isEmpty()) {
                contactService.saveOne(contactDTO);
                resp.setStatus(resp.SC_OK);
                resp.getWriter().write(gson.toJson("Contact has been saved"));

            } else {
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(errorList));
            }

        } catch (ServiceException | RequestParseException | AlreadyExistException e) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(e.getMessage()));
        }

    }

}