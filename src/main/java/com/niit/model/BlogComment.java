package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="blogcomment")
public class BlogComment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="blog_id")
	private Blog blog;
	
	@ManyToOne
	@JoinColumn(name="commentedby")
	private User commentedBy;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date commentedOn;
	
	@Lob
	private String commentText;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public User getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(User commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Date getCommentedOn() {
		return commentedOn;
	}

	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

}
