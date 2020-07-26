package by.itechart.web.controller;


import by.itechart.logic.entity.Contact;
import by.itechart.web.thymeleaf.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/static/*")
public class ViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("GET REQUEST OK: " + req.getRequestURI());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("test", "World");


        if (req.getRequestURI().endsWith("/update")) {
            context.setVariable("isCreate", 0);
            final Contact contact = new Contact();
            contact.setFirstName("test");
            contact.setLastName("test2");
            contact.setMiddleName("");
            contact.setSex("female");
            context.setVariable("contact", contact);
        } else {
            context.setVariable("isCreate", 1);
        }

        engine.process("edit-form.html", context, resp.getWriter());
    }

}
