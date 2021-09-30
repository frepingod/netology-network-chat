package ru.netology.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private final Logger log = Logger.getInstance();
    private static final String MESSAGE = "Test message LoggerTest";

    @Test
    void getInstance() {
        assertEquals(Logger.getInstance(), log);
    }

    @Test
    void log() {
        assertTrue(log.log(MESSAGE));
    }
}