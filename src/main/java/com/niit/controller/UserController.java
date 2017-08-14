package com.niit.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.User;
import com.niit.model.Error;
import com.niit.model.Job;

@Controller
public class UserController {
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private JobDao jobdao;

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
		validuser.setOnline(true);
		userdao.update(validuser);
		session.setAttribute("username", validuser.getUsername());
		return new ResponseEntity<User>(validuser, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
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
	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job/*,HttpSession session*/){
		System.out.println("fggvirnfgbvfsdvgnsdvhsdb");
		String username="admin";
		/*String username = (String) session.getAttribute("username");
		System.out.println(username+"dfxcfcf");
		
		if(session.getAttribute("username")==null){
			System.out.println("cdbvuvchndvcdjvcdvn");
			Error error = new Error(5, "Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}*/
		User user = userdao.getUserByUsername(username); 
		if(user.getRole().equals("ADMIN")){
			System.out.println("adjkcfndcghdc da");
			try{
				System.out.println("dbvbd cvhdbcvdcv dcvxdcv ");
				job.setPostedOn(new Date());
				System.out.println("dbvcdjncvAucjDACGHADCBD");
				jobdao.saveJob(job);
				System.out.println("cmdnhcjd bcbdx cv bxhvc xcbhvn z");
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}catch (Exception e) {
				System.out.println("dvgcbdxc vgxhbcb vzhcnc");
				Error error = new Error(7,"Unable to Post Job");
				return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else{
			Error error = new Error(6, "Access Denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
	}
}
