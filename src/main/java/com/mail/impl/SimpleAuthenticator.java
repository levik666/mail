package com.mail.impl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SimpleAuthenticator extends Authenticator {

    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USER_NAME, PASSWORD);
    }
}
