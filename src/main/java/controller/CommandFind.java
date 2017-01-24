package controller;

import entity.Book;
import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

import java.util.Comparator;
import java.util.List;

/**
 * @author Vadim Sharomov
 */
public class CommandFind implements Command {
    private final String name = "find";
    private String description = "find {PartOfNameBook}              (For finding book)";
    private BookDAOService bookDAOService;
    private GUI gui;

    public CommandFind(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;
    }

    @Override
    public void execute(UserQuery userQuery) {
        List<Book> listBooks = bookDAOService.getBookLikeName(userQuery.getBook());
        listBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(b1.getName(), b2.getName());
                return (resCompare != 0) ? resCompare : b1.getName().compareTo(b2.getName());
            }
        });
        gui.showBooks(listBooks);
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
