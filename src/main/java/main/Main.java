package main;

import controller.BookCommandManager;
import controller.LibraryController;
import dao.BookDAO;
import dao.BookDAOImplMYSQL;
import gui.ConsoleGUI;
import gui.GUI;
import services.BookDAOService;

import java.util.Properties;

/**
 * @author Vadim Sharomov
 */
public class Main {
    public static void main(String[] args) {

        Properties propDB = (new ReadConfig()).getProperties();
        BookDAO bookDAO = new BookDAOImplMYSQL(propDB.getProperty("url"), propDB.getProperty("username"), propDB.getProperty("password"), propDB.getProperty("table"));

        BookDAOService bookDAOService = new BookDAOService(bookDAO);
        GUI gui = new ConsoleGUI();

        BookCommandManager bookCommandManager = new BookCommandManager(bookDAOService, gui);
        LibraryController libraryController = new LibraryController(bookCommandManager, gui);

        libraryController.start();
    }
}
