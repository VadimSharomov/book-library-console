package entity;

/**
 * @author Vadim Sharomov
 */
public class UserQuery {
    private String commandLibrary;
    private Book book;
    private boolean exitFromLibrary;

    public UserQuery(String commandLibrary, Book book) {
        this.commandLibrary = commandLibrary;
        this.book = book;
    }

    public boolean isExitFromLibrary() {
        return exitFromLibrary;
    }

    public void setExitFromLibrary(boolean exitFromLibrary) {
        this.exitFromLibrary = exitFromLibrary;
    }

    public String getCommandLibrary() {
        return commandLibrary;
    }

    public Book getBook() {
        return book;
    }
}