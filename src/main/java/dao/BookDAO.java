package dao;

import entity.Book;

import java.util.List;

/**
 * @author Vadim Sharomov
 */
public interface BookDAO {

    int addBook(String nameBook, String author);

    int updateBook(long oldBookId, String newNameBook);

    int removeBook(long bookId);

    List<Book> allBooks();

    List<Book> getBookByName(String bookName);
}
