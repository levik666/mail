package com.mail;

import javax.mail.MessagingException;

public interface Sender {

    void send(String to, String subject, String body) throws MessagingException;
}
