package com.messpush.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * 初始化Log4jServlet
 */
@WebServlet("/Log4jInit")
public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Log4jInit() {
      super();
    }
    
    @Override
    public void init(){
    	  /*找到在web.xml中指定的log4j.properties文件并读取配置信息*/
        String prefix =  getServletContext().getRealPath("/");
        String file = getInitParameter("log4j");
        System.out.println("[info]:log4j started.................");
        if(file != null) {
        	
        	System.out.println("[info]Log4j  Path="+prefix+file);
            PropertyConfigurator.configure(prefix+file);
         }
    }


}
