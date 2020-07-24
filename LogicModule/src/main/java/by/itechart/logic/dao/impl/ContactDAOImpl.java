package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private static final String ID_COL = "id";
    private static final String FIRST_NAME_COL = "first_name";
    private static final String LAST_NAME_COL = "last_name";
    private static final String MIDDLE_NAME_COL = "middle_name";
    private static final String BIRTHDAY_COL = "birthday";
    private static final String SEX_COL = "sex";
    private static final String NATIONALITY_COL = "nationality";
    private static final String MARITAL_STATUS_COL = "marital_status";
    private static final String WEBSITE_URL_COL = "url";
    private static final String EMAIL_COL = "email";
    private static final String CURRENT_JOB_COL = "job";
    private static final String IMAGE_NAME_COL = "image_name";

    private static final String COUNTRY_COL = "country";
    private static final String CITY_COL = "city";
    private static final String STREET_COL = "street";
    private static final String POST_INDEX_COL = "post_index";
    private static final String CONTACT_ID_COL = "contact_id";


    private static final String FIND_ALL_CONTACTS_QUERY = "SELECT * FROM contact c LEFT JOIN address a ON c.id = a.contact_id ORDER BY c.id LIMIT ? , ?;";
    private static final String DELETE_ALL_CONTACTS_QUERY = "DELETE FROM contact WHERE id = ?;";
    private static final String DELETE_ALL_ADDRESS_QUERY = "DELETE FROM address WHERE contact_id = ?;";
    private static final String SAVE_CONTACT_QUERY =
            "INSERT INTO contact (first_name, last_name, middle_name, birthday, sex, nationality, marital_status, url, email, job) VALUES (?,?,?,?,?,?,?,?,?,?)";

    private static final String SAVE_ADDRESS_QUERY = "INSERT INTO address (country, city, street, post_index, contact_id) VALUES (?,?,?,?,?)";
    private static final String COUNT_CONTACTS_QUERY = "SELECT count(*) FROM contact";


    private static Logger logger = Logger.getLogger(ContactDAOImpl.class);


    @Override
    public List<Contact> findAll(int page, int pageLimit) {

        List<Contact> contacts = new ArrayList<>();

        try (final Connection connection = ConnectionFactory.createConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ALL_CONTACTS_QUERY)) {

            statement.setInt(1, (page - 1) * pageLimit);
            statement.setInt(2, pageLimit);

            try (final ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Contact contact = new Contact.Builder()
                            .id(resultSet.getLong(ID_COL))
                            .firstName(resultSet.getString(FIRST_NAME_COL))
                            .lastName(resultSet.getString(LAST_NAME_COL))
                            .middleName(resultSet.getString(MIDDLE_NAME_COL))
                            .birthday(convertToLocalDate(resultSet.getDate(BIRTHDAY_COL)))
                            .sex(resultSet.getString(SEX_COL))
                            .nationality(resultSet.getString(NATIONALITY_COL))
                            .maritalStatus(resultSet.getString(MARITAL_STATUS_COL))
                            .urlWebSite(resultSet.getString(WEBSITE_URL_COL))
                            .email(resultSet.getString(EMAIL_COL))
                            .currentJob(resultSet.getString(CURRENT_JOB_COL))
                            .address(new Address(
                                    resultSet.getLong(ID_COL),
                                    resultSet.getLong(CONTACT_ID_COL),
                                    resultSet.getString(COUNTRY_COL),
                                    resultSet.getString(CITY_COL),
                                    resultSet.getString(STREET_COL),
                                    resultSet.getInt(POST_INDEX_COL)))
                            .build();

                    contacts.add(contact);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SQL error [findAll] --- " + e);
        }

        return contacts;
    }


    @Override
    public long save(Contact contact, Connection connection) throws DaoException {

        long contactId = -1;

        try (final PreparedStatement contactStatement = connection.prepareStatement(SAVE_CONTACT_QUERY, Statement.RETURN_GENERATED_KEYS);
             final PreparedStatement addressStatement = connection.prepareStatement(SAVE_ADDRESS_QUERY)) {

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

            try (final ResultSet generatedKeys = contactStatement.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    contactId = generatedKeys.getLong(1);
                }
            }

            Address address = contact.getAddress();

            addressStatement.setString(1, address.getCountry());
            addressStatement.setString(2, address.getCity());
            addressStatement.setString(3, address.getStreet());
            addressStatement.setInt(4, address.getPostIndex());
            addressStatement.setLong(5, contactId);

            addressStatement.executeUpdate();

            logger.info(String.format("Contact was created [id= %d]", contactId));

        } catch (Exception e) {
            throw new DaoException("SQL error [save] --- " , e);
        }

        return contactId;
    }

    @Override
    public void deleteAll(List<Long> idList) {

        try (final Connection connection = ConnectionFactory.createConnection();
             final PreparedStatement contactStatement = connection.prepareStatement(DELETE_ALL_CONTACTS_QUERY);
             final PreparedStatement addressStatement = connection.prepareStatement(DELETE_ALL_ADDRESS_QUERY)) {

            connection.setAutoCommit(false);

            for (Long id : idList) {
                addressStatement.setLong(1, id);
                addressStatement.executeUpdate();
                contactStatement.setLong(1, id);
                contactStatement.executeUpdate();
            }

            connection.commit();
            logger.info(String.format("Contacts were remove [listId= %s]", idList));


        } catch (Exception e) {
            logger.error("SQL error [deleteAll] --- " + e);
            e.printStackTrace();
        }


    }

    @Override
    public long countAll() {

        long count = 0;

        try (final Connection connection = ConnectionFactory.createConnection();
             final ResultSet resultSet = connection.createStatement().executeQuery(COUNT_CONTACTS_QUERY)) {

            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }

        } catch (Exception e) {
            logger.error("SQL error [countAll] --- " + e);
            e.printStackTrace();
        }

        return count;
    }

    private static LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
