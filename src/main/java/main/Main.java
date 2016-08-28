package main;

import controller.LibraryController;
import dao.BookDAO;
import dao.BookMYSQL;
import controller.BookManager;
import gui.ConsoleGUI;
import gui.GUI;
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
        GUI gui = new ConsoleGUI();
        BookManager bookManager = new BookManager(bookService, gui);
        LibraryController libraryController = new LibraryController(bookService, gui, bookManager);

        libraryController.start();
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
