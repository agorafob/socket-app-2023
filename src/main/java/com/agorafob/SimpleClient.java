package com.agorafob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8080);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String str = "GET / HTTP/1.1\r\n" + "Host:localhost\r\n\r\n";

        out.write(str.getBytes());

        int size;
        byte[] buffer = new byte[1024];
        while ((size= in.read(buffer))!= -1){
            System.out.println(new String(buffer,0,size));
        }

        socket.close();
        in.close();
        out.close();
    }
}
