package com.box.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class BaseListener implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(BaseListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("BaseListener contextInitialized Start--->{}",System.currentTimeMillis());
		ServletContextListener.super.contextInitialized(sce);
		log.info("BaseListener contextInitialized End--->{}",System.currentTimeMillis());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("BaseListener contextDestroyed Start--->{}",System.currentTimeMillis());
		ServletContextListener.super.contextDestroyed(sce);
		log.info("BaseListener contextDestroyed End--->{}",System.currentTimeMillis());
	}
}
