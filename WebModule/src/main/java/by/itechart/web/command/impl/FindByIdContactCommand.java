package by.itechart.web.command.impl;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindByIdContactCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        final String contactId = req.getParameter("contactId");

        if (contactId == null || !contactId.matches("\\d+")) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("Page and page limit must be digit."));
            return;
        }

        try {
            final Contact contact = facadeService.findContactById(Long.parseLong(contactId));

            if(contact == null){
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Contact not found."));
            } else {
                resp.getWriter().write(gson.toJson(contact));
                resp.setStatus(resp.SC_OK);
            }


        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable."));
        }

    }

}
