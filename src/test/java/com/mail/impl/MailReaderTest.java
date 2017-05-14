package com.mail.impl;

import com.mail.Reader;
import com.mail.config.ConfigurationLoader;
import com.mail.dto.MessageDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class MailReaderTest {

    private Reader mailReader;
    private static Properties props = new Properties();;

    @BeforeClass
    public static void configurationLoad() throws IOException {
        props = new ConfigurationLoader("emil_read.properties").load();
    }

    @Before
    public void setUp() throws Exception {
        mailReader = new MailReader(props);
    }

    @Test
    public void readInboxWithReadOnly() throws Exception {
        List<MessageDTO> messages = mailReader.readInbox(FolderMod.READ_WRITE);
        assertNotNull(messages);
        print(messages);
    }

    @Test
    public void readInboxWithReadWrite() throws Exception {
        List<MessageDTO> messages = mailReader.readInbox(FolderMod.READ_ONLY);
        assertNotNull(messages);
        print(messages);
    }

    private void print(final List<MessageDTO> messages) throws MessagingException, IOException {
        System.out.println("messages count : " + messages.size());
        for(final MessageDTO message : messages) {
            System.out.println("---------------------------------------");
            System.out.println("To: " + Arrays.toString(message.getTo()));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + Arrays.toString(message.getFrom()));
            System.out.println("Text: " + message.getBody());
            System.out.println("---------------------------------------");
            System.out.println();
        }
    }

}