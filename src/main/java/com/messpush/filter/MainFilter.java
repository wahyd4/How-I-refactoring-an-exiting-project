package com.messpush.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.messpush.tool.MyTools;

/**
 * 设置页面过滤器
 */
@WebFilter("/MainFilter")
public class MainFilter implements Filter {

	protected FilterConfig filterConfig = null;
	private final String sessionKey = "userid";// 需要检查的在session保存的键userid
	private List<String> noCheckURLList = new ArrayList<String>();

	public void destroy() {

		noCheckURLList.clear();
	}

	/**
	 * 过滤请求
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession();// 获取session
		//如果没有需要验证的或者在不需要跳转的页面则不管请求
		if (sessionKey == null) {		
			chain.doFilter(request, response);
			return;			
		}
		// 如果没有登陆，将返回到登陆页面
		if (!checkRequestURIIntNotFilterList(request)
				&& session.getAttribute(sessionKey) == null) {			
		//	response.sendRedirect(request.getContextPath() + redirectURL);
			response.setContentType("text/html;charset=utf-8");
	            PrintWriter out = response.getWriter();          
	             out.println(MyTools.getAlertMessage(request.getContextPath(), "登录已失效请重新登录", 3));
			return;
		}
		
		if(session.getAttribute(sessionKey) != null){
			String userId = session.getAttribute(sessionKey).toString();
			//获取application对象
			ServletContext  application = session.getServletContext();
			String sessionId = (String) application.getAttribute(userId);
			//如果原来sessionId为空则返回
			if(sessionId==null){
				response.setContentType("text/html;charset=utf-8");
	            PrintWriter out = response.getWriter();
				 out.println(MyTools.getAlertMessage(request.getContextPath(), "请重新登录。", 3));
				return ;
			}
			String trueSessionId = session.getId();
			System.out.println("sessionID====="+sessionId+"现在的sessionID"+trueSessionId);
			if(sessionId.equals(trueSessionId)){
				
			}else{
				//说明有多个用户登录,去掉其session
				session.removeAttribute(sessionKey);
			//	response.sendRedirect(request.getContextPath() + redirectURL);
				response.setContentType("text/html;charset=utf-8");
	            PrintWriter out = response.getWriter();
	            out.println(MyTools.getAlertMessage(request.getContextPath(), "您的账户已在别处登录，请重新登录。", 3));
				return;
			}    
		}		
	
		chain.doFilter(request, response);
	}
	// 检查地址是否需要被过滤
	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {

		String uri = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		boolean b = noCheckURLList.contains(uri);
		System.out.println("URL:" + uri + "将不被过滤？" + b);
		return b;

	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
		// 找到不需要过滤的页面
		String notCheckURLListStr = filterConfig
				.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			String[] strUrl = notCheckURLListStr.split(",");
			for (int i = 0; i < strUrl.length; i++) {
				System.out.println(strUrl[i]);
				noCheckURLList.add(strUrl[i]);
			}
		}

	}
}
