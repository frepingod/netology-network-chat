package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

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

            System.out.println("Введите свой никнейм:");
            String name = scanner.nextLine();
            out.println(name);
            System.out.println(in.readLine());

            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);
                System.out.println(in.readLine());
                if ("exit".equals(message)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            scanner.close();
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}