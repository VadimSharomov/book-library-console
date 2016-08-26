package controller;

import entity.Answer;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.BookService;

import java.util.Comparator;
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

    @SuppressWarnings("Since15")
    public void start() {
        String greeting = "\nWelcome to our library!";
        gui.showMessage(greeting);
        Answer answer = getAnswer();
        while (!answer.isExit()) {
            if ("add".equals(answer.getLibraryCommand())) {
                bookService.add(answer.getBook());
                gui.showMessage("Book " + answer.getBook() + " was added\n");
            } else if ("edit".equals(answer.getLibraryCommand())) {
                List<Book> listBooks = bookService.getBookByName(answer.getBook());
                int numberSelectedBook = 0;

                if (listBooks.size() > 0) {
                    if (listBooks.size() > 1) {
                        gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                        numberSelectedBook = gui.chooseBook(listBooks);
                    }
                    bookService.editBook(listBooks.get(numberSelectedBook), answer.getNewBook());
                    gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was renamed to " + answer.getNewBook() + "\n");
                } else {
                    gui.showMessage("Not found this book:\n" + answer.getBook());
                }
            } else if ("all books".equals(answer.getLibraryCommand())) {
                List<Book> listBooks = bookService.getAllBoks();
                listBooks.sort(new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        int resCompare = String.CASE_INSENSITIVE_ORDER.compare(b1.getName(), b2.getName());
                        return (resCompare != 0) ? resCompare : b1.getName().compareTo(b2.getName());
                    }
                });
                gui.showBooks(listBooks);
            } else if ("remove".equals(answer.getLibraryCommand())) {
                List<Book> listBooks = bookService.getBookByName(answer.getBook());
                int numberSelectedBook = 0;

                if (listBooks.size() > 0) {
                    if (listBooks.size() > 1) {
                        gui.showMessage("We have few books with such name. Please choose one by typing a number of book:\n");
                        numberSelectedBook = gui.chooseBook(listBooks);
                    }

                    if (bookService.remove(listBooks.get(numberSelectedBook)) > 0) {
                        gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was removed\n");
                    } else {
                        gui.showMessage("Book " + listBooks.get(numberSelectedBook) + " was not removed\n");
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
            if (recognizeCommand(answerStr)) {
                break;
            } else {
                gui.showMessage("Unrecognized command: '" + answerStr + "'\n");
            }
        }
        if ("exit".equals(answerStr)) {
            return new Answer(true);
        }
        String libraryCommand = getLibraryCommand(answerStr);
        answerStr = answerStr.replace(libraryCommand, "").trim();
        Book newBook = getBookWithNewName(answerStr, libraryCommand);
        Book book = getSelectedBook(answerStr);

        return new Answer(libraryCommand, book, newBook);
    }

    private Book getBookWithNewName(String answerStr, String libraryCommand) {
        String newNameBook = "";
        if ("edit".equals(libraryCommand)) {
            newNameBook = gui.enterNewName();
        }
        return new Book(newNameBook, "");
    }

    private String getLibraryCommand(String answerStr) {
        for (int i = 0; i < bookService.getListCommands().size(); i++) {
            if (answerStr.startsWith(bookService.getListCommands().get(i))) {
                return bookService.getListCommands().get(i);
            }
        }
        logger.error("Unknown command: '" + answerStr + "'");
        return "Unknown command";
    }

    private Book getSelectedBook(String answerStr) {
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

    private boolean recognizeCommand(String answer) {
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