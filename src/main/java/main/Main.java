package main;

import controller.BookLibraryController;
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

        Properties dbPr = initialiseDataBase();

        BookLibraryController bookLibraryController = new BookLibraryController(new ConsoleGUI(), new BookService(new BookMYSQL(dbPr.getProperty("url"), dbPr.getProperty("username"), dbPr.getProperty("password"), dbPr.getProperty("table"))));
        bookLibraryController.start();
    }

    private static Properties initialiseDataBase() {
        Properties properties = new Properties();
        String pathToConfigFile = "src/main/resources/application.properties";
        try {
            InputStream input = new FileInputStream(pathToConfigFile);
            properties.load(input);
            logger.info("*** Config file has read '" + pathToConfigFile + "'");
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
