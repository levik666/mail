package com.mail.impl;

import com.mail.Sender;
import com.mail.config.ConfigurationLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static com.mail.config.Configuration.*;

public class MailSenderTest {

    private static final String EMPTY = "";
    private static final String TO_VALUE = "zhenya12@gmail.com";
    private static final String SUBJECT_VALUE = "Test Subject";
    private static final String BODY_VALUE = "Simple Message";

    private static Properties props;

    private Sender sender;

    @BeforeClass
    public static void configurationLoad() throws IOException {
        props = new ConfigurationLoader("emil_send.properties").load();
    }

    @Before
    public void setUp() throws Exception {
        sender = new MailSender(props);
    }

    @Test(expected = NullPointerException.class)
    public void validateToValue() throws Exception {
        sender.send(null, EMPTY, EMPTY, EMPTY);
    }

    @Test(expected = NullPointerException.class)
    public void validateSubjectValue() throws Exception {
        sender.send(EMPTY, EMPTY, null, EMPTY);
    }

    @Test(expected = NullPointerException.class)
    public void validateBodyValue() throws Exception {
        sender.send(EMPTY, EMPTY, EMPTY, null);
    }

    @Test(expected = NullPointerException.class)
    public void validateFromValue() throws Exception {
        sender.send(EMPTY,null, EMPTY, EMPTY);
    }

    @Test
    public void send() throws Exception {
        sender.send(TO_VALUE, MAIL_SMTP_USER_NAME_KEY, SUBJECT_VALUE, BODY_VALUE);
    }


}