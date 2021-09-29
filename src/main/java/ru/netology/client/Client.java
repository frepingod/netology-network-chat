package ru.netology.client;

import ru.netology.Const;
import ru.netology.log.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Logger log = Logger.getInstance();

    private Scanner scanner;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client() {
        try {
            scanner = new Scanner(System.in);
            socket = new Socket(Const.HOST, Const.PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            log.log("Клиенту предлагают ввести свой никнейм");
            System.out.println("Введите свой никнейм:");
            out.println(scanner.nextLine());

            Receiver receiver = new Receiver(in);
            receiver.start();

            String message = "";
            while (!"exit".equals(message)) {
                message = scanner.nextLine();
                out.println(message);
            }

            receiver.interrupt();
        } catch (IOException e) {
            log.log("Ошибка в конструкторе у класса " + Client.class.getName());
            e.printStackTrace();
        } finally {
            log.log("Закрытие потоков у класса " + Client.class.getName());
            closeAll();
        }
    }

    private void closeAll() {
        try {
            scanner.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            log.log("Ошибка при закрытии потоков у класса " + Client.class.getName());
            e.printStackTrace();
        }
    }

    private class Receiver extends Thread {

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
}