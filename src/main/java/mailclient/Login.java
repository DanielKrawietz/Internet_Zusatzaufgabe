package mailclient;



import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Login extends Menu {

    private Scanner scanner = new Scanner(System.in);
    private BufferedReader br = null;

    public Login() {
        this.addAction("exit", this::close);
        this.addAction("Login", this::readLoginData);
        this.addAction("Login with saved User", this::getLoginData);
        this.addAction("save this User", this::saveLoginDataToFile);
    }


    public void close(){
        System.exit(0);
    }

    private void readLoginData() {

        if(User.currentUser == null)
            User.currentUser = new User();

        User user = User.currentUser;


        System.out.println("User: ");
        user.setUser(scanner.nextLine());

        System.out.println("Password: ");
        user.setPassword(scanner.nextLine());

        System.out.println("SMTP Host: ");
        user.setSmtpHost(scanner.nextLine());

        System.out.println("SMTP Port: ");
        user.setSmtpPort(scanner.nextInt());
        scanner.nextLine();

        System.out.println("IMAP Host: ");
        user.setImapHost(scanner.nextLine());

        System.out.println("IMAP Port: ");
        user.setImapPort(scanner.nextInt());
        scanner.nextLine();

        User.currentUser = user;
        this.addAction("exit", super::exit);
        this.exit();
    }



    private void getLoginData() {
        try {
            File file = new File("logindata.txt");
            br = new BufferedReader(new FileReader(file));

            User userObj = new User();
            userObj.setUser(br.readLine());
            userObj.setPassword(br.readLine());
            userObj.setSmtpHost(br.readLine());
            userObj.setSmtpPort(Integer.parseInt(br.readLine()));
            userObj.setImapHost(br.readLine());
            userObj.setImapPort(Integer.parseInt(br.readLine()));

            this.addAction("exit", super::exit);
            this.exit();
            User.currentUser = userObj;
        } catch (IOException e) {
            System.err.println("Could not read File");
        }catch (NoSuchElementException e) {
            System.err.println("Error while reading data");

        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Error while reading data");
                    this.addAction("exit", super::exit);
                    this.exit();
                }
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
