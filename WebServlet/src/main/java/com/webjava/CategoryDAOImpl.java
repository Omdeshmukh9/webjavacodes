package com.webjava;

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

@WebServlet("/Category")
public class CategoryDAOImpl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/onlineshopping", "root", "omdesh87");
                 PreparedStatement ps = connection.prepareStatement("select * from category")) {
                
                ResultSet rs = ps.executeQuery();
                
                out.println("<html><body>");
                out.println("<h2>Product Categories</h2>");
                out.println("<table border='1' cellpadding='5'>");
                out.println("<tr><th>Name</th><th>Description</th><th>Image</th></tr>");
                
                while (rs.next()) {
                    out.println("<tr>");
                    // Links to Products servlet with categoryId as a query parameter
                    out.println("<td><a href='Products?categoryId=" + rs.getInt("categoryId") + "'>" 
                                + rs.getString("categoryName") + "</a></td>");
                    out.println("<td>" + rs.getString("categoryDescription") + "</td>");
                    out.println("<td><img src='Images/" + rs.getString("categoryImageUrl") 
                                + "' height='80' width='80'/></td>");
                    out.println("</tr>");
                }
                out.println("</table></body></html>");
            }
        } catch (Exception e) {
            out.println("Error fetching categories: " + e.getMessage());
            e.printStackTrace();
        }
    }
}