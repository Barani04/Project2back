package com.niit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Component
@Table(name="Forum_Posts")
public class ForumPosts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FPost_Id")
	private int id;

	private String topic;
	
	@ManyToOne
	@JoinColumn(name="Forum_id")
	private Forum forum;
	
	@Column(name = "PostedBy")
	private String username;
	
	private String description;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date postDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Forum getforum() {
		return forum;
	}

	public void setforum(Forum forum) {
		this.forum = forum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}
