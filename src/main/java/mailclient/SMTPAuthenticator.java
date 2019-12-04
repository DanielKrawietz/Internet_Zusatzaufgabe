package mailclient;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
    private String username, passwd;
    public SMTPAuthenticator(String username, String passwd){
        this.passwd = passwd;
        this.username = username;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.passwd );
    }

}