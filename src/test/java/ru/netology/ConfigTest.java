package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    private static final Config CONFIG = Config.getInstance();
    protected int port;
    protected String host;

    @BeforeEach
    void setUp() {
        final String path = "./config/settings.properties";
        try (FileReader fileReader = new FileReader(path)) {
            Properties props = new Properties();
            props.load(fileReader);

            port = Integer.parseInt(props.getProperty("port"));
            host = props.getProperty("host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance() {
        assertEquals(Config.getInstance(), CONFIG);
    }

    @Test
    void getPort() {
        assertEquals(CONFIG.getPort(), port);
    }

    @Test
    void getHost() {
        assertEquals(CONFIG.getHost(), host);
    }
}