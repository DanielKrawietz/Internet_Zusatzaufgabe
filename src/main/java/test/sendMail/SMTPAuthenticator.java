package test.sendMail;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "internetzusatzaufgabe2019@gmail.com";
        String password = "h[5b)h'{[.dy.R1$Fbk~<})&1Vr7.OpKyfc581\\]@H@q=P<xV=-*xNn/n9~A3*/i";
        return new PasswordAuthentication(username, password);
    }
}