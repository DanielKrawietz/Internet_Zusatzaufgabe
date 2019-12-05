package mailclient;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class FolderMenu extends Menu {

    static String protocol = "imaps";
    String root = "";
    Folder folder = null;

    public static void createRootFolderMenu(){
            System.out.println("getting Messages");
            User userObj = User.currentUser;
            String host = userObj.getImapHost();
            String user = userObj.getUser();
            String password= userObj.getPassword();
            Properties props = System.getProperties();
            Session session = Session.getInstance(props, null);
            Folder folder = null;
            Store store = null;
            try {
                if (protocol != null)
                    store = session.getStore(protocol);
                else
                    store = session.getStore();

                if (host != null || user != null || password != null)
                    store.connect(host, user, password);
                else
                    store.connect();

                folder = store.getDefaultFolder();
            } catch (MessagingException e) {
                System.err.println("cant retrieve folders");
                return;
            }
            System.out.println("Creating Folder Structure (this may take a while)");
            new FolderMenu(folder).enter();
        try {
            store.close();
        } catch (MessagingException | NullPointerException e) {
        }
    }




    public FolderMenu(Folder folder)  {
        this.folder = folder;
        this.addAction("search Mail",this::searchFolder);
        try{
            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                Folder[] f = folder.list();
                for (int i = 0; i < f.length; i++){
                    Menu m = new FolderMenu(f[i]);
                    this.addAction("\tFolder:\t" + f[i].getName(),m);
                }

            }
            try {
                folder.open(Folder.READ_ONLY);
                if ((folder.getType() & Folder.HOLDS_MESSAGES) !=0){
                    Message[] m = folder.getMessages();
                    for (int i = 0; i < m.length; i++){
                        final Message msg = m[i];
                        this.addAction("\tMessage:\t" + m[i].getSubject() + "\t" + m[i].getFrom()[0],() -> {printMessage(msg);});
                    }
                }
            }catch (MessagingException e){

            }finally {
                try {
                    folder.close();
                }catch (IllegalStateException e){

                }

            }

        }catch (MessagingException e){
            System.err.println("Cant connect ");
        }
    }

    @Override
    public void dialog() {
        if (!folder.getName().equals(""))
            System.out.println("Current Folder: " + folder.getName());
        super.dialog();
    }

    private ArrayList<Folder> getSubfolders(Folder folder) throws MessagingException {
        ArrayList<Folder> out = new ArrayList<>();
        out.add(folder);
        if ((folder.getType() & Folder.HOLDS_FOLDERS) !=0){
            Folder[] folders = folder.list();
            for (int i = 0; i < folders.length; i++){
                out.addAll(getSubfolders(folders[i]));
            }
        }
        return out;
    }

    public void searchFolder() {
        Scanner in = new Scanner(System.in);
        System.out.println("For what do you want to search?");
        String queryStr = in.nextLine();
        System.out.println("Searching folder and Subfolders ...");
        String[] queryWords = queryStr.split(" ");

        Menu fm = new Menu();
        ArrayList<Message> allmsgs = new ArrayList<>();
        ArrayList<Folder> folders;
        try {
            folders = getSubfolders(folder);
            for(Folder folder:folders){

                if ((folder.getType() & Folder.HOLDS_MESSAGES) !=0){
                    folder.open(Folder.READ_ONLY);
                    Message[] m = folder.getMessages();
                    for(Message mm : m){
                        allmsgs.add(new MimeMessage( (MimeMessage) mm));
                    }
                    folder.close();
                }

            }
        } catch (MessagingException e) {
            System.err.println("could not search all folders");
        }
        try {
            for (Message m : allmsgs) {
                boolean hit = false;

                for (Address add : m.getFrom()) {
                    for (String qw : queryWords) {
                        if (add.toString().contains(qw))
                            hit = true;
                    }
                }

                for (Address add : m.getAllRecipients()) {
                    for (String qw : queryWords) {
                        if (add.toString().contains(qw))
                            hit = true;
                    }
                }

                for (String qw : queryWords) {
                    if (m.getSubject().contains(qw))
                        hit = true;
                }

                for (String qw : queryWords) {
                    if (getMsgBody(m).contains(qw))
                        hit = true;
                }



                if (hit)
                    fm.addAction("\tMessage:\t" + m.getSubject() + "\t" + m.getFrom()[0], () -> {
                        printMessage(m);
                    });
            }
        }catch (MessagingException | IOException e) {
        }
        fm.enter();

    }

    private String getMsgBody(Message m) throws MessagingException, IOException {
        //start copy https://www.programcreek.com/java-api-examples/?class=javax.mail.Message&method=getContent

        Object content = m.getContent();
        if (content instanceof Multipart) {
            StringBuilder messageContent = new StringBuilder();
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                Part part = multipart.getBodyPart(i);
                if (part.isMimeType("text/plain")) {
                    messageContent.append(part.getContent().toString());
                }
            }
            return messageContent.toString();
        }
        return content.toString();

        //end Copy

    }

    private void printMessage(Message msg) {
        try {
            msg.getFolder().open(Folder.READ_ONLY);
            System.out.println("Subject: " + msg.getSubject());
            System.out.print("From: ");
            for(Address s:msg.getFrom())
                System.out.print(s);
            System.out.println();
            System.out.print("to: ");
            for(Address s:msg.getAllRecipients())
                System.out.print(s);
            System.out.println();
            System.out.println();


            System.out.println(getMsgBody(msg));

        } catch (MessagingException | IOException e) {
            System.err.println("Cant read Message");
        }finally {
            try {
                msg.getFolder().close();
            } catch (NullPointerException | MessagingException e) {
            }
        }


    }

}
