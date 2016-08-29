package controller;

import entity.Book;
import entity.UserQuery;
import gui.GUI;
import services.BookDAOService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Vadim Sharomov
 */
public class BookCommandManager {

    private BookDAOService bookDAOService;
    private GUI gui;
    private List<String> mainMenu;

    public BookCommandManager(BookDAOService bookDAOService, GUI gui) {
        this.bookDAOService = bookDAOService;
        this.gui = gui;

        mainMenu = new ArrayList<>();
        mainMenu.add("For adding new book, type this: add A.AuthorName \"Book Name\"");
        mainMenu.add("For editing book, type this: edit Name of Book");
        mainMenu.add("For finding book, type this: find PartOfNameBook");
        mainMenu.add("For getting all books, type this: all books");
        mainMenu.add("For removing book, type this: remove Name of Book");
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
        } else if (LibraryCommand.FIND.getValue().equals(userQuery.getLibraryCommand())){
            commandFind(userQuery);
        }
    }

    @SuppressWarnings("Since15")
    private void commandFind(UserQuery userQuery) {
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
