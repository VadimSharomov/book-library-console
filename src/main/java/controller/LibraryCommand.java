package controller;

/**
 * @author Vadim Sharomov
 */
enum LibraryCommand {
    ADD("add"),
    EDIT("edit"),
    FIND("find"),
    REMOVE("remove"),
    ALLBOOKS("all books");

    private final String value;

    LibraryCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
