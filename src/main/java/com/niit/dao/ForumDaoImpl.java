package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Forum;

@Repository
@Transactional
public class ForumDaoImpl implements ForumDao {

	@Autowired
	private SessionFactory	sessionFactory;
	
	public void saveForum(Forum forum) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(forum);
		
	}
	

}
