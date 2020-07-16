package by.itechart.web;

import by.itechart.logic.command.Command;
import by.itechart.logic.command.util.CommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/contactsList/*")
public class FrontController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FrontController.class);
    private final static String ENTRY_POINT = "/v1/contactsList/";

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

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info(String.format("Request is processing... [url: %s , method: %s]", req.getRequestURI(), req.getMethod()));

        final String requestURI = req.getRequestURI();
        final String commandKey = requestURI.substring(requestURI.lastIndexOf(ENTRY_POINT) + ENTRY_POINT.length());
        CommandFactory commandFactory = new CommandFactory();
        final Command command = commandFactory.getInstance(commandKey);

        if (command != null) {
            command.execute(req, resp);
            resp.setCharacterEncoding("UTF-8");
            logger.info(String.format("Request [url: %s , method: %s] has been processed", req.getRequestURI(), req.getMethod()));
        } else {
            logger.error(String.format("Command didn't find [%s] ", commandKey));
        }

    }

}
