package com.box.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "baseFilter", urlPatterns = "/*")
public class BaseFilter implements Filter{

	private static final Logger log = LoggerFactory.getLogger(BaseFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("BaseFilter init Start--->{}",System.currentTimeMillis());
		Filter.super.init(filterConfig);
		log.info("BaseFilter init End--->{}",System.currentTimeMillis());
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        long timeStart = System.currentTimeMillis();
//        log.info("过滤器doFilter Start..."+requestURI+"--->"+timeStart);
        if(requestURI.contains("testFailed")){
        	request.getRequestDispatcher("/failed").forward(request, response);
        }else{
        	chain.doFilter(request, response);
        }
        long timeEnd = System.currentTimeMillis();
//        log.info("过滤器doFilter End..."+requestURI+"--->"+timeEnd);
        log.info("{} runtime--->{}",requestURI,(timeEnd-timeStart));
	}
	
	@Override
	public void destroy() {
		log.info("BaseFilter destroy Start--->{}",System.currentTimeMillis());
		Filter.super.destroy();
		log.info("BaseFilter destroy End--->{}",System.currentTimeMillis());
	}

}
