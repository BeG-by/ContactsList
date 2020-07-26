package by.itechart.logic.dao.impl;

import by.itechart.logic.dao.AttachmentDAO;
import by.itechart.logic.entity.Attachment;
import by.itechart.logic.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

public class AttachmentDAOImpl implements AttachmentDAO {

    private static final String CONTACT_ID_COL = "contact_id";
    private static final String FILE_NAME_COL = "file_name";
    private static final String LOAD_DATE_COL = "date_of_load";
    private static final String COMMENT_COL = "comment";

    private static final String ATTACHMENTS_SAVE_QUERY = String.format("INSERT INTO attachment (%s,%s,%s,%s) VALUES (?,?,?,?) ",
            CONTACT_ID_COL, FILE_NAME_COL, LOAD_DATE_COL, COMMENT_COL);

    private static final String DELETE_ATTACHMENT_LIST_QUERY = String.format("DELETE FROM attachment WHERE %s=?;", CONTACT_ID_COL);

    private static final Logger logger = Logger.getLogger(AttachmentDAOImpl.class);


    @Override
    public void save(List<Attachment> attachments, Connection connection) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(ATTACHMENTS_SAVE_QUERY)) {


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
            }

            logger.info(String.format("Attachments were saved, attachmentsList= [%s]", attachments));

        } catch (Exception e) {
            logger.error("Saving attachments has been failed --- ", e);
            throw new DaoException("Incorrect attachments data !");
        }
    }

    @Override
    public void deleteAll(long contactId, Connection connection) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT_LIST_QUERY)) {

            statement.setLong(1 , contactId);
            statement.executeUpdate();

            logger.info(String.format("Attachments for contact with id [%s] were removed", contactId));

        } catch (Exception e) {
            logger.error("Deleting attachments has been failed --- ", e);
            throw new DaoException("Incorrect attachments data !");
        }
    }
}
