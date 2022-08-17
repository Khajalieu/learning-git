package com.myservlets;

public class HelloServlet extends HttpServlet {
    @Override
    public void init() {
        System.out.println("Initializing HelloServlet");
    }

    @Override
    public void doGet() {
        System.out.println("This is HelloServlet overriding doGet method");
    }

}
