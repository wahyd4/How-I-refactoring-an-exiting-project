package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.CategoryService;

/**
 * 通过页面编号获取类别信息
 */
@WebServlet("/GetCategorys")
public class GetCategorys extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCategorys() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		Integer page = Integer.parseInt(request.getParameter("page"));
		//获取类别服务对象
		CategoryService cs = new CategoryService(request);
		//如果page变量没有定义则默认获取第一页数据
		if(page ==null){
			page = 0;
		}
		returnXML =cs.getCategorysByPage(page);
		
		out.print(returnXML);
		System.out.println("你好"+returnXML);
	}

}
