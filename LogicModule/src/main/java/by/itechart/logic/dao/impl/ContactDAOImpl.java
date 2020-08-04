package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.ContactDAO;
import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.entity.Address;
import by.itechart.logic.entity.Contact;
import by.itechart.logic.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private Connection connection;

    public static final String ID_COL = "id";
    public static final String FIRST_NAME_COL = "first_name";
    public static final String LAST_NAME_COL = "last_name";
    public static final String MIDDLE_NAME_COL = "middle_name";
    public static final String BIRTHDAY_COL = "birthday";
    public static final String SEX_COL = "sex";
    public static final String NATIONALITY_COL = "nationality";
    public static final String MARITAL_STATUS_COL = "marital_status";
    private static final String WEBSITE_URL_COL = "url";
    private static final String EMAIL_COL = "email";
    private static final String CURRENT_JOB_COL = "job";

    public static final String COUNTRY_COL = "country";
    public static final String CITY_COL = "city";
    public static final String STREET_COL = "street";
    public static final String POST_INDEX_COL = "post_index";
    public static final String CONTACT_ID_COL = "contact_id";

    public ContactDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String FIND_ALL_CONTACTS_QUERY = String.format("SELECT * FROM contact c LEFT JOIN address a ON c.%s = a.%s ORDER BY c.id LIMIT ? , ?;",
            ID_COL, CONTACT_ID_COL);

    private static final String SAVE_CONTACT_QUERY = String.format("INSERT INTO contact (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?,?,?);",
            FIRST_NAME_COL, LAST_NAME_COL, MIDDLE_NAME_COL, BIRTHDAY_COL, SEX_COL, NATIONALITY_COL, MARITAL_STATUS_COL, WEBSITE_URL_COL, EMAIL_COL, CURRENT_JOB_COL);

    private static final String SAVE_ADDRESS_QUERY = String.format("INSERT INTO address (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?);",
            COUNTRY_COL, CITY_COL, STREET_COL, POST_INDEX_COL, CONTACT_ID_COL);

    private static final String UPDATE_CONTACT_QUERY = String.format("UPDATE contact SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;",
            FIRST_NAME_COL, LAST_NAME_COL, MIDDLE_NAME_COL, BIRTHDAY_COL, SEX_COL, NATIONALITY_COL, MARITAL_STATUS_COL, WEBSITE_URL_COL, EMAIL_COL, CURRENT_JOB_COL, ID_COL);

    private static final String UPDATE_ADDRESS_QUERY = String.format("UPDATE address SET %s=?, %s=?, %s=?, %s=? WHERE %s=?;",
            COUNTRY_COL, CITY_COL, STREET_COL, POST_INDEX_COL, CONTACT_ID_COL);

    private static final String DELETE_CONTACT_QUERY = String.format("DELETE FROM contact WHERE %s = ?;", ID_COL);
    private static final String DELETE_ADDRESS_QUERY = String.format("DELETE FROM address WHERE %s = ?;", CONTACT_ID_COL);

    private static final String COUNT_CONTACTS_QUERY = "SELECT count(*) FROM contact";

    private static final String FIND_BY_ID_QUERY = String.format("SELECT * FROM contact c LEFT JOIN address a ON c.%s = a.%s WHERE c.%s=?;",
            ID_COL, CONTACT_ID_COL, ID_COL);

    private static final String FIND_BY_EMAIL_QUERY = String.format("SELECT * FROM contact c LEFT JOIN address a ON c.%s = a.%s WHERE c.%s=?;",
            ID_COL, CONTACT_ID_COL, EMAIL_COL);

    private static final String FIND_BY_BIRTHDAY_QUERY = String.format("SELECT * FROM contact c LEFT JOIN address a ON c.%s = a.%s WHERE day(%s)=? AND month(%s)=?;",
            ID_COL, CONTACT_ID_COL, BIRTHDAY_COL, BIRTHDAY_COL);


    @Override
    public List<Contact> findAllWithLimit(int page, int pageLimit) throws DaoException {

        List<Contact> contacts = new ArrayList<>();

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ALL_CONTACTS_QUERY)) {

            statement.setInt(1, (page - 1) * pageLimit);
            statement.setInt(2, pageLimit);

            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Contact contact = buildContact(resultSet);
                contacts.add(contact);
            }


        } catch (Exception e) {
            throw new DaoException(e);
        }

        return contacts;
    }


    @Override
    public long save(Contact contact) throws DaoException {

        long contactId = -1;

        try (final PreparedStatement contactStatement = connection.prepareStatement(SAVE_CONTACT_QUERY, Statement.RETURN_GENERATED_KEYS);
             final PreparedStatement addressStatement = connection.prepareStatement(SAVE_ADDRESS_QUERY)) {

            buildContactStatement(contactStatement, contact);
            contactStatement.executeUpdate();

            final ResultSet generatedKeys = contactStatement.getGeneratedKeys();

            while (generatedKeys.next()) {
                contactId = generatedKeys.getLong(1);
            }

            Address address = contact.getAddress();
            buildAddressStatement(addressStatement, address, contactId);
            addressStatement.executeUpdate();


        } catch (Exception e) {
            throw new DaoException(e);
        }

        return contactId;
    }

    @Override
    public void update(Contact contact) throws DaoException {

        try (final PreparedStatement contactStatement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
             final PreparedStatement addressStatement = connection.prepareStatement(UPDATE_ADDRESS_QUERY)) {

            buildContactStatement(contactStatement, contact);
            contactStatement.setLong(11, contact.getId());
            contactStatement.executeUpdate();

            Address address = contact.getAddress();
            buildAddressStatement(addressStatement, address, contact.getId());
            addressStatement.executeUpdate();


        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(long contactId) throws DaoException {

        try (final PreparedStatement contactStatement = connection.prepareStatement(DELETE_CONTACT_QUERY);
             final PreparedStatement addressStatement = connection.prepareStatement(DELETE_ADDRESS_QUERY)) {

            addressStatement.setLong(1, contactId);
            addressStatement.executeUpdate();
            contactStatement.setLong(1, contactId);
            contactStatement.executeUpdate();


        } catch (Exception e) {
            throw new DaoException(e);
        }


    }

    @Override
    public long countAll() throws DaoException {

        long count = 0;

        try (final Connection connection = ConnectionFactory.createConnection();
             final ResultSet resultSet = connection.createStatement().executeQuery(COUNT_CONTACTS_QUERY)) {

            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }

            return count;

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Contact findById(long contactId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            statement.setLong(1, contactId);

            final ResultSet resultSet = statement.executeQuery();

            Contact contact = null;

            while (resultSet.next()) {
                contact = buildContact(resultSet);
            }

            return contact;


        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Contact findByEmail(String email) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_QUERY)) {

            statement.setString(1, email);

            final ResultSet resultSet = statement.executeQuery();

            Contact contact = null;

            while (resultSet.next()) {
                contact = buildContact(resultSet);
            }

            return contact;


        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Contact> findByTodayDate(LocalDate date) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_BY_BIRTHDAY_QUERY)) {

            List<Contact> contacts = new ArrayList<>();

            final int day = date.getDayOfMonth();
            final int month = date.getMonth().getValue();

            statement.setInt(1, day);
            statement.setInt(2, month);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contacts.add(buildContact(resultSet));
            }

            return contacts;

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Contact> findAllWithFilter(String searchSql) throws DaoException {

        List<Contact> contacts = new ArrayList<>();

        try (final Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery(searchSql);

            while (resultSet.next()) {
                contacts.add(buildContact(resultSet));
            }

            return contacts;

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public long countAllWithFilter(String sql) throws DaoException {

        long count = 0;

        try (final Connection connection = ConnectionFactory.createConnection();
             final ResultSet resultSet = connection.createStatement().executeQuery(sql)) {

            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }

            return count;

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }


    private Contact buildContact(ResultSet resultSet) throws SQLException {

        return new Contact.Builder()
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
    }

    private void buildContactStatement(PreparedStatement statement, Contact contact) throws SQLException {

        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getLastName());
        statement.setString(3, contact.getMiddleName());

        if (contact.getBirthday() != null) {
            statement.setDate(4, Date.valueOf(contact.getBirthday()));
        } else {
            statement.setNull(4, Types.DATE);
        }

        statement.setString(5, contact.getSex());
        statement.setString(6, contact.getNationality());
        statement.setString(7, contact.getMaritalStatus());
        statement.setString(8, contact.getUrlWebSite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getCurrentJob());

    }

    private void buildAddressStatement(PreparedStatement statement, Address address, long contactId) throws SQLException {

        statement.setString(1, address.getCountry());
        statement.setString(2, address.getCity());
        statement.setString(3, address.getStreet());
        statement.setObject(4, address.getPostIndex(), Types.INTEGER);
        statement.setLong(5, contactId);

    }


    private LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        if (dateToConvert != null) {
            return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
        }
        return null;
    }

}
