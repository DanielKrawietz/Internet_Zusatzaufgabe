package mailclient;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(User.currentUser.getUser(), User.currentUser.getPassword() );
    }

}