package ru.netology.server;

import ru.netology.Config;

public class MainServer {

    public static void main(String[] args) {
        Config config = Config.getInstance();
        Server server = new Server();
        server.listen(config.getPort());
    }
}