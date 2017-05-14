package com.mail;

import java.util.Properties;

public class Configuration {

    public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
    public static final String MAIL_SMTP_USER_NAME_KEY = "username";
    public static final String MAIL_SMTP_PASSWORD_KEY = "password";
    public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
    public static final String MAIL_SMTP_FROM_KEY = "mail.smtp.from";

    public Properties getGmailSendProps(){
        //TODO move to Properties file
        Properties props = new Properties();
        //Configuration
        props.put(MAIL_SMTP_HOST_KEY, "smtp.gmail.com");
        props.put(MAIL_SMTP_PORT_KEY, "465");

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Gmail credentials
        props.put(MAIL_SMTP_USER_NAME_KEY, "mtest197@gmail.com");
        props.put(MAIL_SMTP_PASSWORD_KEY, "Qwertylol");
        return props;
    }

    public Properties getGmailReadProps(){
        //TODO move to Properties file
        Properties props = new Properties();
        //Configuration
        props.put("mail.protocol", "imap");
        props.put(MAIL_SMTP_HOST_KEY, "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.fallback", false);
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.imap.socketFactory.port", "993");

        //Gmail credentials
        props.put(MAIL_SMTP_USER_NAME_KEY, "mtest197@gmail.com");
        props.put(MAIL_SMTP_PASSWORD_KEY, "Qwertylol");
        return props;
    }
}
