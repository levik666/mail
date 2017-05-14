package com.mail.impl;

import com.mail.Sender;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.MessagingException;
import java.util.Objects;
import java.util.Properties;

import static com.mail.Configuration.*;

public class MailSender implements Sender{

    private final Properties props;

    public MailSender(final Properties props) {
        this.props = props;
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        validation(to, subject, body);
        Email email = new SimpleEmail();
        email.setHostName(props.getProperty(MAIL_SMTP_HOST_KEY));
        email.setSmtpPort(Integer.parseInt(props.getProperty(MAIL_SMTP_PORT_KEY)));
        email.setAuthenticator(new DefaultAuthenticator(
                props.getProperty(MAIL_SMTP_USER_NAME_KEY),
                props.getProperty(MAIL_SMTP_PASSWORD_KEY))
        );
        email.setSSLOnConnect(true);
        try {
            email.setFrom(props.getProperty(MAIL_SMTP_USER_NAME_KEY));
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(to);
            email.send();
        } catch (EmailException exe) {
            throw new MessagingException("Can't sent message", exe);
        }
    }

    private void validation(final String to, final String subject, final String body) {
        Objects.requireNonNull(to, "'to' can't be not null");
        Objects.requireNonNull(subject, "'subject' can't be not null");
        Objects.requireNonNull(body, "'body' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_FROM_KEY), "'" + MAIL_SMTP_FROM_KEY +"' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_USER_NAME_KEY), "'" + MAIL_SMTP_USER_NAME_KEY +"' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_PASSWORD_KEY), "'" + MAIL_SMTP_PASSWORD_KEY +"' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_HOST_KEY), "'" + MAIL_SMTP_HOST_KEY +"' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_PORT_KEY), "'" + MAIL_SMTP_PORT_KEY +"' can't be not null");
        Objects.requireNonNull(Integer.parseInt(props.getProperty(MAIL_SMTP_PORT_KEY)), "'" + MAIL_SMTP_PORT_KEY +"' should be number");
    }
}
