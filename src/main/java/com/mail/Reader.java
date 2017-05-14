package com.mail;

import com.mail.dto.MessageDTO;
import com.mail.impl.FolderMod;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface Reader {

    List<MessageDTO> readInbox(FolderMod folderMod) throws MessagingException, IOException;
}
