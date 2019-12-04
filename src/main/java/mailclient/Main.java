package mailclient;

import java.io.IOException;

public class Main {
    public static void main(String Argv[]) {

        // Login Data
        // internetzusatzaufgabe2019@gmail.com
        // h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\]@H@q=P<xV=-*xNn/n9~A3*/i
        // smtp.gmail.com
        // 587
        // imap.gmail.com
        // 993

        Login login = new Login();
        login.enter();




        Menu mainMenu = new Menu();
        mainMenu.addAction("read mail", FolderMenu::createRootFolderMenu);
        mainMenu.addAction("write mail", new MailSender());
        mainMenu.addAction("Account", login);

        mainMenu.enter();


    }



}
