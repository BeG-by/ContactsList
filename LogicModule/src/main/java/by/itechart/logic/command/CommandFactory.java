package by.itechart.logic.command;

import by.itechart.logic.command.impl.*;

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
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}
