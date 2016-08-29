package controller;

import entity.UserQuery;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.BookDAOService;
import services.LibraryCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */
public class BookCommandManager {
    private final static Logger logger = getLogger(LibraryController.class);

    private BookDAOService bookDAOService;
    private GUI gui;
    private List<String> mainMenu;

    public BookCommandManager(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;

        mainMenu = new ArrayList<>();
        mainMenu.add("To add new book type: add X. AuthorName \"Book Name\"");
        mainMenu.add("To edit book type: edit Name of Book");
        mainMenu.add("To get all books type: all books");
        mainMenu.add("To remove book type: remove Name of Book");
    }

    List<String> getMainMenu() {
        return mainMenu;
    }

    void doIt(UserQuery userQuery) {
        if (LibraryCommand.ADD.getValue().equals(userQuery.getLibraryCommand())) {
            commandAdd(userQuery);
        } else if (LibraryCommand.EDIT.getValue().equals(userQuery.getLibraryCommand())) {
            commandEdit(userQuery);
        } else if (LibraryCommand.ALLBOOKS.getValue().equals(userQuery.getLibraryCommand())) {
            commandAllBooks();
        } else if (LibraryCommand.REMOVE.getValue().equals(userQuery.getLibraryCommand())) {
            commandRemove(userQuery);
        }
    }

    private void commandRemove(UserQuery userQuery) {
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

    @SuppressWarnings("Since15")
    private void commandAllBooks() {
        List<Book> listBooks = bookDAOService.getAllBooks();
        listBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(b1.getName(), b2.getName());
                return (resCompare != 0) ? resCompare : b1.getName().compareTo(b2.getName());
            }
        });
        gui.showBooks(listBooks);
    }

    private void commandEdit(UserQuery userQuery) {
        List<Book> listBooks = bookDAOService.getBookByName(userQuery.getBook());

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

            gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was" + wordNot + " renamed to " + newNameBook + "\n");
        } else {
            gui.showMessage("Not found this book:\n" + userQuery.getBook());
        }
    }

    private void commandAdd(UserQuery userQuery) {
        String wordNot = " not";
        if (bookDAOService.add(userQuery.getBook()) > 0) {
            wordNot = "";
        }
        gui.showMessage("Book " + userQuery.getBook() + " was" + wordNot + " added\n");
    }
}
