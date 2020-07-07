package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FindAllContactsCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(resp.SC_OK);
        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(getList()));

    }

    private List<Contact> getList() {
        return new ArrayList<>() {
            {
                add(new Contact(1,
                        "Ivan",
                        "Ivanov",
                        "Ivanovich",
                        LocalDate.of(1990, 1, 1),
                        "Men",
                        "Belarus",
                        "Single",
                        "google.com",
                        "ex@mail.ru",
                        "iTechArt",
                        new Address(1, 1, "Belarus", "Minsk", "Nemiga 26", 220030)));

                add(new Contact(2,
                        "Ivan2",
                        "Ivanov2",
                        "Ivanovich2",
                        LocalDate.of(1990, 1, 1),
                        "Men",
                        "Belarus",
                        "Single",
                        "google.com",
                        "ex@mail.ru",
                        "iTechArt",
                        new Address(1, 1, "Belarus", "Minsk", "Nemiga 26", 220030)));

                add(new Contact(3,
                        "Ivan3",
                        "Ivanov3",
                        "Ivanovich3",
                        LocalDate.of(1990, 1, 1),
                        "Men",
                        "Belarus",
                        "Single",
                        "google.com",
                        "ex@mail.ru",
                        "iTechArt",
                        new Address(1, 1, "Belarus", "Minsk", "Nemiga 26", 220030)));

                add(new Contact(4,
                        "Ivan4",
                        "Ivanov4",
                        "Ivanovich4",
                        LocalDate.of(1990, 1, 1),
                        "Men",
                        "Belarus",
                        "Single",
                        "google.com",
                        "ex@mail.ru",
                        "iTechArt",
                        new Address(1, 1, "Belarus", "Minsk", "Nemiga 26", 220030)));

                add(new Contact(5,
                        "Ivan5",
                        "Ivanov5",
                        "Ivanovich5",
                        LocalDate.of(1990, 1, 1),
                        "Men",
                        "Belarus",
                        "Single",
                        "google.com",
                        "ex@mail.ru",
                        "iTechArt",
                        new Address(1, 1, "Belarus", "Minsk", "Nemiga 26", 220030)));
            }
        };


    }
}
