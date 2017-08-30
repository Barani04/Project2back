package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.model.User;
import com.niit.service.EmailService;
import com.niit.service.Error;

@Controller
public class UserController {
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			User duplicateUser = userdao.validateUsername(user.getUsername());
			if (duplicateUser != null) {
				Error error = new Error(2, "Username already Exists....");
				return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);
			}
			User duplicateEmail = userdao.validateEmail(user.getEmail());
			if (duplicateEmail != null) {
				Error error = new Error(3, "Email address already exists.... ");
				return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);
			}
			userdao.registerUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			Error error = new Error(1, "Unable to register user...");
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/validateuser", method = RequestMethod.POST)
	public ResponseEntity<?> validateUser(@RequestBody User user,HttpSession session) {
		User validuser = userdao.login(user);
		System.out.println(user.getEmail() + "--" + user.getUsername() + "--" + user.getPassword() + "--"
				+ user.getFirstname() + "--" + user.getLastname() + "--" + user.getPhonenumber());
		if (validuser == null) {
			Error error = new Error(4, "Invalid Username/Password");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		else if(validuser.isActivated()==false){
			Error error = new Error(8,"Your Account is not yet activated..!Wait for Activation Mail");
			return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		validuser.setOnline(true);
		userdao.update(validuser);
		session.setAttribute("username", validuser.getUsername());
		return new ResponseEntity<User>(validuser, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		if(session.getAttribute("username")==null){
			Error error = new Error(5, "UnAuthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username = (String) session.getAttribute("username");
		System.out.println(username);
		User user = userdao.getUserByUsername(username);
		user.setOnline(false);
		userdao.update(user);
		session.removeAttribute(username);
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session) {
		if(session.getAttribute("username")==null){
			Error error = new Error(5, "UnAuthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username = (String) session.getAttribute("username");
		System.out.println(username);
		User user = userdao.getUserByUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session){
		if(session.getAttribute("username")==null){
			Error error = new Error(5, "UnAuthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		try{
		userdao.update(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			Error error =new Error(6,"Unable to Edit Your Profile");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getusers/{activated}",method=RequestMethod.GET)
	public ResponseEntity<?> getUserReq(@PathVariable int activated,HttpSession session){
		if(session.getAttribute("username")==null){
			Error error = new Error(5, "Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<User> users=userdao.getUsers(activated);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@RequestMapping(value="/activateuser/{username}",method=RequestMethod.POST)
	public ResponseEntity<?> acctivateAcc(@PathVariable("username") String username,HttpSession session){
		if(session.getAttribute("username")==null){
			Error error = new Error(5, "Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		User user =userdao.getUserByUsername(username);
		user.setActivated(true);
		userdao.update(user);
		emailService.approvedUserNotify(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	
}
