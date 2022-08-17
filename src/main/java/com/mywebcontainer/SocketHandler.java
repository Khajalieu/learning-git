package com.mywebcontainer;

import com.myservlets.HttpServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;

public class SocketHandler extends Thread {

    private final Socket socket;
    Map<String, HttpServlet> handlers;

    public SocketHandler(Socket socket, Map<String, HttpServlet> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        BufferedReader reader;
        PrintWriter writer;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            writer = new PrintWriter(socket.getOutputStream());
            writer.println("HTTP/1.1 200 OK :)");
            writer.println("Content-Type: text/html");
            writer.println();
            writer.println("<html><body>Current Time:");
            writer.println(LocalDateTime.now());
            writer.println("</body></html>");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
