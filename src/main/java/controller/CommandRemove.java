package controller;

import entity.Book;
import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

import java.util.List;

/**
 * @author Vadim Sharomov
 */
public class CommandRemove implements Command {
    private final String name = "remove";
    private String description = "remove {Name of Book}              (For removing book)";
    private BookDAOService bookDAOService;
    private GUI gui;

    public CommandRemove(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;
    }

    @Override
    public void execute(UserQuery userQuery) {
        List<Book> listBooks = bookDAOService.getBookByName(userQuery.getBook());
        int numberSelectedBook = 0;

        if (listBooks.size() > 0) {
            if (listBooks.size() > 1) {
                gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                numberSelectedBook = gui.chooseBook(listBooks);
            }
            String wordNot = " not";
            if (bookDAOService.remove(listBooks.get(numberSelectedBook)) > 0) {
                wordNot = "";
            }
            gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was" + wordNot + " removed\n");
        } else {
            gui.showMessage("Not found this book:\n" + userQuery.getBook());
        }
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
