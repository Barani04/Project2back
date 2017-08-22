package com.niit.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.niit.model.Blog;
import com.niit.model.User;

@Service("emailService")
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private static String from="EduHub";
	
	public void approvedUserNotify(User user) {
		
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		
		MimeMessageHelper helper =null;
		
		try{
			helper = new MimeMessageHelper(mimeMsg, false, "utf-8");
			StringBuilder htmlMsg = new StringBuilder();
			
			htmlMsg.append("<h1>Welcome"+user.getFirstname()+" "+user.getLastname()+"!</h1>");
			htmlMsg.append("<p>Your account has been activated!</p><br/>");
			htmlMsg.append("<p>Thanks For joining with us!</p><br/>");
			
			mimeMsg.setContent(htmlMsg.toString(), "text/html");
			
			helper.setTo(user.getEmail());
			helper.setSubject("WELCOME TO EduHub");
			helper.setFrom(from);
			
			mailSender.send(mimeMsg);
			
		}catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	public void approvedBlogsNotify(Blog blog) {
		
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = null;
		
		try{
			helper = new MimeMessageHelper(mimeMsg, false, "text/html");
			StringBuilder htmlMsg = new StringBuilder();
			
			htmlMsg.append("Dear <b>"+blog.getPostedBy().getFirstname()+"</b>,<br/>");
			htmlMsg.append("<p>Your Blog has been approved by Admin!<p>");
			htmlMsg.append("<p>Keep Posting....!</p>");
			
			mimeMsg.setContent(htmlMsg.toString(), "text/html");
			
			helper.setTo(blog.getPostedBy().getEmail());
			helper.setSubject("Your Blog was Approved");
			helper.setFrom(from);
			
			mailSender.send(mimeMsg);
			
			
		}catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
}
