package mailclient;


import java.io.IOException;

public class Main {
    public static void main(String Argv[]) throws IOException {

        // Login Data
        // internetzusatzaufgabe2019@gmail.com
        // h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\]@H@q=P<xV=-*xNn/n9~A3*/i
        // smtp.gmail.com
        User user = new User();
        MailSender mailsender = new MailSender();
        Login login = new Login();

        login.login(user);
        mailsender.sendMail(user);

        /*
        Menu mainMenu = new Menu();
        Menu readMailmenu = new FolderMenu();
        Menu subMenu = new Menu();
        Menu loginMenu = new Login();

        readMailmenu.addAction("submenu",subMenu);
        Menu writeMailmenu = new Menu();
        mainMenu.addAction("read mail", readMailmenu);
        mainMenu.addAction("write mail", writeMailmenu);
        mainMenu.addAction("login", loginMenu);

        mainMenu.enter();
        */

    }



}
