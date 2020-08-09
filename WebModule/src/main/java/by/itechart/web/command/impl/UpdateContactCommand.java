package by.itechart.web.command.impl;

import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.exception.EmailAlreadyExistException;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.logic.validator.ContactValidator;
import by.itechart.web.command.Command;
import by.itechart.web.exception.RequestParseException;
import by.itechart.web.util.MultipartParserUtil;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.itechart.web.command.ConstantMessages.*;

public class UpdateContactCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private ContactValidator contactValidator = new ContactValidator();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            final ContactDTO contactDTO = MultipartParserUtil.parseMultipartRequest(req);
            final List<String> errorList = contactValidator.validateContact(contactDTO);

            if (errorList.isEmpty()) {
                facadeService.updateFullContact(contactDTO);
                resp.setStatus(resp.SC_OK);
                resp.getWriter().write(gson.toJson(CONTACT_UPDATE));

            } else {
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(errorList));
            }

        } catch (EmailAlreadyExistException e) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(EMAIL_EXISTS));

        } catch (ServiceException | RequestParseException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
        }

    }

}
