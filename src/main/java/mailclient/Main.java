package mailclient;

import java.io.IOException;

public class Main {
    public static void main(String Argv[]) throws IOException {

        // Login Data
        // internetzusatzaufgabe2019@gmail.com
        // h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\]@H@q=P<xV=-*xNn/n9~A3*/i
        // smtp.gmail.com

        Menu login = new Login();
        login.enter();



        Menu mainMenu = new Menu();
            Menu readMailmenu = new FolderMenu();
            Menu writeMailmenu = new MailSender();
            Menu subMenu = new Menu();


        readMailmenu.addAction("submenu",subMenu);
        mainMenu.addAction("read mail", readMailmenu);
        mainMenu.addAction("write mail", writeMailmenu);
        mainMenu.addAction("Account", login);

        mainMenu.enter();


    }



}
