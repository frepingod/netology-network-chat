package ru.netology.server;

import ru.netology.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private final Logger log = Logger.getInstance();

    private ServerSocket serverSocket;
    private Socket socket;

    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<>());

    public void listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.log("Server started!");
            System.out.println("Server started!");

            while (true) {
                socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connections.add(connection);
                connection.start();
            }
        } catch (IOException e) {
            log.log("Ошибка в методе listen() у класса " + Server.class.getName());
            e.printStackTrace();
        } finally {
            log.log("Закрытие потоков у класса " + Server.class.getName());
            closeAll();
        }
    }

    private void closeAll() {
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            log.log("Ошибка при закрытии потоков у класса " + Server.class.getName());
            e.printStackTrace();
        }
    }

    private class Connection extends Thread {

        private BufferedReader in;
        private PrintWriter out;

        public Connection(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                log.log("Ошибка в конструкторе у класса " + Connection.class.getName());
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String name = in.readLine();
                log.log(name + " cames now");
                sendMessageAllConnection(name + " cames now");

                String message;
                while (true) {
                    message = in.readLine();
                    if ("exit".equals(message)) {
                        break;
                    }
                    log.log(name + ": " + message);
                    sendMessageAllConnection(name + ": " + message);
                }

                log.log(name + " has left");
                sendMessageAllConnection(name + " has left");
            } catch (IOException e) {
                log.log("Ошибка в методе run() у класса " + Connection.class.getName());
                e.printStackTrace();
            } finally {
                log.log("Закрытие потоков у класса " + Connection.class.getName());
                closeAll();
            }
        }

        private void closeAll() {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage(String message) {
            out.println(message);
        }

        private void sendMessageAllConnection(String message) {
            for (Connection connection : connections) {
                connection.sendMessage(message);
            }
        }
    }
}