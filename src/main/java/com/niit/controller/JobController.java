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
	
	@Autowired
	private JobDao jobdao;

	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){		
		String username = (String) session.getAttribute("username");		
		if(session.getAttribute("username")==null){		
			Error error = new Error(5, "Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		User user = userdao.getUserByUsername(username); 
		if(user.getRole().equals("ADMIN")){
			try{
				
				job.setPostedOn(new Date());				
				jobdao.saveJob(job);		
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}catch (Exception e) {
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
