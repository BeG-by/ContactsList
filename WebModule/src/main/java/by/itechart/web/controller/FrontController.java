package by.itechart.web.controller;


import by.itechart.web.command.Command;
import by.itechart.web.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/contacts/*")
public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
    private final static String ENTRY_POINT = "/api/v1/contacts/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        final String method = req.getMethod();
        final String requestURI = req.getRequestURI();

        LOGGER.info("Request is processing... [url: {} , method: {}]", requestURI, method);

        final String commandKey = method + "/" + requestURI.substring(requestURI.lastIndexOf(ENTRY_POINT) + ENTRY_POINT.length());

        CommandFactory commandFactory = new CommandFactory();
        final Command command = commandFactory.getInstance(commandKey);

        if (command != null) {
            command.execute(req, resp);
            LOGGER.info("Request [url: {} , method: {}] has been processed", req.getRequestURI(), req.getMethod());
        } else {
            LOGGER.error("Command didn't find [{}] ", commandKey);
        }

    }

}
