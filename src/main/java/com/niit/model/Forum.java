package com.niit.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.stereotype.Component;


@Entity
@Component
@Table(name="Forum")
public class Forum {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FORUM_ID")
	private int id;
	
	private String forumTitle;
	
	
	private boolean status;
	
	@Lob
	private String description;
	
	@OneToMany(mappedBy="forum", cascade = CascadeType.ALL)
	List<ForumPosts> forumpost;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdOn;
	
	@Column(name="NumberOfPosts")
	private int nfp;
	
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String forumTitle) {
		this.forumTitle = forumTitle;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ForumPosts> getForumpost() {
		return forumpost;
	}

	public void setForumpost(List<ForumPosts> forumpost) {
		this.forumpost = forumpost;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getNfp() {
		return nfp;
	}

	public void setNfp(int nfp) {
		this.nfp = nfp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
