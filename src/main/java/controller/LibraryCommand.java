package controller;

/**
 * @author Vadim Sharomov
 */
enum LibraryCommand {
    ADD     ("add",         "add {A.AuthorName \"Book Name\"}     (For adding new book)"),
    EDIT    ("edit",        "edit {Name of Book}                (For editing book)"),
    FIND    ("find",        "find {PartOfNameBook}              (For finding book)"),
    REMOVE  ("remove",      "remove {Name of Book}              (For removing book)"),
    ALLBOOKS("all books",   "all books                          (For getting all books)"),
    EXIT    ("exit",        "exit                               (For exit from library)");

    private final String value;
    private final String description;

    LibraryCommand(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
