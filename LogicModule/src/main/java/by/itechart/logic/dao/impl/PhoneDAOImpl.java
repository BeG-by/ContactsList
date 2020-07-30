package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    private Connection connection;

    private static final String ID_COL = "id";
    private static final String CONTACT_ID_COL = "contact_id";
    private static final String COUNTRY_CODE_COL = "country_code";
    private static final String OPERATOR_CODE_COL = "operator_code";
    private static final String NUMBER_COL = "number";
    private static final String TYPE_COL = "type";
    private static final String COMMENT_COL = "comment";

    private static final String SAVE_PHONE_QUERY = String.format("INSERT INTO phone (%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?);",
            CONTACT_ID_COL, COUNTRY_CODE_COL, OPERATOR_CODE_COL, NUMBER_COL, TYPE_COL, COMMENT_COL);

    private static final String DELETE_PHONE_LIST_QUERY = String.format("DELETE FROM phone WHERE %s=?;", CONTACT_ID_COL);

    private static final String FIND_BY_CONTACT_ID_QUERY = String.format("SELECT * FROM phone WHERE %s=?;", CONTACT_ID_COL);


    public PhoneDAOImpl() {
    }

    public PhoneDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Phone phone) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(SAVE_PHONE_QUERY)) {

            statement.setLong(1, phone.getContactId());
            statement.setInt(2, phone.getCountryCode());
            statement.setInt(3, phone.getOperatorCode());
            statement.setInt(4, phone.getNumber());
            statement.setString(5, phone.getType());
            statement.setString(6, phone.getComment());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void deleteByContactId(long contactId) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_PHONE_LIST_QUERY)) {

            statement.setLong(1, contactId);
            statement.executeUpdate();


        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Phone> findByContactId(long contactId) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_CONTACT_ID_QUERY)) {

            statement.setLong(1, contactId);

            List<Phone> phones = new ArrayList<>();

            try (final ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    final long id = resultSet.getLong(ID_COL);
                    final int country = resultSet.getInt(COUNTRY_CODE_COL);
                    final int operator = resultSet.getInt(OPERATOR_CODE_COL);
                    final int number = resultSet.getInt(NUMBER_COL);
                    final String type = resultSet.getString(TYPE_COL);
                    final String comment = resultSet.getString(COMMENT_COL);

                    phones.add(new Phone(id, contactId, country, operator, number, type, comment));
                }
            }

            return phones;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
