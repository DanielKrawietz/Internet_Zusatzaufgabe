package mailclient;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    HashMap<String, Runnable> submenus = new HashMap<String, Runnable>();

    private boolean closed = false;

    public Menu(){
        submenus.put("exit",this::exit);
    }

    private void exit() {
        this.closed = true;
    }

    public void enter(){
        Scanner in = new Scanner(System.in);
        while (!this.closed)
        {
            System.out.println("Please choose an option");
            for (int i = 0; i < submenus.size();i++){
                System.out.println(i + "\t" + submenus.keySet().toArray()[i].toString());
            }

            int selection = in.nextInt();
            if(selection < submenus.size()){
                Runnable action = (Runnable) submenus.values().toArray()[selection];
                action.run();
            }else{
                System.out.println("Not a possible Option");
            }

        }
    }

    public void addAction(String name, Menu action){
        submenus.put(name,action::enter);
    }
    public void addAction(String name, Runnable action){
        submenus.put(name,action);
    }



}
