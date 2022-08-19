package com.mywebcontainer;

import com.myservlets.HttpRequest;
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
            writer = new PrintWriter(socket.getOutputStream());
            HttpRequest httpRequest = new HttpRequest(reader);
            if (!httpRequest.parse()) {
                writer.println("HTTP/1.1 500 Internal Server error");
                writer.println("Content-Type: text/plain");
                writer.println();
                writer.println("<html><body>Cannot process your request</body></html>");
                writer.flush();
            }
            System.out.println("====================================================");
            System.out.println("method:" + httpRequest.getMethod());
            System.out.println("====================================================");
            System.out.println("path:" + httpRequest.getPath());
            System.out.println("====================================================");
            httpRequest.getRequestParameters().forEach((paramName, paramValue) -> System.out.println(paramName + ":" + paramValue));
            System.out.println("====================================================");
            httpRequest.getHeaders().forEach((headerName, headerValue) -> System.out.println(headerName + ":" + headerValue));
            System.out.println("====================================================");


            writer.println("Http/1.1 200 0k :)");
            writer.println("Content-Type: text/html");
            writer.println();
            writer.println("<html><body> Current time");
            writer.println(LocalDateTime.now());
            writer.println("</body></html>");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
