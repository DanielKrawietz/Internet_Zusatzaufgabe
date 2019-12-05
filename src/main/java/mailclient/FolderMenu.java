package mailclient;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class FolderMenu extends Menu {

    static String protocol = "imaps";
    String root = "";
    Folder folder = null;

    public static void createRootFolderMenu(){
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
            }
            new FolderMenu(folder).enter();
        try {
            store.close();
        } catch (MessagingException | NullPointerException e) {
        }
    }




    public FolderMenu(Folder folder)  {
        this.folder = folder;
    }

    @Override
    public void dialog() {
        if (!folder.getName().equals(""))
            System.out.println("Current Folder: " + folder.getName());

        try{
            Menu fm = new Menu();
            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                Folder[] f = folder.list(pattern);
                for (int i = 0; i < f.length; i++){
                    Folder folder = f[i];
                    Menu m = new FolderMenu(folder);
                    fm.addAction("\tFolder:\t" + folder.getName(),m);
                }

            }
            try {
                folder.open(Folder.READ_ONLY);
                if ((folder.getType() & Folder.HOLDS_MESSAGES) !=0){
                    Message[] m = folder.getMessages();
                    for (int i = 0; i < m.length; i++){
                        final Message msg = m[i];
                        fm.addAction("\tMessage\t" + m[i].getSubject() + "\t" + m[i].getFrom()[0],() -> {printMessage(msg);});
                    }
                }
            }catch (MessagingException e){

            }finally {
                try {
                    folder.close();
                }catch (IllegalStateException e){

                }

            }

            fm.enter();
        }catch (MessagingException e){
            System.err.println("Cant connect ");
        }

    }

    public void searchFolder(){

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

            String text;
            //start copy https://www.programcreek.com/java-api-examples/?class=javax.mail.Message&method=getContent

            Object content = msg.getContent();
            if (content instanceof Multipart) {
                StringBuilder messageContent = new StringBuilder();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                text = messageContent.toString();
            }
            else
                text = content.toString();

            //end Copy

            System.out.println(text);

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
