package by.itechart.logic.service.util;

import by.itechart.logic.dao.connection.ConnectionFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileManagerUtil {

    private static final String PROPERTIES_PATH = "/diskPath.properties";
    private static String directoryPath;

    private static final Logger logger = Logger.getLogger(FileManagerUtil.class);

    static {
        loadProperties();
    }

    private static void loadProperties() {

        Properties properties = new Properties();
        try {
            InputStream in = ConnectionFactory.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);

            directoryPath = properties.getProperty("directory.path");

        } catch (IOException e) {
            logger.error("Database properties haven't been loaded !\n" + e.getMessage());
        }
    }

    public static ServletFileUpload createServletFileUpload(HttpServletRequest req) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        File repository = (File) req.getServletContext().getAttribute("javax.servlet.context.tempdir");
        diskFileItemFactory.setRepository(repository);
        return new ServletFileUpload(diskFileItemFactory);
    }

    public static String saveFile(FileItem fileItem, String pathToDirectory, String start) throws Exception {
        final String name = NameGeneratorUtil.generate(start);
        String path = pathToDirectory + File.separator + name + fileItem.getName();
        fileItem.write(new File(path));
        return path;
    }

    public static String createDirectory(String firstName, String lastName, long contactId) throws IOException {
        final String directoryName = String.format("%d_%s_%s", contactId, firstName, lastName);
        final String pathToDirectory = directoryPath + File.separator + directoryName;
        Files.createDirectory(Paths.get(pathToDirectory));
        logger.info(String.format("Directory %s has been created", pathToDirectory));
        return pathToDirectory;
    }

    public static String findDirectoryPath(long contactId) throws IOException {

        try (DirectoryStream<Path> directory = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path path : directory) {
                if (path.getFileName().endsWith(String.valueOf(contactId))) {
                    return path.toString();
                }
            }
        }

        return null;
    }


    public static void cleanDirectory(String path) throws IOException {
        if (path != null) {
            FileUtils.cleanDirectory(new File(path));
            logger.info(String.format("Directory %s has been cleaned", path));
        }
    }

}
