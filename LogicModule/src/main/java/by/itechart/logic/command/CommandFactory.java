package by.itechart.logic.command;

import by.itechart.logic.command.impl.DeleteAllContactsCommand;
import by.itechart.logic.command.impl.FindAllContactsCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap = new HashMap<>();

    {
        commandMap.put("contacts/findAll", new FindAllContactsCommand());
        commandMap.put("contacts/deleteAll", new DeleteAllContactsCommand());
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}
