package gui;

import entity.Book;

import java.util.List;
import java.util.Scanner;

/**
 * @author Vadim Sharomov
 */
public class GUIConsole implements GUI {
    private Scanner scanner;

    public GUIConsole() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showMainMenu(List<String> mainMenu) {
        System.out.println("Main menu:");
        for (String itemMenu : mainMenu) {
            System.out.println(" " + itemMenu);
        }
    }

    @Override
    public String getUserRequest() {
        System.out.println("Input command and press Enter:");
        return scanner.nextLine().trim();
    }

    @Override
    public String enterNewNameBook() {
        System.out.println("Input new name of book and press Enter:");
        String newNameBook = "";
        while (newNameBook.length() == 0) {
            newNameBook = scanner.nextLine().trim();
        }
        return newNameBook;
    }

    @Override
    public void showBooks(List<Book> listBook) {
        System.out.println("Our books:");
        for (int i = 0; i < listBook.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + listBook.get(i));
        }
        System.out.println();
    }

    @Override
    public int chooseBook(List<Book> listBook) {
        for (int i = 0; i < listBook.size(); i++) {
            System.out.println((i + 1) + ". " + listBook.get(i));
        }
        return scanner.nextInt() - 1;
    }
}
