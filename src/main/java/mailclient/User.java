package mailclient;

public class User {
    public static User currentUser;

    private String smtpHost = null;
    private int smtpPort = 0;
    private String imapHost = null;
    private int imapPort = 0;
    private String user = null;
    private String password = null;

    public User() {

    }

    /**
     * Sets SMTP Host
     * @param smtpHost
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * Sets SMTP Port
     * @param smtpPort
     */
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * Sets IMAP Host
     * @param imapHost
     */
    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    /**
     * Sets IMAP Port
     * @param imapPort
     */
    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    /**
     * Sets user
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets ICMP Host
     */
    public String getSmtpHost() {
        return this.smtpHost;
    }

    /**
     * Gets ICMP Port
     */
    public int getSmtpPort() {
        return this.smtpPort;
    }

    /**
     * Gets IMAP Host
     */
    public String getImapHost() {
        return this.imapHost;
    }

    /**
     * Gets IMAP Port
     */
    public int getImapPort() {
        return this.imapPort;
    }

    /**
     * Gets user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Gets password
     */
    public String getPassword() {
        return this.password;
    }

    public void show() {
        System.out.println("User: " + this.getUser());
        System.out.println("Password: " + this.getPassword());
        System.out.println("SMTP Host: " + this.getSmtpHost());
        System.out.println("SMTP Port: " + this.getSmtpPort());
        System.out.println("IMAP Host: " + this.getImapHost());
        System.out.println("IMAP Port: " + this.getImapPort());
    }
}
