package services;

/**
 * @author Vadim Sharomov
 */
public enum LibraryCommand {
    ADD("add"),
    EDIT("edit"),
    REMOVE("remove"),
    ALLBOOKS("all books");

    LibraryCommand(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
