package mailclient;

import java.io.*;
import java.util.Scanner;

public class Login extends Menu {

    private Scanner scanner = new Scanner(System.in);
    private boolean loopRunning = false;

    public Login() {

    }

    /**
     * Just call this method for the object to contain the login data
     */
    public void login(User userObj) throws IOException {
        loopRunning = true;

        while(loopRunning) {
            System.out.println("Actions");
            System.out.println("1 - Login\n2 - Save login data\n3 - Show login data\n4 - Cancel");
            switch(scanner.nextInt()) {
                case 1:
                    loginUser(userObj);
                    break;
                case 2:
                    saveLoginDataToFile(userObj);
                    break;
                case 3:
                    showLoginData(userObj);
                    break;
                case 4:
                    loopRunning = false;
                    break;
                default:
                    System.out.println("Invalid input, try again");
            }
        }

    }

    /**
     * Private methods
     */

    private void loginUser(User userObj) throws FileNotFoundException {
        System.out.println("Actions");
        System.out.println("1 - User data using scanner\n2 - User data using file");

        switch(scanner.nextInt()) {
            case 1:
                saveLoginData(userObj);
                System.out.println("Logged in");
                break;
            case 2:
                getLoginData(userObj);
                break;
        }
    }

    private void saveLoginData(User userObj) {
        scanner.nextLine();
        System.out.println("User: ");
        //userObj.setUser(scanner.nextLine());
        userObj.setUser("internetzusatzaufgabe2019@gmail.com");

        System.out.println("Password: ");
        //userObj.setPassword(scanner.nextLine());
        userObj.setPassword("h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\\]@H@q=P<xV=-*xNn/n9~A3*/i");

        System.out.println("SMTP Host: ");
        //userObj.setSmtpHost(scanner.nextLine());
        userObj.setSmtpHost("smtp.gmail.com");

        /*System.out.println("SMTP Port: ");
        userObj.setSmtpPort(scanner.nextLine());

        System.out.println("IMAP Host: ");
        userObj.setImapHost(scanner.nextLine());

        System.out.println("IMAP Port: ");
        userObj.setImapPort(scanner.nextLine());*/
    }

    private void showLoginData(User userObj) {
        System.out.println("User: " + userObj.getUser());
        System.out.println("Password: " + userObj.getPassword());
        System.out.println("SMTP Host: " + userObj.getSmtpHost());
        System.out.println("SMTP Port: " + userObj.getSmtpPort());
        System.out.println("IMAP Host: " + userObj.getImapHost());
        System.out.println("IMAP Port: " + userObj.getImapPort());
    }

    private void getLoginData(User userObj) throws FileNotFoundException {
        File file = new File("logindata.txt");
        Scanner fileScanner = new Scanner(file);

        userObj.setUser(fileScanner.nextLine());
        userObj.setPassword(fileScanner.nextLine());
        userObj.setSmtpHost(fileScanner.nextLine());
        userObj.setSmtpPort(fileScanner.nextLine());
        userObj.setImapHost(fileScanner.nextLine());
        userObj.setImapPort(fileScanner.nextLine());

        fileScanner.close();
    }

    private void saveLoginDataToFile(User userObj) throws IOException {
        saveLoginData(userObj);

        BufferedWriter writer = new BufferedWriter(new FileWriter(("logindata.txt")));
        writer.write(userObj.getUser());
        writer.write(userObj.getPassword());
        writer.write(userObj.getSmtpHost());
        writer.write(userObj.getSmtpPort());
        writer.write(userObj.getImapHost());
        writer.write(userObj.getImapPort());

        writer.close();
    }
}
