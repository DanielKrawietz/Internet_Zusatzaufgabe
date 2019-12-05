package mailclient;



import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Login extends Menu {

    private Scanner scanner = new Scanner(System.in);

    public Login() {
        this.addAction("exit", this::close);
        this.addAction("Login", this::readLoginData);
        this.addAction("Login with saved User", this::getLoginData);
        this.addAction("save this User", this::saveLoginDataToFile);
    }
    private boolean debug = true;

    public void close(){
        System.exit(0);
    }

    private void readLoginData() {

        if(User.currentUser == null)
            User.currentUser = new User();

        User user = User.currentUser;

        if (debug){
            user.setUser("internetzusatzaufgabe2019@gmail.com");
            user.setPassword("h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\\]@H@q=P<xV=-*xNn/n9~A3*/i");
            user.setSmtpHost("smtp.gmail.com");
            user.setSmtpPort(587);
            user.setImapHost("imap.gmail.com");
            user.setImapPort(993);
        }else{
            System.out.println("User: ");
            user.setUser(scanner.nextLine());

            System.out.println("Password: ");
            user.setPassword(scanner.nextLine());


            System.out.println("SMTP Host: ");
            user.setSmtpHost(scanner.nextLine());


            System.out.println("SMTP Port: ");
            user.setSmtpPort(scanner.nextInt());

            System.out.println("IMAP Host: ");
            user.setImapHost(scanner.nextLine());

            System.out.println("IMAP Port: ");
            user.setImapPort(scanner.nextInt());
        }
        User.currentUser = user;
        this.addAction("exit", super::exit);
        this.exit();



    }



    private void getLoginData() {
        Scanner fileScanner = null;
        try {
            File file = new File("logindata.txt");
            fileScanner = new Scanner(file);

            User userObj = new User();
            userObj.setUser(fileScanner.nextLine());
            userObj.setPassword(fileScanner.nextLine());
            userObj.setSmtpHost(fileScanner.nextLine());
            userObj.setSmtpPort(fileScanner.nextInt());
            userObj.setImapHost(fileScanner.nextLine());
            userObj.setImapPort(fileScanner.nextInt());

            User.currentUser = userObj;
        } catch (IOException e) {
            System.err.println("Could not read File");
        }catch (NoSuchElementException e) {
            System.err.println("Error while reading data");

        } finally {
            if (fileScanner!= null)
                fileScanner.close();
        }



    }

    private void saveLoginDataToFile() {
        User userObj = User.currentUser;

        if( userObj == null) {
            readLoginData();
        }

        String user = userObj.getUser();
        String password = userObj.getPassword();
        String smtpHost = userObj.getSmtpHost();
        int smtpPort = userObj.getSmtpPort();
        String imapHost = userObj.getImapHost();
        int imapPort = userObj.getImapPort();

        user = user == null ? "" : user;
        password = password == null ? "" : password;
        smtpHost = smtpHost == null ? "" : smtpHost;
        imapHost = imapHost == null ? "" : imapHost;

        FileWriter writer = null;
        try {
            writer = new FileWriter("logindata.txt");
            writer.write(user + "\n");
            writer.write(password + "\n");
            writer.write(smtpHost + "\n");
            writer.write(Integer.toString(smtpPort) + "\n");
            writer.write(imapHost + "\n");
            writer.write(Integer.toString(imapPort) + "\n");
        }catch (IOException e){
            System.err.println("Could not create File");
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }


    }
}
