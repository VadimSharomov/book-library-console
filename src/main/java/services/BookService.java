package services;

import dao.BookDAO;
import entity.Book;
import org.slf4j.Logger;

import javax.annotation.Resource;
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

    @Resource
    private BookDAO dao;

    public BookService(BookDAO dao) {
        this.dao = dao;

        mainMenu = new ArrayList<>();
        mainMenu.add("To add new book type: add X. AuthorName \"Book Name\"");
        mainMenu.add("To edit book type: edit book Name of Book");
        mainMenu.add("To get all books type: all books");
        mainMenu.add("To remove book type: remove Name of Book");

        listCommands = new ArrayList<>();
        listCommands.add("add");
        listCommands.add("remove");
        listCommands.add("edit book");
        listCommands.add("all books");
    }

    public List<String> getListCommands() {
        return listCommands;
    }

    public void setListCommands(List<String> listCommands) {
        this.listCommands = listCommands;
    }

    public void add(Book book) {
        dao.addBook(book.getName(), book.getAuthor());
    }

    public List<String> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<String> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<Book> getAllBoks() {
        return dao.allBooks();
    }

    public List<Book> getBookByName(Book book) {
        return dao.getBookByName(book.getName());
    }

    public void editBook(Book oldBook, Book newBook) {
        dao.updateBook(oldBook.getName(), newBook.getName());
    }

    public int remove(Book book){
        return dao.removeBook(book.getId());
    }
}


