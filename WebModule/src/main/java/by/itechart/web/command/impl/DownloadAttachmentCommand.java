package by.itechart.web.command.impl;

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

public class DownloadAttachmentCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private final Gson gson = new Gson();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String contactId = req.getParameter(CONTACT_ID_PARAMETER);
        final String attachmentId = req.getParameter(ATTACHMENT_ID_PARAMETER);


        if (!ParameterValidator.contactIdValidate(contactId) || !ParameterValidator.attachmentIdValidate(attachmentId)) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(ID_INCORRECT));
            return;
        }

        try {
            final String attachmentFile = facadeService.findAttachmentFile(Long.parseLong(contactId), Long.parseLong(attachmentId));

            if (attachmentFile != null) {
                resp.getWriter().write(gson.toJson(attachmentFile));
            } else {
                resp.setStatus(resp.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(ATTACHMENT_NOT_FOUND));
            }

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
        }

    }

}
