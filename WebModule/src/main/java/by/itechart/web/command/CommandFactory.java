package by.itechart.web.command;

import by.itechart.web.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap = new HashMap<>();

    {
        commandMap.put("contacts/findAll", new FindAllContactsCommand());
        commandMap.put("contacts/deleteAll", new DeleteAllContactsCommand());
        commandMap.put("contacts/save", new SaveContactCommand());
        commandMap.put("contacts/countAll", new CountAllContactsCommand());
        commandMap.put("contacts/update", new UpdateContactCommand());
        commandMap.put("contacts/findById", new FindByIdContactCommand());
        commandMap.put("contacts/email", new SendEmailsCommand());
        commandMap.put("contacts/search", new SearchCommand());
        commandMap.put("contacts/searchCountAll", new SearchCountAllCommand());
        commandMap.put("contacts/templates", new FindAllTemplates());
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}