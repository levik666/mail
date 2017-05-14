package com.mail.impl;

import com.mail.Reader;
import com.mail.Sender;
import com.mail.config.ConfigurationLoader;
import com.mail.dto.MessageDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MailIntegrationTest {

    private static final String SEND_TO_ME = "mtest197@gmail.com";
    private static final String SUBJECT = "Simple Subject";
    private static final String BODY = "Simple Body";

    private Reader mailReader;
    private Sender mailSender;
    private static Properties readProps = new Properties();
    private static Properties sendProps = new Properties();


    @BeforeClass
    public static void configurationLoad() throws IOException {
        readProps = new ConfigurationLoader("emil_read.properties").load();
        sendProps = new ConfigurationLoader("emil_send.properties").load();
    }

    @Before
    public void setUp() throws Exception {
        mailReader = new MailReader(readProps);
        mailSender = new MailSender(sendProps);
    }

    @Test
    public void sendAndReadMail() throws IOException, MessagingException {
        List<MessageDTO> messagesBeforeSend = mailReader.readInbox(FolderMod.READ_WRITE, true);
        assertNotNull(messagesBeforeSend);

        List<MessageDTO> messagesRecheckBeforeSend = mailReader.readInbox(FolderMod.READ_WRITE, true);
        assertNotNull(messagesRecheckBeforeSend);
        assertEquals("Inbox should be empty",0, messagesRecheckBeforeSend.size());

        mailSender.send(SEND_TO_ME, SEND_TO_ME, SUBJECT, BODY);

        List<MessageDTO> messages = mailReader.readInbox(FolderMod.READ_WRITE);
        assertNotNull(messages);
        assertEquals("Inbox should be empty",1, messages.size());

        MessageDTO message = messages.get(0);

        assertEquals("Subject should be the same as send before", SUBJECT, message.getSubject());
        assertEquals("Body should be the same as send before", BODY, message.getBody().replace("\r\n", ""));
    }


}
