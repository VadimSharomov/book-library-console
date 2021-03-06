package services;

import entity.Book;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * @author Vadim Sharomov
 */
public class BookDAOService {
    private final static Logger logger = getLogger(BookDAOService.class);

    private BookDAO dao;

    public BookDAOService(BookDAO dao) {
        this.dao = dao;

    }

    public int add(Book book) {
        return dao.addBook(book.getName(), book.getAuthor());
    }

    public List<Book> getAllBooks() {
        return dao.allBooks();
    }

    public List<Book> getBookByName(String bookName) {
        return dao.getBookByName(bookName);
    }

    public int editBook(Book oldBook, String newNameBook) {
        return dao.updateBook(oldBook.getId(), newNameBook);
    }

    public int remove(Book book) {
        return dao.removeBook(book.getId());
    }

    public List<Book> getBookLikeName(Book book) {
        return dao.getBookLikeName(book.getName());
    }
}


