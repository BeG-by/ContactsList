package by.itechart.logic.service.util;

import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.TemplateNotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class TemplateUtil {

    private static Configuration config;
    private static Map<String, String> templatesName = new HashMap<>();
    private static Map<String, String> templatesContent = new HashMap<>();

    static {
        config = new Configuration(Configuration.VERSION_2_3_0);
        config.setClassForTemplateLoading(TemplateUtil.class, "/templates/");
        config.setDefaultEncoding("UTF-8");

        fillTemplateMap();
    }


    private static void fillTemplateMap() {
        templatesName.put("Happy birthday, friend", "friend-birthday.ftl");
        templatesName.put("Happy birthday, boss", "boss-birthday.ftl");
        templatesName.put("Work Anniversary, colleague", "anniversary.ftl");
    }


    public static String getTemplateString(String key, Contact contact) throws IOException, TemplateException, TemplateNotFoundException {

        final String templateName = templatesName.get(key);

        if (templateName == null) {
            throw new TemplateNotFoundException("Template not found !");
        }

        final Template template = config.getTemplate(templateName);

        Map<String, Object> values = new HashMap<>();
        values.put("contact", contact);

        StringWriter stringWriter = new StringWriter();
        template.process(values, stringWriter);

        return stringWriter.toString();

    }

    public static Map<String, String> getTemplatesContent() throws IOException, TemplateException {

        final Contact mockContact = new Contact.Builder()
                .firstName("FirstName")
                .lastName("LastName")
                .middleName("MiddleName")
                .build();

        Map<String, Object> values = new HashMap<>();
        values.put("contact", mockContact);

        for (Map.Entry<String, String> entry : templatesName.entrySet()) {
            final Template template = config.getTemplate(entry.getValue());
            StringWriter stringWriter = new StringWriter();
            template.process(values, stringWriter);
            templatesContent.put(entry.getKey(), stringWriter.toString());
        }

        return templatesContent;

    }

}
