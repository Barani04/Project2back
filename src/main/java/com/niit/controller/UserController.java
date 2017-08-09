package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.model.User;
import com.niit.model.Error;

@Controller
public class UserController {
	@Autowired
	private UserDao userdao;
	
	@RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try{
			User duplicateUser = userdao.validateUsername(user.getUsername());
			if(duplicateUser!=null){
				Error error = new Error(2, "Username already Exists....");
				return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
			}
			User duplicateEmail = userdao.validateEmail(user.getEmail());
			if(duplicateEmail!=null){
				Error error = new Error(3, "Email address already exists.... ");
				return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);	
			}
			userdao.registerUser(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}catch(Exception e) {
			Error error = new Error(1,"Unable to register user...");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/validateUser",method=RequestMethod.POST)
	public ResponseEntity<?> validateUser(@RequestBody User user){
		User validuser=userdao.login(user);
		if(validuser==null){
			Error error =new Error(4, "Invalid Username/Password");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		validuser.setOnline(true);
		userdao.update(validuser);
		return new ResponseEntity<User>(validuser,HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session){
		String username = (String) session.getAttribute("username");
		User user = userdao.getUserByUsername(username);
		user.setOnline(false);
		userdao.update(user);
		session.removeAttribute("username");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
