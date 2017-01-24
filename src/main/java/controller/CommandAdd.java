package controller;

import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

/**
 * @author Vadim Sharomov
 */
public class CommandAdd implements Command {
    private final String name = "add";
    private String description = "add {A.AuthorName \"Book Name\"}     (For adding new book)";
    private BookDAOService bookDAOService;
    private GUI gui;

    public CommandAdd(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;
    }

    @Override
    public void execute(UserQuery userQuery) {
        String wordNot = " not";
        if (bookDAOService.add(userQuery.getBook()) > 0) {
            wordNot = "";
        }
        gui.showMessage("Book " + userQuery.getBook() + " was" + wordNot + " added\n");
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
