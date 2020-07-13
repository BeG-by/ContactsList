package by.itechart.logic.dao;

import by.itechart.logic.entity.Contact;

import java.util.List;

public interface ContactDAO {

    List<Contact> findAll();

    long save(Contact contact);

    void deleteAll(List<Long> idList);

}
