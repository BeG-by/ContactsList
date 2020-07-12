package by.itechart.logic.command;

import by.itechart.logic.command.impl.FindAllContactsCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap = new HashMap<>();

    {
        commandMap.put("contacts/findAll", new FindAllContactsCommand());
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}
