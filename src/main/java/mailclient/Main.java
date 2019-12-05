package mailclient;

public class Main {
    public static void main(String Argv[]) {
        Login login = new Login();
        login.enter();

        Menu mainMenu = new Menu();
        mainMenu.addAction("read mail", FolderMenu::createRootFolderMenu);
        mainMenu.addAction("write mail", new MailSender());
        mainMenu.addAction("Account", login);
        mainMenu.enter();
    }
}
