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
import com.niit.model.Error;
import com.niit.model.Job;
import com.niit.model.User;

@Controller
public class JobController {
	
	@Autowired
	private UserDao userdao;
	
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
}
