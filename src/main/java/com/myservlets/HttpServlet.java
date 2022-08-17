package com.myservlets;

public abstract class HttpServlet {

    public void init() {
        System.out.println("This is the default init method");
    }

    public void service() {  //TODO:Add request and response objects as parameter

    }

    public void doGet() {
        System.out.println("This is the default doGet method");
    }

    public void doPost() {
        System.out.println("This is the default doPost method");
    }
}
