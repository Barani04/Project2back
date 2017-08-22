package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Blog;

@Repository
@Transactional
public class BlogDaoImpl implements BlogDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveBlog(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(blog);
	}

	public List<Blog> getBlogs(int approved) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where approved="+approved);
		return query.list();
	}

	public Blog getBlog(int bid) {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Blog where bid="+bid);
		
		return (Blog)query.uniqueResult();
	}

	public void updateBlog(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.update(blog);
		
	}

}
