package by.itechart.web.command.impl;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.logic.validator.ParameterValidator;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itechart.web.command.ConstantMessages.*;

public class FindByIdContactCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String contactId = req.getParameter(CONTACT_ID_PARAMETER);

        if (!ParameterValidator.contactIdValidate(contactId)) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(CONTACT_ID_INCORRECT));
            return;
        }

        try {
            final Contact contact = facadeService.findContactById(Long.parseLong(contactId));

            if (contact == null) {
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(CONTACT_NOT_FOUND));
            } else {
                resp.getWriter().write(gson.toJson(contact));
                resp.setStatus(resp.SC_OK);
            }


        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
        }

    }

}
