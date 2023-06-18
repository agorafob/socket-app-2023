package com.agorafob.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final String BYE = "bye";

    public void sendMessage(String message, String host, int port) {
        try {
            // Открыть сокет (Socket) для обращения к локальному компьютеру
            // Сервер мы будем запускать на этом же компьютере
            // Это специальный класс для сетевого взаимодействия c клиентской стороны
            Socket socket = new Socket(host, port);
            // Пишем, что стартовали клиент
            System.out.println("Client is connected to server");


            // Создать поток для чтения символов из сокета
            // Для этого надо открыть поток сокета - socket.getInputStream()
            // Потом преобразовать его в поток символов - new InputStreamReader
            // И уже потом сделать его читателем строк - BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Создать поток для записи символов в сокет
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // Отправляем тестовую строку в сокет
            System.out.println("Client send message: " + message);
            pw.println(message);
            // Входим в цикл чтения, что нам ответил сервер
            while ((message = br.readLine()) != null) {
                // Если пришел ответ “bye”, то заканчиваем цикл
                if (message.equals(BYE)) {
                    System.out.println("Client got message: " + BYE);
                    break;
                }
                // Печатаем ответ от сервера на консоль для проверки
                System.out.println("Client got message: " + message);

                // Посылаем ему "bye" для окончания "разговора"
                System.out.println("Client send message: " + BYE);
                pw.println(BYE);
            }

            br.close();
            pw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
