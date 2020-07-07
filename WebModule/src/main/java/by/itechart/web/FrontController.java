package by.itechart.web;

import by.itechart.logic.command.Command;
import by.itechart.logic.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contacts/*")
public class FrontController extends HttpServlet {

    private final static String ENTRY_POINT = "/contacts/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO GET");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("START PROCESS");
        final String requestURI = req.getRequestURI();
        final String commandKey = requestURI.substring(requestURI.lastIndexOf(ENTRY_POINT) + ENTRY_POINT.length());
        System.out.println(req.getRequestURI());
        System.out.println(System.getProperty("java.classpath"));
        CommandFactory commandFactory = new CommandFactory();
        final Command command = commandFactory.getInstance(commandKey);

        if (command != null) {
            command.execute(req, resp);
        }
    }

}
