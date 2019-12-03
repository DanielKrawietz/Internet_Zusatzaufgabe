package mailclient;

public class User {
    private String smtpHost = null;
    private String smtpPort = null;
    private String imapHost = null;
    private String imapPort = null;
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
    public void setSmtpPort(String smtpPort) {
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
    public void setImapPort(String imapPort) {
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
    public String getSmtpPort() {
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
    public String getImapPort() {
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
}
