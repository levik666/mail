package com.mail.impl;

import com.mail.Reader;
import com.mail.dto.MessageDTO;

import javax.mail.*;
import java.io.IOException;
import java.util.*;

import static com.mail.Configuration.*;


public class MailReader implements Reader {

    private static final String PROTOCOL_IMAP = "imap";
    private static final String INBOX = "inbox";

    private final Properties props;

    public MailReader(final Properties props) {
        this.props = props;
    }

    @Override
    public List<MessageDTO> readInbox(final FolderMod mode) throws MessagingException, IOException {
        validation(mode);
        Store store = null;
        Folder folder = null;
        try {
            store = getStore();
            store.connect(
                            props.getProperty(MAIL_SMTP_HOST_KEY),
                            props.getProperty(MAIL_SMTP_USER_NAME_KEY),
                            props.getProperty(MAIL_SMTP_PASSWORD_KEY)
            );
            folder = store.getFolder(INBOX);
            if(!folder.isOpen()) {
                folder.open(mode.getMod());
            }
            return convertMessages(folder.getMessages());
        } finally {
            close(store, folder);
        }
    }

    private List<MessageDTO> convertMessages(Message[] messages) throws MessagingException, IOException {
        if (messages.length > 0) {
            final List<MessageDTO> messageResult = new ArrayList<>(messages.length + 2);
            for(Message message : messages) {
                MessageDTO messageDTO = new MessageDTO(message);
                messageResult.add(messageDTO);
            }
            return messageResult;
        }
        return new ArrayList<>();
    }

    private void close(final Store store, final Folder folder) throws MessagingException {
        try {
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
        } finally {
            if (store != null) {
                store.close();
            }
        }
    }

    private void validation(final FolderMod mode) {
        Objects.requireNonNull(mode, "'mode' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_HOST_KEY), "'" + MAIL_SMTP_HOST_KEY + "' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_USER_NAME_KEY), "'" + MAIL_SMTP_USER_NAME_KEY + "' can't be not null");
        Objects.requireNonNull(props.getProperty(MAIL_SMTP_PASSWORD_KEY), "'" + MAIL_SMTP_PASSWORD_KEY + "' can't be not null");
    }

    private Store getStore() throws MessagingException {
        return getSession().getStore(PROTOCOL_IMAP);
    }

    private Session getSession() {
        return Session.getDefaultInstance(props);
    }

}
