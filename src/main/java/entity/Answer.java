package entity;

/**
 * @author Vadim Sharomov
 */
public class Answer {
    private String libraryCommand;
    private Book book;
    private Book newBook;
    private boolean exit;

    public Answer(String libraryCommand, Book book) {
        this.libraryCommand = libraryCommand;
        this.book = book;
    }

    public Answer(String libraryCommand, Book book, Book newBook) {
        this.libraryCommand = libraryCommand;
        this.book = book;
        this.newBook = newBook;
    }

    public Answer(boolean exit) {
        this.exit = exit;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public String getLibraryCommand() {
        return libraryCommand;
    }

    public void setLibraryCommand(String libraryCommand) {
        this.libraryCommand = libraryCommand;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }
}
