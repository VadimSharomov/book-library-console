package controller;

import entity.Book;
import entity.UserQuery;
import gui.GUI;
import org.reflections.Reflections;
import org.slf4j.Logger;
import services.BookDAOService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */

public class LibraryController {
    private final static Logger logger = getLogger(LibraryController.class);
    private GUI gui;
    private Map<String, Command> commandMap = new HashMap<>();

    public LibraryController(BookDAOService bookDAOService, GUI gui) {
        this.gui = gui;
        Set<Class<? extends Command>> commandClasses = new Reflections().getSubTypesOf(Command.class);
        for (Class commandClass : commandClasses) {
            try {
                Command command = (Command) commandClass.getConstructor(BookDAOService.class, GUI.class).newInstance(bookDAOService, gui);
                commandMap.put(command.getName(), command);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                logger.info("Unable create instance of command class '" + commandClass.getName() + "'");
            }
        }
    }

    public void start() {
        String greeting = "\nWelcome to our library!";
        gui.showMessage(greeting);

        UserQuery userQuery;
        do {
            showMainMenu();
            userQuery = getQueryFromUser();
            if (commandMap.containsKey(userQuery.getLibraryCommand())) {
                commandMap.get(userQuery.getLibraryCommand()).execute(userQuery);
            }
        } while (!(userQuery).isExitFromLibrary());

        String farewell = "\nBest regards!";
        gui.showMessage(farewell);
    }

    private UserQuery getQueryFromUser() {
        String userQueryStr;
        while (true) {
            userQueryStr = gui.getUserRequest();
            if (isRecognizeCommand(userQueryStr)) {
                break;
            } else {
                gui.showMessage("Unrecognized command: '" + userQueryStr + "'\n");
            }
        }

        String libraryCommand = getLibraryCommand(userQueryStr);

        String queriedBook = userQueryStr.replace(libraryCommand, "").trim();
        Book book = getQueriedBook(queriedBook);

        return new UserQuery(libraryCommand, book);
    }

    private void showMainMenu() {
        List<String> mainMenu = new ArrayList<>();
        for (Command command : commandMap.values()) {
            mainMenu.add(command.getDescription());
            Collections.sort(mainMenu);
        }
        gui.showMainMenu(mainMenu);
    }

    private String getLibraryCommand(String queryStr) {
        for (Command com : commandMap.values()) {
            if (queryStr.startsWith(com.getName())) {
                return com.getName();
            }
        }
        logger.error("Unknown command: '" + queryStr + "'");
        return "Unknown command";
    }

    private Book getQueriedBook(String queryStr) {
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
        String[] arWords = queryStr.split(" ");
        if (arWords.length < 1) return false;

        for (Command com : commandMap.values()) {
            if (queryStr.startsWith(com.getName())) {
                return true;
            }
        }
        return false;
    }
}