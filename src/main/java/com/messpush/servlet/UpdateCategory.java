package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.CategoryService;
import com.messpush.model.bean.Category;

/**
 * 更新一个类别信息
 */
@WebServlet("/UpdateCategory")
public class UpdateCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategory() {
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
		//实例化类别服务对象
		CategoryService cs = new CategoryService(request);
		Category cate = new Category();
		cate.setCateId(Integer.parseInt(request.getParameter("update_cate_id")));
		cate.setCateName(request.getParameter("update_cate_name"));
		cate.setCateDesc(request.getParameter("update_cate_desc"));
		returnXML = cs.updateCategory(cate);
		out.println(returnXML);
		System.out.println(returnXML);
		
	}

}
