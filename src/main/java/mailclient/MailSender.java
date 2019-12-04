package mailclient;

import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class MailSender extends Menu{

    String to = null;
    String subject = null;
    String from = null;
    String mailer = "MailSender";

    boolean debug = false;
    boolean verbose = false;

    @Override
    public void dialog() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        User userObj = User.currentUser;

        if(userObj == null || userObj.getPassword() == null || userObj.getUser() == null || userObj.getSmtpHost() == null) {
            System.err.println("You are not logged in");
            this.exit();
            return;
        }

        /**
         * Message properties
         */

        debug = true;
        from = userObj.getUser();

        String text = "";
        try {
            System.out.println("To: ");
            to = in.readLine();
            System.out.println("Subject: ");
            subject = in.readLine();
            text = collect(in);
        }catch (IOException e){
            System.err.println("Error while reading");
        }



        try {

            /*
             * Initialize the JavaMail Session.
             */

            Properties props = System.getProperties();
            if (userObj.getSmtpHost() != null)
                props.put("mail.smtp.host", userObj.getSmtpHost());

            props.put("mail.smtp.starttls.enable","true");

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", Integer.toString(userObj.getSmtpPort()));
            props.put("mail.smtp.user", userObj.getUser());

            Authenticator authenticator = new SMTPAuthenticator(userObj.getUser(), userObj.getPassword());
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
                        System.err.println("Send failed: " + sfe.toString());
                }
                Exception ne;
                if ((ne = sfe.getNextException()) != null &&
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
        this.exit();
    }

    /**
     * Read the body of the message until EOF.
     */
    public static String collect(BufferedReader in) throws IOException {
        System.out.println("Text: (end with q)");
        String line;
        StringBuffer sb = new StringBuffer();
        while (true) {
            line = in.readLine();
            if (line != null && line.contains("q")) {
                System.out.println("0: exit\n1: continue");
                String temp = in.readLine();
                if (temp.contains("0"))
                    break;
            }
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
}
