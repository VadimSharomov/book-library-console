package main;

import controller.ControllerLibrary;
import gui.GUIConsole;
import services.BookDAO;
import services.BookDAOImplMYSQL;
import services.BookDAOService;

import java.util.Properties;

/**
 * @author Vadim Sharomov
 */
public class Main {
    public static void main(String[] args) {

        Properties propDB = (new ReadConfig()).getProperties();
        BookDAO bookDAO = new BookDAOImplMYSQL(propDB.getProperty("url"), propDB.getProperty("username"), propDB.getProperty("password"), propDB.getProperty("table"));

        ControllerLibrary controllerLibrary = new ControllerLibrary(new BookDAOService(bookDAO), new GUIConsole());
        controllerLibrary.start();
    }
}