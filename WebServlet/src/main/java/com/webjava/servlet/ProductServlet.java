package com.webjava.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Extract the unique category ID sent from the Category page hyperlink
        String categoryId = request.getParameter("categoryId");
        
        // Early safety check: bounce back if someone visits the URL directly without an ID
        if (categoryId == null || categoryId.trim().equals("")) {
            out.println("<html><body>");
            out.println("<h3>Invalid Request: No product category selected.</h3>");
            out.println("<a href='Category'>Return to Categories</a>");
            out.println("</body></html>");
            return;
        }
        
        try {
            // Load the MySQL Database Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to your local schema
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/onlineshopping", "root", "omdesh87");
                 PreparedStatement ps = connection.prepareStatement("select * from product where categoryId = ?")) {
                
                ps.setString(1, categoryId);
                
                try (ResultSet rs = ps.executeQuery()) {
                    out.println("<html>");
                    out.println("<head><title>Products Marketplace</title></head>");
                    out.println("<body>");
                    out.println("<h2>Products Available In This Category</h2>");
                    out.println("<table border='1' cellpadding='6' cellspacing='0'>");
                    out.println("<tr style='background-color:#f2f2f2;'>");
                    out.println("<th>Product Name</th>");
                    out.println("<th>Price</th>");
                    out.println("<th>Preview</th>");
                    out.println("</tr>");
                    
                    boolean hasItems = false;
                    while (rs.next()) {
                        hasItems = true;
                        String prodImg = rs.getString("productImageUrl");
                        
                        // Smart extension fix: checks if file name is missing its image type suffix
                        if (prodImg != null && !prodImg.toLowerCase().endsWith(".jfif") && !prodImg.toLowerCase().endsWith(".jpg")) {
                            prodImg += ".jfif";
                        }
                        
                        out.println("<tr>");
                        out.println("<td><strong>" + rs.getString("productName") + "</strong></td>");
                        out.println("<td>$" + rs.getDouble("productPrice") + "</td>");
                        // Points directly to the corrected deployment folder path
                        out.println("<td><img src='Images/" + prodImg + "' height='80' width='80' alt='Product Image'/></td>");
                        out.println("</tr>");
                    }
                    
                    // User friendly empty state handling
                    if (!hasItems) {
                        out.println("<tr><td colspan='3' style='text-align:center; color:gray;'>");
                        out.println("No product inventory matches this category selection currently.");
                        out.println("</td></tr>");
                    }
                    
                    out.println("</table>");
                    out.println("<br/>");
                    out.println("<a href='Category'>← Back to Main Categories</a>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        } catch (Exception e) {
            out.println("<h3>An error occurred while generating inventory tables: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
