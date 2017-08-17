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

import com.niit.dao.BlogDao;
import com.niit.dao.UserDao;
import com.niit.model.Blog;
import com.niit.model.Error;
import com.niit.model.User;

@Controller
public class BlogController {
	
	@Autowired
	private BlogDao blogdao;
	
	@Autowired
	private UserDao userdao;
	
	@RequestMapping(value="/saveblog",method=RequestMethod.POST)
	public ResponseEntity<?> saveBlog(@RequestBody Blog blog,HttpSession session){
		if(session.getAttribute("username")==null){		
			Error error = new Error(5, "Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username = (String) session.getAttribute("username");
		User user = userdao.getUserByUsername(username);
		blog.setPostedOn(new Date());
		blog.setPostedBy(user);
		try{
			blogdao.saveBlog(blog);
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}catch(Exception e){
			Error error = new Error(7,"Unable to Post Blog");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}