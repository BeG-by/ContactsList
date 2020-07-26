package by.itechart.web.thymeleaf;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebListener
public class Config implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        TemplateEngine engine = templateEngine(sce.getServletContext());
        TemplateEngineUtil.storeTemplateEngine(sce.getServletContext(), engine);
    }


    private TemplateEngine templateEngine(ServletContext servletContext) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver(servletContext));
        return engine;
    }

    private ITemplateResolver templateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

}