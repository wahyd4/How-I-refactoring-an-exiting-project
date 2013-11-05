package com.messpush.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.NDC;

/**
 * 实现log4j的过滤器
 */
@WebFilter("/Log4jFilter")
public class Log4jFilter implements Filter {

    /**
     * Default constructor. 
     */
    public Log4jFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * 执行过滤操作
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 
		HttpServletRequest myRequest = (HttpServletRequest) request;
		HttpServletResponse myResponse = (HttpServletResponse) response;
		 myRequest.setCharacterEncoding("utf-8");
		 myResponse.setCharacterEncoding("utf-8");
	    String userInfo = null;
	    //用户客户端GUID信息
	    userInfo  = myRequest.getParameter("user");
	     //如果GUID不存在则尝试获取用户ID，表明其实从网络登录
	     if(userInfo ==null){
	    	 //  userInfo =  session.getAttribute("userid").toString();
	     }
	    
	    if(userInfo!=null){
	    	
	    	//用户已登录,将用户ID放入NDC中
	       NDC.push(userInfo); 
	    	chain.doFilter(myRequest, myResponse);
	    	return ;
	    }
	      chain.doFilter(myRequest, myResponse);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
