package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Blog;
import com.niit.model.BlogComment;

@Repository
@Transactional
public class BlogDaoImpl implements BlogDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveBlog(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.save(blog);
	}

	@SuppressWarnings("unchecked")
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

	public void addBlogComment(BlogComment bc) {
		Session session = sessionFactory.getCurrentSession();
		session.save(bc);
		
	}

	@SuppressWarnings("unchecked")
	public List<BlogComment> getAllBlogComments(int blogId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogComment where blog.bid=?");
		query.setInteger(0,blogId);
		return query.list();
	}

}
