package com.niit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*Dispatcher-Servlet.xml*/
@Configuration
@EnableWebMvc /*instead of <mvc:annotation-driven> in xml page*/
@ComponentScan(basePackages="com.niit.*") /*instead of <context:component-scan> in xml page*/
public class WebConfig extends WebMvcConfigurerAdapter {
	public void addResourceHandlers(ResourceHandlerRegistry reg){
		reg.addResourceHandler("/Resources/**")
		.addResourceLocations("/WEB-INF/Resources/");
		/*<mvc:resources mapping="/resources/**" location="/WEB-INF/resources/"/>*/
	}
}
