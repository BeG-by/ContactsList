package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.util.ConnectionFactory;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private static final String FIND_ALL_CONTACTS_QUERY = "SELECT * FROM contact c LEFT JOIN address a ON c.id = a.contact_id;";
    private static final String DELETE_ALL_CONTACTS_QUERY = "DELETE FROM contact WHERE id = ?;";
    private static final String DELETE_ALL_ADDRESS_QUERY = "DELETE FROM address WHERE contact_id = ?;";
    private static final String SAVE_CONTACT_QUERY =
            "INSERT INTO contact (first_name, last_name, middle_name, birthday, sex, nationality, marital_status, url, email, job) VALUES (?,?,?,?,?,?,?,?,?,?)";

    private static final String SAVE_ADDRESS_QUERY = "INSERT INTO address ( country, city, street, post_index, contact_id) VALUES (?,?,?,?,?)";


    private static Logger logger = Logger.getLogger(ContactDAOImpl.class);


    @Override
    public List<Contact> findAll() {

        List<Contact> contacts = new ArrayList<>();

        try (final Connection connection = ConnectionFactory.createConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(FIND_ALL_CONTACTS_QUERY);

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
            e.printStackTrace();
            logger.error("SQL error [findAll] --- " + e);
        }

        return contacts;
    }

    //need Date validation (NullPointerException)
    @Override
    public long save(Contact contact) {

        long contactId = -1;

        try (final Connection connection = ConnectionFactory.createConnection()) {
            final PreparedStatement contactStatement = connection.prepareStatement(SAVE_CONTACT_QUERY, Statement.RETURN_GENERATED_KEYS);
            final PreparedStatement addressStatement = connection.prepareStatement(SAVE_ADDRESS_QUERY);

            connection.setAutoCommit(false);
            contactStatement.setString(1, contact.getFirstName());
            contactStatement.setString(2, contact.getLastName());
            contactStatement.setString(3, contact.getMiddleName());
            contactStatement.setDate(4, Date.valueOf(contact.getBirthday()));
            contactStatement.setString(5, contact.getSex());
            contactStatement.setString(6, contact.getNationality());
            contactStatement.setString(7, contact.getMaritalStatus());
            contactStatement.setString(8, contact.getUrlWebSite());
            contactStatement.setString(9, contact.getEmail());
            contactStatement.setString(10, contact.getCurrentJob());
            contactStatement.executeUpdate();

            ResultSet generatedKeys = contactStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                contactId = generatedKeys.getLong(1);
            }

            Address address = contact.getAddress();

            addressStatement.setString(1, address.getCountry());
            addressStatement.setString(2, address.getCity());
            addressStatement.setString(3, address.getStreet());
            addressStatement.setInt(4, address.getPostIndex());
            addressStatement.setLong(5, contactId);

            addressStatement.executeUpdate();

            connection.commit();
            logger.info(String.format("Contact was created [id= %d]", contactId));

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQL error [save] --- " + e);
            return -1;
        } catch (Exception e) {
            // bug finder
            e.printStackTrace();
        }

        return contactId;
    }

    @Override
    public void deleteAll(List<Long> idList) {

        try (final Connection connection = ConnectionFactory.createConnection()) {

            final PreparedStatement contactStatement = connection.prepareStatement(DELETE_ALL_CONTACTS_QUERY);
            final PreparedStatement addressStatement = connection.prepareStatement(DELETE_ALL_ADDRESS_QUERY);
            connection.setAutoCommit(false);

            for (Long id : idList) {
                addressStatement.setLong(1, id);
                addressStatement.executeUpdate();
                contactStatement.setLong(1, id);
                contactStatement.executeUpdate();
            }

            connection.commit();
            logger.info(String.format("Contacts were remove [listId= %s]", idList));


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQL error [deleteAll] --- " + e);
        }


    }

    private static LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
