package com.webjava;

import com.web.entity.User;
import com.webjava.exceptions.UserException;

public interface UserDAO 
{
	public boolean RegisterUser(User objUser) throws UserException ;
	public boolean RemoveUser(String username) throws UserException ;
	public User UserDetails(String username) throws UserException;

	
}
