package ru.netology;

public class MainServer {

    public static void main(String[] args) {
        Server server = new Server();
        server.listen(Const.PORT);
    }
}