package controller;

import entity.UserQuery;

/**
 * @author Vadim Sharomov
 */
public interface Command {
    void execute(UserQuery userQuery);
    String getName();
    String getDescription();
}
