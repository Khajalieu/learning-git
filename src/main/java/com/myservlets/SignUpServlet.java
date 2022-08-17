package com.myservlets;

public class SignUpServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("Initializing SignUpServlet");
    }

    @Override
    public void doPost() {
        System.out.println("This is SignUpServlet overriding doPost method");
    }
}
