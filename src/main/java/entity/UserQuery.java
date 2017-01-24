package entity;

/**
 * @author Vadim Sharomov
 */
public class UserQuery {
    private String libraryCommand;
    private Book book;
    private boolean exitFromLibrary;

    public UserQuery(String libraryCommand, Book book) {
        this.libraryCommand = libraryCommand;
        this.book = book;
    }

    public boolean isExitFromLibrary() {
        return exitFromLibrary;
    }

    public void setExitFromLibrary(boolean exitFromLibrary) {
        this.exitFromLibrary = exitFromLibrary;
    }

    public String getLibraryCommand() {
        return libraryCommand;
    }

    public Book getBook() {
        return book;
    }
}
