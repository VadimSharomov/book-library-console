package controller;

import entity.Answer;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.BookService;
import services.LibraryCommand;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */

public class LibraryController {
    private final static Logger logger = getLogger(LibraryController.class);

    private BookService bookService;
    private GUI gui;
    private BookManager bookManager;

    public LibraryController(BookService bookService, GUI gui, BookManager bookManager) {
        this.bookManager = bookManager;
        this.bookService = bookService;
        this.gui = gui;
    }

    public void start() {
        String greeting = "\nWelcome to our library!";
        gui.showMessage(greeting);
        Answer answer;
        while (!(answer = getAnswerFromUser()).isExit()) {
            bookManager.doIt(answer);
        }
    }

    private Answer getAnswerFromUser() {
        String answerStr;
        while (true) {
            answerStr = gui.showMainMenu(bookManager.getMainMenu());
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
        for (LibraryCommand libraryCommand: LibraryCommand.values()) {
            if (answerStr.startsWith(libraryCommand.getValue())) {
                return libraryCommand.getValue();
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
        for (LibraryCommand libraryCommand: LibraryCommand.values()) {
            if (answer.startsWith(libraryCommand.getValue())) {
                return true;
            }
        }
        return false;
    }
}