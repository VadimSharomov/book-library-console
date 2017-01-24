package controller;

import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

/**
 * @author Vadim Sharomov
 */
public class CommandExit implements Command {
    private final String name = "exit";
    private String description = "exit                               (For exit from library)";
    private BookDAOService bookDAOService;
    private GUI gui;

    public CommandExit(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;
    }

    @Override
    public void execute(UserQuery userQuery) {
        userQuery.setExitFromLibrary(true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
