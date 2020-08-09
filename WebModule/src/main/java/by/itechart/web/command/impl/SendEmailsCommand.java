package by.itechart.web.command.impl;

import by.itechart.logic.dto.MessageRequest;
import by.itechart.logic.exception.TemplateNotFoundException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.web.command.Command;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itechart.web.command.ConstantMessages.*;

public class SendEmailsCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MessageRequest message = gson.fromJson(req.getReader().readLine(), new TypeToken<MessageRequest>() {
        }.getType());

        try {
            facadeService.sendMessagesViaEmail(message);
            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(EMAIL_SEND);

        } catch (TemplateNotFoundException e) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(TEMPLATE_NOT_FOUND));

        } catch (Exception e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
        }

    }

}
