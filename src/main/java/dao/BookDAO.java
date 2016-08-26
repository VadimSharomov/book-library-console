package dao;

import entity.Book;

import java.util.List;

/**
 * @author Vadim Sharomov
 */
public interface BookDAO {

    void addBook(String nameBook, String author);

    void updateBook(String oldNameBook, String newNameBook);

    int removeBook(String bookName);

    List<Book> allBooks();

    List<Book> getBookByName(String bookName);
}
