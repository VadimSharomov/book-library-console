package controller;

import entity.UserQuery;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.LibraryCommand;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */

public class LibraryController {
    private final static Logger logger = getLogger(LibraryController.class);

    private GUI gui;
    private BookCommandManager bookCommandManager;

    public LibraryController(BookCommandManager bookCommandManager, GUI gui) {
        this.bookCommandManager = bookCommandManager;
        this.gui = gui;
    }

    public void start() {
        String greeting = "\nWelcome to our library!";
        gui.showMessage(greeting);

        UserQuery userQuery;
        while (!(userQuery = getQueryFromUser()).isExitFromLibrary()) {
            bookCommandManager.doIt(userQuery);
        }

        String farewell = "\nBest regards!";
        gui.showMessage(farewell);
    }

    private UserQuery getQueryFromUser() {
        String queryStr;
        while (true) {
            queryStr = gui.showMainMenu(bookCommandManager.getMainMenu());
            if (isRecognizeCommand(queryStr)) {
                break;
            } else {
                gui.showMessage("Unrecognized command: '" + queryStr + "'\n");
            }
        }
        if ("exit".equals(queryStr.trim().toLowerCase())) {
            return new UserQuery(true);
        }
        String libraryCommand = getLibraryCommand(queryStr);
        queryStr = queryStr.replace(libraryCommand, "").trim();
        Book book = getSelectedBook(queryStr);

        return new UserQuery(libraryCommand, book);
    }

    private String getLibraryCommand(String queryStr) {
        for (LibraryCommand libraryCommand: LibraryCommand.values()) {
            if (queryStr.startsWith(libraryCommand.getValue())) {
                return libraryCommand.getValue();
            }
        }
        logger.error("Unknown command: '" + queryStr + "'");
        return "Unknown command";
    }

    private Book getSelectedBook(String queryStr) {
        String authorName, bookName;
        if (queryStr.split("\"").length > 1) {
            authorName = queryStr.split("\"")[0].trim();
            bookName = queryStr.split("\"")[1].trim();
        } else {
            authorName = "";
            bookName = queryStr;
        }
        return new Book(bookName, authorName);
    }

    private boolean isRecognizeCommand(String queryStr) {
        if ("exit".equals(queryStr)) return true;
        String[] arWords = queryStr.split(" ");
        if (arWords.length < 1) return false;
        for (LibraryCommand libraryCommand: LibraryCommand.values()) {
            if (queryStr.startsWith(libraryCommand.getValue())) {
                return true;
            }
        }
        return false;
    }
}