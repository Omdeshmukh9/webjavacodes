package com.webjava.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.web.entity.User;
import com.webjava.UserDAOImpl;
import com.webjava.exceptions.UserException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Extract data from the incoming form request
        String usernameParam = request.getParameter("username");
        String passwordParam = request.getParameter("password");
        String emailParam = request.getParameter("email");
        String nameParam = request.getParameter("name");
        String cityParam = request.getParameter("city");

        // 2. Create the User object and populate it using setters
        User user = new User();
        user.setUserName(usernameParam);
        user.setPassword(passwordParam);
        user.setEmail(emailParam);
        user.setName(nameParam);
        user.setCity(cityParam);

      try {
		UserDAOImpl objuser = new UserDAOImpl();
		
		  boolean isRegisterd = objuser.RegisterUser(user);
		  
		  if(isRegisterd) {
			  response.sendRedirect("login.html");
		  }else {
			  response.sendRedirect("Register.html");
		  }
	} catch (UserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		response.sendRedirect("login.html");
	}
    }
}