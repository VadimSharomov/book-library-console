package main;

import controller.BookLibraryController;
import dao.BookDAO;
import dao.BookMYSQL;
import gui.ConsoleGUI;
import org.slf4j.Logger;
import services.BookService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */
public class Main {
    private final static Logger logger = getLogger(Main.class);

    public static void main(String[] args) {

        Properties propDB = getPropertiesOfDataBase();
        BookDAO bookDAO = new BookMYSQL(propDB.getProperty("url"), propDB.getProperty("username"), propDB.getProperty("password"), propDB.getProperty("table"));
        BookService bookService = new BookService(bookDAO);
        BookLibraryController bookLibraryController = new BookLibraryController(new ConsoleGUI(), new BookService(bookDAO));

        bookLibraryController.start();
    }

    private static Properties getPropertiesOfDataBase() {
        String pathToConfigFile = "src/main/resources/application.properties";
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream(pathToConfigFile);
            properties.load(input);
            logger.info("Config file was read '" + pathToConfigFile + "'");
            input.close();
        } catch (FileNotFoundException e) {
            logger.error("File properties not found in this path: '" + pathToConfigFile + "'", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            logger.error("Error to load from file properties: '" + pathToConfigFile + "'", e.getMessage());
            System.exit(1);
        }
        return properties;
    }
}
