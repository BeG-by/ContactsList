package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.AttachmentDAO;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAOImpl implements AttachmentDAO {

    private Connection connection;

    private static final String ID_COL = "id";
    private static final String CONTACT_ID_COL = "contact_id";
    private static final String FILE_NAME_COL = "file_name";
    private static final String LOAD_DATE_COL = "date_of_load";
    private static final String COMMENT_COL = "comment";

    private static final String ATTACHMENTS_SAVE_QUERY = String.format("INSERT INTO attachment (%s,%s,%s,%s) VALUES (?,?,?,?) ",
            CONTACT_ID_COL, FILE_NAME_COL, LOAD_DATE_COL, COMMENT_COL);

    private static final String DELETE_BY_ID_QUERY = String.format("DELETE FROM attachment WHERE %s=?;", ID_COL);

    private static final String FIND_BY_CONTACT_ID_QUERY = String.format("SELECT * FROM attachment WHERE %s=?;", CONTACT_ID_COL);

    private static final String UPDATE_BY_ID_QUERY = String.format("UPDATE attachment SET %s=?, %s=?, %s=? WHERE %s=?;",
            FILE_NAME_COL, LOAD_DATE_COL, COMMENT_COL, ID_COL);

    private static final Logger logger = Logger.getLogger(AttachmentDAOImpl.class);

    public AttachmentDAOImpl() {
    }

    public AttachmentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Long> saveAll(List<Attachment> attachments) throws DaoException {

        List<Long> idList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ATTACHMENTS_SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            for (Attachment attachment : attachments) {
                statement.setLong(1, attachment.getContactId());
                statement.setString(2, attachment.getFileName());

                if (attachment.getDateOfLoad() != null) {
                    statement.setDate(3, Date.valueOf(attachment.getDateOfLoad()));
                } else {
                    statement.setNull(3, Types.DATE);
                }

                statement.setString(4, attachment.getComment());
                statement.executeUpdate();

                try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        idList.add(generatedKeys.getLong(1));
                    }
                }
            }

            logger.info(String.format("Attachments were saved, attachmentsList= [%s]", attachments));
            return idList;

        } catch (Exception e) {
            logger.error("Saving attachments has been failed --- ", e);
            throw new DaoException("Incorrect attachments data !");
        }
    }

    @Override
    public void deleteAllById(List<Long> attachmentIdList) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {

            for (Long id : attachmentIdList) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }

            logger.info(String.format("Attachments list were removed %s", attachmentIdList));

        } catch (Exception e) {
            logger.error("Deleting attachments has been failed --- ", e);
            throw new DaoException("Incorrect attachments data !");
        }
    }

    @Override
    public void updateAll(List<Attachment> attachments) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {

            for (Attachment att : attachments) {
                statement.setString(1, att.getFileName());

                if (att.getDateOfLoad() != null) {
                    statement.setDate(2, Date.valueOf(att.getDateOfLoad()));
                } else {
                    statement.setNull(2, Types.DATE);
                }

                statement.setString(3, att.getComment());
                statement.setLong(4, att.getId());
                statement.executeUpdate();
            }

            logger.info(String.format("Attachments list were updated %s", attachments));

        } catch (Exception e) {
            logger.error("Deleting attachments has been failed --- ", e);
            throw new DaoException("Incorrect attachments data !");
        }
    }

    @Override
    public List<Attachment> findAllByContactId(long contactId) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_CONTACT_ID_QUERY)) {

            statement.setLong(1, contactId);

            List<Attachment> attachmentList = new ArrayList<>();

            try (final ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    final long id = resultSet.getLong(ID_COL);
                    final String fileName = resultSet.getString(FILE_NAME_COL);
                    final LocalDate date = convertToLocalDate(resultSet.getDate(LOAD_DATE_COL));
                    final String comment = resultSet.getString(COMMENT_COL);

                    attachmentList.add(new Attachment(id, contactId, fileName, date, comment));
                }
            }

            return attachmentList;

        } catch (Exception e) {
            logger.error("Finding attachment list has been failed --- ", e);
            throw new DaoException("Incorrect attachment list data !", e);
        }
    }

    private static LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        if (dateToConvert != null) {
            return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
        }
        return null;
    }


}



