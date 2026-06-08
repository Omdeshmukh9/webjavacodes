package com.webjava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String userName = request.getParameter("username"); //
        String password = request.getParameter("password"); //
        
        try {
            // Load the MySQL Database Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); //
            
            // Establish Connection
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/onlineshopping", "root", "omdesh87"); //
                 
                 // Prepare SQL Statement to verify credentials against your database table
                 PreparedStatement psAuthenticate = connection.prepareStatement(
                    "select * from user where userName=? and password=?")) { //
                
                psAuthenticate.setString(1, userName); //
                psAuthenticate.setString(2, password); //
                
                try (ResultSet result = psAuthenticate.executeQuery()) { //
                    if (result.next()) { //
                        // Match found! Redirect the browser to the Category page
                        response.sendRedirect("Category"); //
                    } else {
                        // Credential mismatch! Bounce them back to the login screen
                        response.sendRedirect("login.html"); //
                    }
                }
            }
        } catch (SQLException e) { //
            out.println("We seem to have run into an issue, our team is already sleeping over it"); //
            e.printStackTrace(); //
        } catch (ClassNotFoundException e) { //
            e.printStackTrace(); //
        }
    }
}