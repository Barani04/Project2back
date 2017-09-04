package com.niit.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.model.Blog;
import com.niit.model.BlogComment;
import com.niit.model.Forum;
import com.niit.model.ForumPosts;
import com.niit.model.Job;
import com.niit.model.User;


@Configuration
@EnableTransactionManagement
public class DBConfig {
	@SuppressWarnings("rawtypes")
	@Bean
	public SessionFactory sessionFactory(){
		LocalSessionFactoryBuilder lsf= new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		Class classes[]=new Class[]{User.class,Job.class,Blog.class,BlogComment.class,Forum.class,ForumPosts.class};
		return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}

	@Bean
	public DataSource getDataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setUsername("collab");
		dataSource.setPassword("collab");
		return dataSource;
	}
	
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		
		return new HibernateTransactionManager(sessionFactory());
	}
}
