package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.PhoneDAO;
import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {

    private static final String CONTACT_ID_COL = "contact_id";
    private static final String COUNTRY_CODE_COL = "country_code";
    private static final String OPERATOR_CODE_COL = "operator_code";
    private static final String NUMBER_COL = "number";
    private static final String TYPE_COL = "type";
    private static final String COMMENT_COL = "comment";

    private static final String SAVE_PHONE_QUERY = String.format("INSERT INTO phone (%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?);",
            CONTACT_ID_COL, COUNTRY_CODE_COL, OPERATOR_CODE_COL, NUMBER_COL, TYPE_COL, COMMENT_COL);

    private static final String DELETE_PHONE_LIST_QUERY = String.format("DELETE FROM phone WHERE %s=?;", CONTACT_ID_COL);

    private static final Logger logger = Logger.getLogger(PhoneDAOImpl.class);

    @Override
    public void save(List<Phone> phoneList, Connection connection) throws DaoException {

        try (final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_PHONE_QUERY)) {

            for (Phone phone : phoneList) {
                preparedStatement.setLong(1, phone.getContactId());
                preparedStatement.setInt(2, phone.getCountryCode());
                preparedStatement.setInt(3, phone.getOperatorCode());
                preparedStatement.setInt(4, phone.getNumber());
                preparedStatement.setString(5, phone.getType());
                preparedStatement.setString(6, phone.getComment());
                preparedStatement.executeUpdate();
            }

            logger.info(String.format("Phones were saved, phoneList= [%s]", phoneList));

        } catch (Exception e) {
            logger.error("Saving phone list has been failed --- ", e);
            throw new DaoException("Incorrect phone list data !", e);
        }

    }

    @Override
    public void deleteAll(long contactId, Connection connection) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PHONE_LIST_QUERY)) {

            preparedStatement.setLong(1, contactId);
            preparedStatement.executeUpdate();

            logger.info(String.format("Phones for contact with id [%s] were removed", contactId));

        } catch (Exception e) {
            logger.error("Deleting phone list has been failed --- ", e);
            throw new DaoException("Incorrect phone list data !", e);
        }

    }

}
