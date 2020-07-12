package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.ConnectionFactory;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private static final String FIND_ALL_QUERY = "SELECT * FROM contact c LEFT JOIN address a ON c.id = a.contact_id;";


    private static Logger logger = Logger.getLogger(ContactDAOImpl.class);


    @Override
    public List<Contact> findAll() {

        List<Contact> contacts = new ArrayList<>();

        try (final Connection connection = ConnectionFactory.createConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resultSet.next()) {

                Contact contact = new Contact.Builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .middleName(resultSet.getString(4))
                        .birthday(convertToLocalDate(resultSet.getDate(5)))
                        .sex(resultSet.getString(6))
                        .nationality(resultSet.getString(7))
                        .maritalStatus(resultSet.getString(8))
                        .urlWebSite(resultSet.getString(9))
                        .email(resultSet.getString(10))
                        .currentJob(resultSet.getString(11))
                        .address(new Address(
                                resultSet.getLong(12),
                                resultSet.getLong(17),
                                resultSet.getString(13),
                                resultSet.getString(14),
                                resultSet.getString(15),
                                resultSet.getInt(16)))
                        .build();

                contacts.add(contact);
            }

        } catch (SQLException e) {
            logger.error("SQL error [findAll]");
            logger.error(e);
        }

        return contacts;
    }

    private static LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
