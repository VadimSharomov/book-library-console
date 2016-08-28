package main;

import controller.LibraryController;
import dao.BookDAO;
import dao.BookMYSQL;
import controller.BookCommandManager;
import gui.ConsoleGUI;
import gui.GUI;
import org.slf4j.Logger;
import services.BookDAOService;

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

        Properties propDB = getPropertiesOfDataBase(args);
        BookDAO bookDAO = new BookMYSQL(propDB.getProperty("url"), propDB.getProperty("username"), propDB.getProperty("password"), propDB.getProperty("table"));

        BookDAOService bookDAOService = new BookDAOService(bookDAO);

        GUI gui = new ConsoleGUI();

        BookCommandManager bookCommandManager = new BookCommandManager(bookDAOService, gui);

        LibraryController libraryController = new LibraryController(bookCommandManager, gui);

        libraryController.start();
    }

    private static Properties getPropertiesOfDataBase(String[] args) {
        String pathToConfigFile = "src/main/resources/application.properties";
        if (args.length == 0){
            logger.error("In arguments JVM not found the path to file.properties: 'application.properties'!");
            logger.info("It will use as default the path: '" + pathToConfigFile + "'");
        } else {
            pathToConfigFile = args[0];
        }

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
