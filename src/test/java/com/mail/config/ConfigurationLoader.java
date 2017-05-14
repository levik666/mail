package com.mail.config;

import com.mail.impl.MailReaderTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {

    private String name;

    public ConfigurationLoader(String name) {
        this.name = name;
    }

    public Properties load() throws IOException {
        Properties props = new Properties();
        InputStream config = null;
        try {
            config =  ConfigurationLoader.class.getClassLoader().getResourceAsStream(name);
            if (config == null) {
                throw new RuntimeException("Can't get config");
            }

            props.load(config);
        } finally {
            if (config != null) {
                config.close();
            }

        }

        return props;
    }
}
