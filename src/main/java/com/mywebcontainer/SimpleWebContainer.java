package com.mywebcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class SimpleWebContainer {
    private final int port;

    public SimpleWebContainer(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            try (Socket socket = serverSocket.accept()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine();

                while (!line.isEmpty()) {
                    System.out.println(line);
                    line = reader.readLine();
                }

                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println("HTTP/1.1 200 OK :)");
                writer.println("Content-Type: text/html");
                writer.println();
                writer.println("<html><body>Current Time:");
                writer.println(LocalDateTime.now());
                writer.println("</body></html>");
                writer.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleWebContainer simpleWebContainer = new SimpleWebContainer(49150);
        simpleWebContainer.start();

    }
}
