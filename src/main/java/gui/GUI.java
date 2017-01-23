package gui;

import entity.Book;

import java.util.List;

/**
 * @author Vadim Sharomov
 */
public interface GUI {

    void showMessage(String message);

    void showMainMenu(List<String> mainMenu);

    String getUserRequest();

    String enterNewNameBook();

    void showBooks(List<Book> listBook);

    int chooseBook(List<Book> listBook);
}
