package by.itechart.web.command;

import by.itechart.web.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap = new HashMap<>();

    {
        commandMap.put("GET/", new FindAllContactsCommand());
        commandMap.put("DELETE/", new DeleteAllContactsCommand());
        commandMap.put("POST/", new SaveContactCommand());
        commandMap.put("GET/quantity", new CountAllContactsCommand());
        commandMap.put("PUT/", new UpdateContactCommand());
        commandMap.put("GET/id", new FindByIdContactCommand());
        commandMap.put("POST/email", new SendEmailsCommand());
        commandMap.put("POST/filter", new SearchCommand());
        commandMap.put("POST/filter/quantity", new SearchCountAllCommand());
        commandMap.put("GET/templates", new FindAllTemplates());
        commandMap.put("GET/attachment", new DownloadAttachmentCommand());
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}