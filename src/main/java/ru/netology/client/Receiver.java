package ru.netology.client;

import ru.netology.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class Receiver extends Thread {

    private final Logger log = Logger.getInstance();

    private final BufferedReader in;

    public Receiver(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            log.log("Ошибка в методе run() у класса " + Receiver.class.getName());
            e.printStackTrace();
        }
    }
}