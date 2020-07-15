package by.itechart.logic.command.util;

import by.itechart.logic.command.Command;
import by.itechart.logic.command.impl.DeleteAllContactsCommand;
import by.itechart.logic.command.impl.FindAllContactsCommand;
import by.itechart.logic.command.impl.SaveContactCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, Command> commandMap = new HashMap<>();

    {
        commandMap.put("contact/findAll", new FindAllContactsCommand());
        commandMap.put("contact/deleteAll", new DeleteAllContactsCommand());
        commandMap.put("contact/save", new SaveContactCommand());
    }


    public Command getInstance(String key) {
        return commandMap.get(key);
    }

}
