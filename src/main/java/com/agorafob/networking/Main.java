package com.agorafob.networking;

import ua.ithillel.networking.client.Client;
import ua.ithillel.networking.server.Server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Server server = new Server();
        ServerThread serverThread = new ServerThread(server);
        serverThread.start();

        scanner.nextLine();

        Client client = new Client();
        client.sendMessage("Привет, Сокет! - 1", "localhost", 1777);

        scanner.nextLine();

        client.sendMessage("Привет, Сокет! - 2", "localhost", 1777);

        scanner.nextLine();

        client.sendMessage("Привет, Сокет! - 3", "localhost", 1777);

        server.stop();
    }
}

class ServerThread extends Thread {
    Server server;

    public ServerThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.start(1777);
    }
}
