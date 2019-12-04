package mailclient;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    HashMap<String, Runnable> submenus = new HashMap<String, Runnable>();

    private boolean closed;

    public Menu() {
        submenus.put("exit", this::exit);
    }

    public void exit() {
        this.closed = true;
    }

    public void enter() {
        this.closed = false;

        while (!this.closed) {
            this.dialog();
        }
    }

    public void dialog() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please choose an option");
        for (int i = 0; i < submenus.size(); i++) {
            System.out.println(i + "\t" + submenus.keySet().toArray()[i].toString());
        }
        try {
            int selection = in.nextInt();
            if (0 <= selection && selection < submenus.size()) {
                Runnable action = (Runnable) submenus.values().toArray()[selection];
                action.run();
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Not a possible Option");
        }

    }
    public

    public void addAction(String name, Menu action) {
        submenus.put(name, action::enter);
    }

    public void addAction(String name, Runnable action) {
        submenus.put(name, action);
    }
}