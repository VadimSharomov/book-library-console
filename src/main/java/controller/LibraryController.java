package controller;

import entity.Answer;
import entity.Book;
import gui.GUI;
import org.slf4j.Logger;
import services.LibraryCommand;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */

public class LibraryController {
    private final static Logger logger = getLogger(LibraryController.class);

    private GUI gui;
    private BookCommandManager bookCommandManager;

    public LibraryController(BookCommandManager bookCommandManager, GUI gui) {
        this.bookCommandManager = bookCommandManager;
        this.gui = gui;
    }

    public void start() {
        String greeting = "\nWelcome to our library!";
        gui.showMessage(greeting);
        Answer answer;
        while (!(answer = getAnswerFromUser()).isExit()) {
            bookCommandManager.doIt(answer);
        }
    }

    private Answer getAnswerFromUser() {
        String answerStr;
        while (true) {
            answerStr = gui.showMainMenu(bookCommandManager.getMainMenu());
            if (isRecognizeCommand(answerStr)) {
                break;
            } else {
                gui.showMessage("Unrecognized command: '" + answerStr + "'\n");
            }
        }
        if ("exit".equals(answerStr.trim().toLowerCase())) {
            return new Answer(true);
        }
        String libraryCommand = getLibraryCommand(answerStr);
        answerStr = answerStr.replace(libraryCommand, "").trim();
        Book newNameBook = getBookWithNewName(answerStr, libraryCommand);
        Book book = getSelectedBook(answerStr);

        return new Answer(libraryCommand, book, newNameBook);
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

    private boolean isRecognizeCommand(String answer) {
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