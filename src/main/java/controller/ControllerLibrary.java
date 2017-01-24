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

public class ControllerLibrary {
    private final static Logger logger = getLogger(ControllerLibrary.class);
    private GUI gui;
    private Map<String, Command> commands = new HashMap<>();

    public ControllerLibrary(BookDAOService bookDAOService, GUI gui) {
        this.gui = gui;
        Command command;
        Set<Class<? extends Command>> commandClasses = new Reflections().getSubTypesOf(Command.class);
        for (Class commandClass : commandClasses) {
            try {
                command = (Command) commandClass.getConstructor(BookDAOService.class, GUI.class).newInstance(bookDAOService, gui);
                commands.put(command.getName(), command);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                logger.info("Unable create instance of command class '" + commandClass.getName() + "'");
            }
        }
    }

    public void start() {
        gui.showMessage("\nWelcome to our library!");

        UserQuery userQuery;
        do {
            showMainMenu();
            userQuery = getQueryFromUser();
            if (isCorrectUserQuery(userQuery)) {
                commands.get(userQuery.getCommandLibrary()).execute(userQuery);
            }
        } while (!userQuery.isExitFromLibrary());

        gui.showMessage("\nBest regards!");
    }

    private boolean isCorrectUserQuery(UserQuery userQuery) {
        return commands.containsKey(userQuery.getCommandLibrary());
    }

    private UserQuery getQueryFromUser() {
        String userQueryStr;
        while (true) {
            userQueryStr = gui.getUserRequest();
            if (!isRecognizeCommand(userQueryStr)) {
                gui.showMessage("Unrecognized command: '" + userQueryStr + "'\n");
                continue;
            }
            break;
        }

        String libraryCommand = getLibraryCommand(userQueryStr);
        Book book = getQueriedBook(userQueryStr.replace(libraryCommand, "").trim());

        return new UserQuery(libraryCommand, book);
    }

    private void showMainMenu() {
        List<String> mainMenu = new ArrayList<>();
        for (Command command : commands.values()) {
            mainMenu.add(command.getDescription());
            Collections.sort(mainMenu);
        }
        gui.showMainMenu(mainMenu);
    }

    private String getLibraryCommand(String queryStr) {
        for (Command com : commands.values()) {
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
        String[] splittedWords = queryStr.split(" ");
        if (splittedWords.length < 1) return false;

        for (Command com : commands.values()) {
            if (queryStr.startsWith(com.getName())) {
                return true;
            }
        }
        return false;
    }
}