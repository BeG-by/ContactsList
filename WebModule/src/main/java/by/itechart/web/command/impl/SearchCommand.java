package by.itechart.web.command.impl;

import by.itechart.logic.dto.SearchRequest;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.FacadeService;
import by.itechart.logic.service.impl.FacadeServiceImpl;
import by.itechart.logic.validator.ParameterValidator;
import by.itechart.logic.validator.SearchRequestValidator;
import by.itechart.web.command.Command;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.itechart.web.command.ConstantMessages.*;

public class SearchCommand implements Command {

    private FacadeService facadeService = new FacadeServiceImpl();
    private Gson gson = new Gson();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String page = req.getParameter(PAGE_PARAMETER);
        final String pageLimit = req.getParameter(PAGE_LIMIT_PARAMETER);

        if (!ParameterValidator.pageValidate(page , pageLimit)) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(PAGE_INCORRECT));
            return;
        }

        final SearchRequest searchRequest = gson.fromJson(req.getReader().readLine(), SearchRequest.class);

        try {
            SearchRequestValidator.validate(searchRequest);
            final List<Contact> contacts = facadeService.searchContactWithFilter(searchRequest, Integer.parseInt(page), Integer.parseInt(pageLimit));
            resp.setStatus(resp.SC_OK);
            resp.getWriter().write(gson.toJson(contacts));

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(SERVICE_UNAVAILABLE));
        }

    }

}
