package services;

import dao.BookDAO;
import entity.Book;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * @author Vadim Sharomov
 */
public class BookService {
    private final static Logger logger = getLogger(BookService.class);
    private List<String> mainMenu;
    private List<String> listCommands;

    private BookDAO dao;

    public BookService(BookDAO dao) {
        this.dao = dao;

        mainMenu = new ArrayList<>();
        mainMenu.add("To add new book type: add X. AuthorName \"Book Name\"");
        mainMenu.add("To edit book type: edit Name of Book");
        mainMenu.add("To get all books type: all books");
        mainMenu.add("To remove book type: remove Name of Book");

        listCommands = new ArrayList<>();
        listCommands.add("add");
        listCommands.add("remove");
        listCommands.add("edit");
        listCommands.add("all books");
    }

    public List<String> getListCommands() {
        return listCommands;
    }

    public void setListCommands(List<String> listCommands) {
        this.listCommands = listCommands;
    }

    public List<String> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<String> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public int add(Book book) {
        return dao.addBook(book.getName(), book.getAuthor());
    }

    public List<Book> getAllBooks() {
        return dao.allBooks();
    }

    public List<Book> getBookByName(Book book) {
        return dao.getBookByName(book.getName());
    }

    public int editBook(Book oldBook, Book newBook) {
        return dao.updateBook(oldBook.getId(), newBook.getName());
    }

    public int remove(Book book){
        return dao.removeBook(book.getId());
    }
}


