package com.niit.dao;

import java.util.List;

import com.niit.model.Blog;
import com.niit.model.BlogComment;

public interface BlogDao {
	
	void saveBlog(Blog blog);

	List<Blog> getBlogs(int approved);

	Blog getBlog(int id);

	void updateBlog(Blog blog);

	void addBlogComment(BlogComment bc);
	
	List<BlogComment>	getAllBlogComments(int blogId);

}
