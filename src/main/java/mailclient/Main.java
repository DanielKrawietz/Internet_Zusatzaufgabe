package mailclient;


import javax.mail.MessagingException;

public class Main {
    public static void main(String Argv[]){
        Menu mainMenu = new Menu();
        Menu readMailmenu = new FolderMenu();
        Menu subMenu = new Menu();
        readMailmenu.addAction("submenu",subMenu);
        Menu writeMailmenu = new Menu();
        mainMenu.addAction("read mail", readMailmenu);
        mainMenu.addAction("write mail", writeMailmenu);
        mainMenu.enter();


    }



}
