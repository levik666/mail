package com.mail.impl;

import com.mail.Configuration;
import com.mail.Sender;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static com.mail.Configuration.*;

public class MailSenderTest {

    private static final String EMPTY = "";
    private static final String TO_VALUE = "zhenya12@gmail.com";
    private static final String SUBJECT_VALUE = "Test Subject";
    private static final String BODY_VALUE = "Simple Message";

    private Properties props;

    private Sender sender;

    @Before
    public void setUp() throws Exception {
        props = new Configuration().getGmailSendProps();
        sender = new MailSender(props);
    }

    @Test(expected = NullPointerException.class)
    public void validateToValue() throws Exception {
        sender.send(null, EMPTY, EMPTY);
    }

    @Test(expected = NullPointerException.class)
    public void validateSubjectValue() throws Exception {
        sender.send(EMPTY, null, EMPTY);
    }

    @Test(expected = NullPointerException.class)
    public void validateBodyValue() throws Exception {
        sender.send(EMPTY, EMPTY, null);
    }

    @Test(expected = NullPointerException.class)
    public void validateFromValue() throws Exception {
        sender.send(EMPTY, EMPTY, EMPTY);
    }

    @Test
    public void send() throws Exception {
        props.put(MAIL_SMTP_FROM_KEY, MAIL_SMTP_USER_NAME_KEY);
        sender.send(TO_VALUE, SUBJECT_VALUE, BODY_VALUE);
    }


}