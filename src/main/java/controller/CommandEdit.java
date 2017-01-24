package controller;

import entity.Book;
import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

import java.util.List;

/**
 * @author Vadim Sharomov
 */
public class CommandEdit implements Command {
    private final String name = "edit";
    private String description = "edit {Name of Book}                (For editing book)";
    private BookDAOService bookDAOService;
    private GUI gui;

    public CommandEdit(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;
    }

    @Override
    public void execute(UserQuery userQuery) {
        List<Book> listBooks = bookDAOService.getBookByName(userQuery.getBook().getName());

        if (listBooks.size() > 0) {
            int numberSelectedBook = 0;
            if (listBooks.size() > 1) {
                gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                numberSelectedBook = gui.chooseBook(listBooks);
            }

            String newNameBook = gui.enterNewNameBook();

            String wordNot = " not";
            if (bookDAOService.editBook(listBooks.get(numberSelectedBook), newNameBook) > 0) {
                wordNot = "";
            }

            gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was" + wordNot + " renamed to \"" + newNameBook + "\"\n");
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
