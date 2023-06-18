package com.agorafob.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static ua.ithillel.networking.client.Client.BYE;

public class SocketThread extends Thread
{
    private final Socket fromClientSocket;

    public SocketThread(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        // Автоматически будут закрыты все ресурсы
        // Получив соединение начинаем работать с сокетом
        try (Socket localSocket = fromClientSocket;
             // Работаем с потоками ввода-вывода
             PrintWriter pw = new PrintWriter(localSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(localSocket.getInputStream()))) {

            // Читаем сообщения от клиента до тех пор пока он не скажет "bye"
            String str;
            while ((str = br.readLine()) != null) {
                // Печатаем сообщение
                System.out.println("Server got message: " + str);

                // Сравниваем с "bye" и если это так - выходим из цикла
                if (str.equals(BYE)) {
                    // Тоже говорим клиенту "bye" и выходим из цикла
                    System.out.println("Server send message: " + str);
                    pw.println(BYE);
                    break;
                } else {
                    // Посылаем клиенту ответ
                    str = "Server returns: " + str;
                    System.out.println("Server send message: " + str);
                    pw.println(str);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
