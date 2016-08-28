package controller;

import entity.Answer;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.BookService;
import services.LibraryCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */
public class BookManager {
    private final static Logger logger = getLogger(LibraryController.class);

    private BookService bookService;
    private GUI gui;
    private List<String> mainMenu;

    public BookManager(BookService bookService, GUI gui) {
        this.bookService = bookService;
        this.gui = gui;

        mainMenu = new ArrayList<>();
        mainMenu.add("To add new book type: add X. AuthorName \"Book Name\"");
        mainMenu.add("To edit book type: edit Name of Book");
        mainMenu.add("To get all books type: all books");
        mainMenu.add("To remove book type: remove Name of Book");
    }

    public List<String> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<String> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void doIt(Answer answer) {
        if (LibraryCommand.ADD.getValue().equals(answer.getLibraryCommand())) {
            commandAdd(answer);
        } else if (LibraryCommand.EDIT.getValue().equals(answer.getLibraryCommand())) {
            commandEdit(answer);
        } else if (LibraryCommand.ALLBOOKS.getValue().equals(answer.getLibraryCommand())) {
            commandAllBooks();
        } else if (LibraryCommand.REMOVE.getValue().equals(answer.getLibraryCommand())) {
            commandRemove(answer);
        }
    }

    private void commandRemove(Answer answer) {
        List<Book> listBooks = bookService.getBookByName(answer.getBook());
        int numberSelectedBook = 0;

        if (listBooks.size() > 0) {
            if (listBooks.size() > 1) {
                gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                numberSelectedBook = gui.chooseBook(listBooks);
            }
            String not = " not";
            if (bookService.remove(listBooks.get(numberSelectedBook)) > 0) {
                not = "";
            }
            gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was" + not + " removed\n");
        } else {
            gui.showMessage("Not found this book:\n" + answer.getBook());
        }
    }

    @SuppressWarnings("Since15")
    private void commandAllBooks() {
        List<Book> listBooks = bookService.getAllBooks();
        listBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(b1.getName(), b2.getName());
                return (resCompare != 0) ? resCompare : b1.getName().compareTo(b2.getName());
            }
        });
        gui.showBooks(listBooks);
    }

    private void commandEdit(Answer answer) {
        List<Book> listBooks = bookService.getBookByName(answer.getBook());
        int numberSelectedBook = 0;

        if (listBooks.size() > 0) {
            if (listBooks.size() > 1) {
                gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                numberSelectedBook = gui.chooseBook(listBooks);
            }
            String not = " not";
            if (bookService.editBook(listBooks.get(numberSelectedBook), answer.getNewBook()) > 0) {
                not = "";
            }
            gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was" + not + " renamed to " + answer.getNewBook() + "\n");
        } else {
            gui.showMessage("Not found this book:\n" + answer.getBook());
        }
    }

    private void commandAdd(Answer answer) {
        String not = " not";
        if (bookService.add(answer.getBook()) > 0) {
            not = "";
        }
        gui.showMessage("Book " + answer.getBook() + " was" + not + " added\n");
    }
}
