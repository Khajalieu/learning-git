package com.mywebcontainer;

import com.myservlets.HttpServlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SimpleWebContainer {
    private final int port;
    private String configFileName;
    private Map<String, HttpServlet> handlers = new HashMap<>();

    public SimpleWebContainer(int port, String configFileName) {
        this.port = port;
        this.configFileName = configFileName;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread handler = new SocketHandler(socket, handlers);
            handler.start();
        }
    }

    private void loadProperties() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
        if (inputStream == null) {
            throw new RuntimeException("Unable to locate file");
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((key, value) -> {
            HttpServlet servlet = getServletInstance((String) value);
            servlet.init();
            handlers.put((String) key, servlet);
        });
    }


    private HttpServlet getServletInstance(String className) {
        try {
            return (HttpServlet) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        SimpleWebContainer simpleWebContainer = new SimpleWebContainer(8888, "config.properties");
        simpleWebContainer.loadProperties();
        simpleWebContainer.start();



        //        simpleWebContainer.handlers.forEach((url, httpServlet) -> {
//            System.out.println(url);
//            httpServlet.doPost();
//        });

    }


}
