package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.dao.util.ConnectionFactory;
import by.itechart.logic.entity.Phone;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    private static final String SAVE_PHONE_QUERY = "INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES (?,?,?,?,?,?)";

    private static final Logger logger = Logger.getLogger(PhoneDAOImpl.class);

    @Override
    public boolean save(List<Phone> phoneList) {

        try (final Connection connection = ConnectionFactory.createConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_PHONE_QUERY);

            connection.setAutoCommit(false);

            for (Phone phone : phoneList) {
                preparedStatement.setLong(1, phone.getContactId());
                preparedStatement.setInt(2, phone.getCountryCode());
                preparedStatement.setInt(3, phone.getOperatorCode());
                preparedStatement.setInt(4, phone.getNumber());
                preparedStatement.setString(5, phone.getType());
                preparedStatement.setString(6, phone.getComment());
                preparedStatement.executeUpdate();
            }

            connection.commit();

            logger.info(String.format("Phones was created phoneList= [%s]", phoneList));

        } catch (SQLException e) {
            logger.error("SQL error [save] --- " + e);
            return false;
        }

        return true;
    }
}
