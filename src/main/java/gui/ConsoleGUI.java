package gui;

import entity.Book;

import java.util.List;
import java.util.Scanner;

/**
 * @author Vadim Sharomov
 */
public class ConsoleGUI implements GUI {

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showMainMenu(List<String> mainMenu) {
        System.out.println("Main menu:");
        for (int i = 0; i < mainMenu.size(); i++) {
            System.out.println(i + ": " + mainMenu.get(i));
        }
    }

    @Override
    public String getUserRequest() {
        String res = "";
        System.out.println("Input command and press Enter:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    @Override
    public String enterNewNameBook() {
        System.out.println("Input new name of book and press Enter:");
        Scanner scanner = new Scanner(System.in);
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
            System.out.println((i + 1) + ". " + listBook.get(i));
        }
        System.out.println();
    }

    @Override
    public int chooseBook(List<Book> listBook) {
        for (int i = 0; i < listBook.size(); i++) {
            System.out.println((i + 1) + ". " + listBook.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt() - 1;
    }
}
