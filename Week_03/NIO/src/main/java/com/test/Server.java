package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+100);

        ServerSocket serverSocket = new ServerSocket(port);

        while (true){
            Socket socket = serverSocket.accept();
            executorService.execute(()-> {
                service(socket);
            });
        }

    }

    public static void service(Socket serverSocket){
        {
            try {
                PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Content-Type:text/html;charset=utf-8");
                String body = "hello,nio";
                printWriter.println("Content-Length:" + body.getBytes().length);
                printWriter.println();
                printWriter.write(body);
                printWriter.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
