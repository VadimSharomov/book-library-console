package controller;

import entity.UserQuery;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;

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
        String userQueryStr;
        while (true) {
            gui.showMainMenu(bookCommandManager.getCommandsMenu());
            userQueryStr = gui.getUserRequest();
            if (isRecognizeCommand(userQueryStr)) {
                break;
            } else {
                gui.showMessage("Unrecognized command: '" + userQueryStr + "'\n");
            }
        }
        if (LibraryCommand.EXIT.getValue().equals(userQueryStr.toLowerCase())) {
            return new UserQuery(true);
        }

        String libraryCommand = getLibraryCommand(userQueryStr);

        String queriedBook = userQueryStr.replace(libraryCommand, "").trim();
        Book book = queriedBook(queriedBook);

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

    private Book queriedBook(String queryStr) {
        String bookName;
        String authorName = "";
        String splitterQuotes = "\"";
        if (queryStr.split(splitterQuotes).length > 1) {
            authorName = queryStr.split(splitterQuotes)[0].trim();
            bookName = queryStr.split(splitterQuotes)[1].trim();
        } else {
            bookName = queryStr;
        }
        return new Book(bookName, authorName);
    }

    private boolean isRecognizeCommand(String queryStr) {
        if (LibraryCommand.EXIT.getValue().equals(queryStr.toLowerCase())) return true;

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