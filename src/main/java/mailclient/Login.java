package mailclient;



import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Login extends Menu {

    private Scanner scanner = new Scanner(System.in);

    public Login() {
        this.addAction("Login", this::readLoginData);
        this.addAction("Login with saved User", this::getLoginData);
        this.addAction("save this User", this::saveLoginDataToFile);
        this.addAction("back", this::back);

    }
    private boolean debug = true;

    public void back(){
        super.exit();
    }

    public void exit() {
        System.exit(0);
    }

    private void readLoginData() {

        User userObj = User.currentUser;
        if(userObj == null)
            userObj = new User();

        if (debug){
            userObj.setUser("internetzusatzaufgabe2019@gmail.com");
            userObj.setPassword("h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\\]@H@q=P<xV=-*xNn/n9~A3*/i");
            userObj.setSmtpHost("smtp.gmail.com");
            userObj.setSmtpPort(587);
        }else{
            System.out.println("User: ");
            userObj.setUser(scanner.nextLine());

            System.out.println("Password: ");
            userObj.setPassword(scanner.nextLine());


            System.out.println("SMTP Host: ");
            userObj.setSmtpHost(scanner.nextLine());


            System.out.println("SMTP Port: ");
            userObj.setSmtpPort(scanner.nextInt());

            System.out.println("IMAP Host: ");
            userObj.setImapHost(scanner.nextLine());

            System.out.println("IMAP Port: ");
            userObj.setImapPort(scanner.nextInt());
        }
        User.currentUser = userObj;
        this.back();



    }



    private void getLoginData() {
        Scanner fileScanner = null;
        try {
            File file = new File("logindata.txt");
            fileScanner = new Scanner(file);

            User userObj = User.currentUser;
            if(userObj == null)
                userObj = new User();
            userObj.setUser(fileScanner.nextLine());
            userObj.setPassword(fileScanner.nextLine());
            userObj.setSmtpHost(fileScanner.nextLine());
            userObj.setSmtpPort(fileScanner.nextInt());
            userObj.setImapHost(fileScanner.nextLine());
            userObj.setImapPort(fileScanner.nextInt());
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

        if(userObj == null)
            userObj = new User();

        FileWriter writer = null;
        try {
            writer = new FileWriter("logindata.txt");
            writer.write(userObj.getUser());
            writer.write(userObj.getPassword());
            writer.write(userObj.getSmtpHost());
            writer.write(userObj.getSmtpPort());
            writer.write(userObj.getImapHost());
            writer.write(userObj.getImapPort());
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
