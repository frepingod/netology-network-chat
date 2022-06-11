package ru.netology.client;

import ru.netology.Config;
import ru.netology.log.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final Logger LOGGER = Logger.getInstance();

    private Scanner scanner;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client() {
        try {
            final Config config = Config.getInstance();

            scanner = new Scanner(System.in);
            socket = new Socket(config.getHost(), config.getPort());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            LOGGER.log("Клиенту предлагают ввести свой никнейм");
            System.out.println("Введите свой никнейм:");
            out.println(scanner.nextLine());

            final Receiver receiver = new Receiver(in);
            receiver.start();

            String message = "";
            while (!"exit".equals(message)) {
                message = scanner.nextLine();
                out.println(message);
            }

            receiver.interrupt();
        } catch (IOException e) {
            LOGGER.log("Ошибка в конструкторе у класса " + Client.class.getName());
            e.printStackTrace();
        } finally {
            LOGGER.log("Закрытие потоков у класса " + Client.class.getName());
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
            LOGGER.log("Ошибка при закрытии потоков у класса " + Client.class.getName());
            e.printStackTrace();
        }
    }
}