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
			
			htmlMsg.append("<p style='font-size:20px;color:black;'>Welcome "+user.getFirstname()+"...!</p>");
			htmlMsg.append("<b style='font-size:18px;text-align: justify;margin-left:30px;color:black;'>Your account has been activated!</b><br/>");
			htmlMsg.append("<p style='text-align: justify;font-size:18px;margin-left:30px;color:black;'>Your Login Credentials...</p>");
			htmlMsg.append("<p style='text-align: justify;font-size:15px;margin-left:30px;color:black;'><b>UserName:</b>"+user.getUsername()+"</p>");
			htmlMsg.append("<p style='text-align: justify;font-size:15px;margin-left:30px;color:black;'><b>Password:</b>"+user.getPassword()+"</p>");
			htmlMsg.append("<p style='text-align: justify;font-size:18px;margin-left:30px;color:black;'>Login to your account: <a style='text-decoration:none;' href="+"http://localhost:8081/Project2Front/#!/login"+">Login</a></p><br/>");
			htmlMsg.append("<p style='text-align: justify;font-size:18px;margin-left:30px;color:black;'> Thanks For joining with us! </p><br/>");
			htmlMsg.append("<br/><br/>");
			htmlMsg.append("<span style='font-size:18px;color:black;'>Regards,</span><br/><span style='font-size:18px;color:black;'>EDUHUB Corp</span><br/>");
			
			
			
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
			
			htmlMsg.append("Dear <strong>"+blog.getPostedBy().getFirstname()+"</strong>,<br/>");
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
