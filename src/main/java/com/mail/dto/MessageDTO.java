package com.mail.dto;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

public class MessageDTO {

    private static final String TEXT_PLAIN = "text/plain";
    private static final String TEXT_HTML = "text/html";
    private static final String MULTIPART = "multipart/*";

    private Address[] to;
    private Address[] from;
    private String subject;
    private String body;

    public MessageDTO(Message message) throws MessagingException, IOException {
        this.to = message.getReplyTo();
        this.from = message.getFrom();
        this.subject = message.getSubject();
        this.body = parsContent(message);

    }

    private String parsContent(Message message) throws MessagingException, IOException {
        if (message.isMimeType(TEXT_PLAIN)) {
            return  message.getContent().toString();
        } else if (message.isMimeType(MULTIPART)) {
            MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType(TEXT_PLAIN)) {
                    return bodyPart.getContent().toString();
                } else if (bodyPart.isMimeType(TEXT_HTML)) {
                    return (String) bodyPart.getContent();
                }
            }
        }

        return "";
    }

    public Address[] getTo() {
        return to;
    }

    public Address[] getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
