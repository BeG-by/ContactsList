package by.itechart.web.command.impl;

import by.itechart.logic.dto.SearchRequest;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.logic.validator.SearchRequestValidator;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchCountAllCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        final SearchRequest searchRequest = gson.fromJson(req.getReader().readLine(), by.itechart.logic.dto.SearchRequest.class);

        try {
            SearchRequestValidator.validate(searchRequest);
            final long count = facadeService.countAllContactsWithFilter(searchRequest);
            resp.getWriter().write(gson.toJson(count));
            resp.setStatus(resp.SC_OK);

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Service is temporarily unavailable."));
        }

    }

}
