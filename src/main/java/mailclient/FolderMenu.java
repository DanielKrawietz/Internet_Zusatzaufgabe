package mailclient;

import javax.mail.*;
import java.util.Properties;

public class FolderMenu extends Menu {

    static String protocol = null;
    static String host = null;
    static String user = null;
    static String password = null;
    static String url = null;
    static String root = null;
    static String pattern = "%";
    static boolean recursive = true;
    static boolean verbose = false;
    static boolean debug = false;




    Store store = null;

    public FolderMenu() {
        Properties props = System.getProperties();
        Session session = Session.getInstance(props, null);


        try {
            if (protocol != null)
                store = session.getStore(protocol);
            else
                store = session.getStore();

            // Connect
            if (host != null || user != null || password != null)
                store.connect(host, user, password);
            else
                store.connect();
        }catch (MessagingException e){
            System.err.println("Cant Connect to Server");
            System.exit(-1);
        }





    }
    public FolderMenu(String url) throws MessagingException {
        Properties props = System.getProperties();
        Session session = Session.getInstance(props, null);

        URLName urln = new URLName(url);
        store = session.getStore(urln);
        store.connect();

    }

    @Override
    public void dialog() {
        try {
            Folder folder = null;
            if (root != null)
                folder = store.getFolder(root);
            else
                folder = store.getDefaultFolder();

            System.out.println( "Name:      " + folder.getName());
            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                Folder[] f = folder.list(pattern);
                for (int i = 0; i < f.length; i++){
                    System.out.println(i + "\t" + submenus.keySet().toArray()[i].toString());
                }

            }
        }catch (MessagingException e){
            System.err.println("Cant connect ");
        }

    }

    @Override
    public void exit() {
        super.exit();
        try{
            store.close();
        }catch (MessagingException e){

        }

    }

}
