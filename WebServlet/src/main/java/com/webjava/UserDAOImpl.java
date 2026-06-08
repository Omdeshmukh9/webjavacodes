package com.webjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.web.entity.User;
import com.webjava.exceptions.UserException;

public class UserDAOImpl implements UserDAO{
	Connection connection;
	PreparedStatement psRegisterUser;
	PreparedStatement psUserDetails;
	PreparedStatement psRemoveUser;
	public UserDAOImpl() throws UserException {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        String url = "jdbc:mysql://localhost:3306/onlineshopping";
	        String username = "root";
	        String password = "omdesh87";
	        
	        connection = DriverManager.getConnection(url, username, password);
	        
	        // FIXED: The closing parenthesis ensures this initializes properly
	        psRegisterUser = connection.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?)");
	        psUserDetails = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
	        psRemoveUser = connection.prepareStatement("DELETE FROM user WHERE username = ?");
	        
	    } catch (ClassNotFoundException e) {
	        throw new UserException("JDBC Driver not found: " + e.getMessage());
	    } catch (SQLException e) {
	        // CRITICAL: Throwing this prevents the object from being created with null statements
	        throw new UserException("Database setup failed. Statements are null: " + e.getMessage());
	    }
	}
	@Override
	public boolean RegisterUser(User objUser) throws UserException{
		try {
			psRegisterUser.clearParameters();
			psRegisterUser.setString(1, objUser.getUserName());
			psRegisterUser.setString(2, objUser.getPassword());
			psRegisterUser.setString(3, objUser.getName());
			psRegisterUser.setString(4, objUser.getEmail());
			psRegisterUser.setString(5, objUser.getCity());
			psRegisterUser.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new UserException("Failed to register user:"+e.getMessage());
		}		

	}
	@Override
	public User UserDetails(String username) throws UserException {
	    // 1. Safe-guard against null inputs
	    if (username == null || username.trim().isEmpty()) {
	        throw new UserException("Username parameter cannot be null or empty.");
	    }

	    try {
	        psUserDetails.clearParameters();
	        psUserDetails.setString(1, username.trim()); // trim() removes accidental spaces
	        
	        // Using try-with-resources to automatically close the ResultSet
	        try (ResultSet result = psUserDetails.executeQuery()) {
	            if (result.next()) {
	                User objUser = new User(username);
	                
	                // FIXED: Use actual database column names as strings, NOT the variable!
	                objUser.setUserName(result.getString("username")); 
	                objUser.setPassword(result.getString("password")); 
	                objUser.setName(result.getString("name")); 
	                
	                // If your User entity has these fields, map them too:
	                 objUser.setEmail(result.getString("email"));
	                 objUser.setCity(result.getString("city"));
	                
	                return objUser;
	            }
	        }
	    } catch (SQLException e) {
	        // FIXED: Stop swallowing the error. Throw it so you can see what went wrong.
	        throw new UserException("Database error while fetching user details: " + e.getMessage());
	    }
	    
	    // Returns null ONLY if the database successfully checked but found no user
	    return null; 
	}
	@Override
	public boolean RemoveUser(String username) throws UserException 
	{
		try {
			// TODO Auto-generated method stub
			psRemoveUser.clearParameters();
			if(psUserDetails.executeUpdate()>0)
			{
				return true;
			}
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			throw new UserException("Failed to register user:"+e.getMessage());
		}
		return false;
	}

	

}
