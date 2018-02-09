package com.light.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符编码过滤器，用来指定Request，response的characterEncoding为"UTF-8"。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class CharacterEncodeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}