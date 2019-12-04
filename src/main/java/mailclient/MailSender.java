package mailclient;

import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;
import test.sendMail.SMTPAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class MailSender {

    String to = null;
    String subject = null;
    String from = null;
    String url = null;
    String mailhost = null;
    String mailer = "MailSender";
    String host = null;
    String user = null;
    String password = null;

    Scanner scanner = new Scanner(System.in);

    boolean debug = false;
    boolean verbose = true;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void sendMail(User userObj) {

        /**
         * Message properties
         */

        host = userObj.getUser();
        user = userObj.getUser();
        password = userObj.getPassword();
        mailhost = userObj.getSmtpHost();
        debug = true;
        from = userObj.getUser();

        System.out.println("To: ");
        to = scanner.nextLine();

        System.out.println("Subject: ");
        subject = scanner.nextLine();

        try {
            /*
             * Initialize the JavaMail Session.
             */
            Properties props = System.getProperties();
            if (mailhost != null)
                props.put("mail.smtp.host", mailhost);

            props.put("mail.smtp.auth", "true");
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(props, authenticator);

            // Get a Session object
            if (debug)
                session.setDebug(true);

            /*
             * Construct the message and send it.
             */
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            String text = collect(in);

            // If the desired charset is known, you can use
            // setText(text, charset)
            msg.setText(text);

            msg.setHeader("X-Mailer", mailer);
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");

            try {
                t.connect();
                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                if (verbose)
                    System.out.println("Response: " + t.getLastServerResponse());
                    t.close();
            }

            System.out.println("\nMail was sent successfully.");

            /*
             * Save a copy of the message, if requested.
             */

        } catch (Exception e) {
            /*
             * Handle SMTP-specific exceptions.
             */
            if (e instanceof SendFailedException) {
                MessagingException sfe = (MessagingException)e;
                if (sfe instanceof SMTPSendFailedException) {
                    SMTPSendFailedException ssfe =
                            (SMTPSendFailedException)sfe;
                    System.out.println("SMTP SEND FAILED:");
                    if (verbose)
                        System.out.println(ssfe.toString());
                    System.out.println("  Command: " + ssfe.getCommand());
                    System.out.println("  RetCode: " + ssfe.getReturnCode());
                    System.out.println("  Response: " + ssfe.getMessage());
                } else {
                    if (verbose)
                        System.out.println("Send failed: " + sfe.toString());
                }
                Exception ne;
                while ((ne = sfe.getNextException()) != null &&
                        ne instanceof MessagingException) {
                    sfe = (MessagingException)ne;
                    if (sfe instanceof SMTPAddressFailedException) {
                        SMTPAddressFailedException ssfe =
                                (SMTPAddressFailedException)sfe;
                        System.out.println("ADDRESS FAILED:");
                        if (verbose)
                            System.out.println(ssfe.toString());
                        System.out.println("  Address: " + ssfe.getAddress());
                        System.out.println("  Command: " + ssfe.getCommand());
                        System.out.println("  RetCode: " + ssfe.getReturnCode());
                        System.out.println("  Response: " + ssfe.getMessage());
                    } else if (sfe instanceof SMTPAddressSucceededException) {
                        System.out.println("ADDRESS SUCCEEDED:");
                        SMTPAddressSucceededException ssfe =
                                (SMTPAddressSucceededException)sfe;
                        if (verbose)
                            System.out.println(ssfe.toString());
                        System.out.println("  Address: " + ssfe.getAddress());
                        System.out.println("  Command: " + ssfe.getCommand());
                        System.out.println("  RetCode: " + ssfe.getReturnCode());
                        System.out.println("  Response: " + ssfe.getMessage());
                    }
                }
            } else {
                System.out.println("Got Exception: " + e);
                if (verbose)
                    e.printStackTrace();
            }
        }
    }

    /**
     * Read the body of the message until EOF.
     */
    public static String collect(BufferedReader in) throws IOException {
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
}
