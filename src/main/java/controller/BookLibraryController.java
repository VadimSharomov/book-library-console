package controller;

import entity.Answer;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.BookService;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */

public class BookLibraryController {
    private final static Logger logger = getLogger(BookLibraryController.class);

    private BookService bookService;
    private GUI gui;

    public BookLibraryController(GUI gui, BookService bookService) {
        this.gui = gui;
        this.bookService = bookService;
    }

    public void start() {
        String greeting = "\nWelcome to our library!\n";
        gui.showMessage(greeting);
        Answer answer = getAnswer();
        while (!answer.isExit()) {
            if ("add".equals(answer.getLibraryCommand())) {
                bookService.add(answer.getBook());
                gui.showMessage("Book " + answer.getBook() + " was added\n");
            } else if ("edit book".equals(answer.getLibraryCommand())) {
                bookService.editBook(answer.getBook(), answer.getNewBook());
                gui.showMessage("Book " + answer.getBook() + " was renamed to " + answer.getNewBook().getName() + "\n");
            } else if ("all books".equals(answer.getLibraryCommand())) {
                List<Book> listBooks = bookService.getAllBoks();
                gui.showBooks(listBooks);
            } else if ("remove".equals(answer.getLibraryCommand())) {
                int numberBooks = bookService.getBookByName(answer.getBook()).size();
                int numberSelectedBook = 0;

                if (numberBooks > 0) {
                    if (numberBooks > 1) {
                        gui.showMessage("We have few books with such name please choose one by typing a number of book:\n");
                        numberSelectedBook = gui.chooseBook(bookService.getBookByName(answer.getBook()));
                    }

                    if (bookService.remove(bookService.getBookByName(answer.getBook()).get(numberSelectedBook)) > 0) {
                        gui.showMessage("Book " + answer.getBook() + " was removed\n");
                    } else {
                        gui.showMessage("Book " + answer.getBook() + " was not removed\n");
                    }
                } else {
                    gui.showMessage("Not found this book:\n" + answer.getBook());
                }
            }
            answer = getAnswer();
        }
    }

    private Answer getAnswer() {
        String answerStr;
        while (true) {
            answerStr = gui.showMainMenu(bookService.getMainMenu());
            if (recogniseCommand(answerStr)) {
                break;
            }
        }
        if ("exit".equals(answerStr)) {
            return new Answer(true);
        }
        String libraryCommand = getLibraryCommand(answerStr);
        answerStr = answerStr.replace(libraryCommand, "").trim();
        Book newBook = getNewNameBook(answerStr, libraryCommand);
        Book book = getBookFromAnswerStr(answerStr);

        return new Answer(libraryCommand, book, newBook);
    }

    private Book getNewNameBook(String answerStr, String libraryCommand) {
        if ("edit book".equals(libraryCommand)) {
            return new Book(gui.enterNewName(), "");
        }
        return new Book();
    }

    private String getLibraryCommand(String answerStr) {
        for (int i = 0; i < bookService.getListCommands().size(); i++) {
            if (answerStr.startsWith(bookService.getListCommands().get(i))) {
                return bookService.getListCommands().get(i);
            }
        }
        return "unknown";
    }

    private Book getBookFromAnswerStr(String answerStr) {
        String authorName, bookName;
        if (answerStr.split("\"").length > 1) {
            authorName = answerStr.split("\"")[0].trim();
            bookName = answerStr.split("\"")[1].trim();
        } else {
            authorName = "";
            bookName = answerStr;
        }
        return new Book(bookName, authorName);
    }

    private boolean recogniseCommand(String answer) {
        if ("exit".equals(answer)) return true;
        String[] arWords = answer.split(" ");
        if (arWords.length < 1) return false;
        for (int i = 0; i < bookService.getListCommands().size(); i++) {
            if (answer.startsWith(bookService.getListCommands().get(i))) {
                return true;
            }
        }
        return false;
    }
}