package com.mail.impl;

import com.mail.Sender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Properties;

import static com.mail.Configuration.*;

public class MailSender implements Sender {

    private static final String MAIL_SMTP_PROTOCOL = "smtp";

    private final Properties props;

    public MailSender(final Properties props) {
        this.props = props;
    }

    @Override
    public void send(final String to, final String subject, final String body) throws MessagingException {
        validation(to, subject, body);
        Session session = getSession();
        Transport transport = null;
        try {
            transport = session.getTransport(MAIL_SMTP_PROTOCOL);
            if (transport != null) {
                if (!transport.isConnected()){
                    transport.connect(props.getProperty(MAIL_SMTP_HOST_KEY),
                                        Integer.parseInt(props.getProperty(MAIL_SMTP_PORT_KEY)),
                                        props.getProperty(MAIL_SMTP_USER_NAME_KEY),
                                        props.getProperty(MAIL_SMTP_PASSWORD_KEY));
                }
                Transport.send(buildMessage(to, props.getProperty(MAIL_SMTP_FROM_KEY), subject, body, session));
            }
        } finally {
            if (transport != null) {
                transport.close();
            }
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

    private Session getSession() {
        return Session.getDefaultInstance(props, new SimpleAuthenticator(
                    props.getProperty(MAIL_SMTP_USER_NAME_KEY), props.getProperty(MAIL_SMTP_PASSWORD_KEY)
                )
        );
    }

    private Message buildMessage(final String to, final String from, final String subject, final String body, final Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        return message;
    }
}
