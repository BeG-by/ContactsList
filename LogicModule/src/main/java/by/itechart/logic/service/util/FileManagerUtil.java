package by.itechart.logic.service.util;

import by.itechart.logic.dao.connection.ConnectionFactory;
import by.itechart.logic.exception.LoadPropertiesException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class FileManagerUtil {

    private static final String PROPERTIES_PATH = "/diskPath.properties";
    private static String directoryPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManagerUtil.class);

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
            LOGGER.error("Database properties haven't been loaded !", e);
        }
    }


    public static String saveImg(byte[] bytes, String pathToDirectory, String fileName, String prefix) throws Exception {
        String path = pathToDirectory + File.separator + prefix + fileName;
        FileUtils.writeByteArrayToFile(new File(path), bytes);
        return path;
    }

    public static String saveFile(byte[] bytes, String pathToDirectory, String id, String fileName, String prefix) throws Exception {
        String path = pathToDirectory + File.separator + id + prefix + fileName;
        FileUtils.writeByteArrayToFile(new File(path), bytes);
        return path;
    }


    public static String createDirectory(long contactId) throws IOException, LoadPropertiesException {

        if (directoryPath == null) {
            throw new LoadPropertiesException();
        }

        final String pathToDirectory = directoryPath + File.separator + contactId;
        Files.createDirectory(Paths.get(pathToDirectory));
        LOGGER.info("Directory {} has been created", pathToDirectory);
        return pathToDirectory;
    }

    public static String findDirectory(long contactId) throws IOException, LoadPropertiesException {

        if (directoryPath == null) {
            throw new LoadPropertiesException();
        }

        try (DirectoryStream<Path> directory = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path path : directory) {
                if (path.getFileName().toString().equals(String.valueOf(contactId))) {
                    return path.toString();
                }
            }
        }

        return null;
    }

    public static String findPathToFile(long contactId, String fileNameId) throws IOException, LoadPropertiesException {

        if (directoryPath == null) {
            throw new LoadPropertiesException();
        }

        final String path = findDirectory(contactId);
        if (path != null) {
            try (DirectoryStream<Path> userDirectory = Files.newDirectoryStream(Paths.get(path))) {
                for (Path file : userDirectory) {
                    final String id = file.getFileName().toString().split("_")[0];
                    if (id.equals(fileNameId)) {
                        return file.toString();
                    }
                }
            }
        }


        return null;
    }


    public static void deleteFile(String path) throws IOException {
        FileUtils.forceDelete(new File(path));
    }


    public static String FileToBase64(String path) throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File(path));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void deleteDirectory(long contactId) throws IOException, LoadPropertiesException {
        final String directory = findDirectory(contactId);

        if (directory != null) {
            FileUtils.deleteDirectory(new File(directory));
        }

    }

}
