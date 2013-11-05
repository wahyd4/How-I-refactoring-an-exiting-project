package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.SettingService;
import com.messpush.model.bean.Setting;

/**
 * 更新系统设置的相关信息
 */
@WebServlet("/UpdateSetting")
public class UpdateSetting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSetting() {
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
		Setting set = new Setting();
		//实例化一个设置服务对象
		SettingService ss=  new SettingService(request);
		//将更新信息填入到setting Bean中
		set.setId(Integer.parseInt(request.getParameter("setting_id")));
		set.setAppName(request.getParameter("app_name"));
		set.setLogo(request.getParameter("logo"));
		set.setIcp(request.getParameter("icp"));
		set.setCopyright(request.getParameter("copyright"));
		set.setSinglepageCount(Integer.parseInt(request.getParameter("singlepage_count")));
		set.setDefaultFreeDay(Integer.parseInt(request.getParameter("default_free_day")));
		set.setSearchCount(Integer.parseInt(request.getParameter("search_result")));
		set.setFirstPushToClientCount(Integer.parseInt(request.getParameter("first_push_to_client_count")));
		//提交更新
		returnXML= ss.updateSettingInfo(set);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
