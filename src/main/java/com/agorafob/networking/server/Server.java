package com.agorafob.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private boolean isServerWaitingConnection;
    private ServerSocket servSocket;

    public void start(int port) {
        try {
            // Открыть серверный сокет (ServerSocket)
            // Это специальный класс для сетевого взаимодействия с серверной стороны
            servSocket = new ServerSocket(port);

            isServerWaitingConnection = true;
            // Входим в бесконечный цикл - ожидаем соединения
            while (isServerWaitingConnection) {
                System.out.println("Waiting for a connection on " + port);

                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();

                // Стартуем новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void stop() {
        isServerWaitingConnection = false;
        System.out.println("Server stopped");
        try {
            if (!servSocket.isClosed()) {
                servSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
