package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.MessageService;
import com.messpush.model.bean.Message;

/**
 * 更新一条消息
 */
@WebServlet("/UpdateMessage")
public class UpdateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMessage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//将页面数据装入对象
		Message msg = new Message();
		msg.setMessage(request.getParameter("update_content"));
		//类别信息
		msg.setCategoryId(Integer.parseInt(request.getParameter("update_cate")));
		msg.setTitle(request.getParameter("update_title"));
		msg.setPreson(request.getParameter("update_person"));
		msg.setTelephone(request.getParameter("update_tel"));
		msg.setMessageId(Long.parseLong(request.getParameter("message_id")));
		msg.setTags("none");
		//从session中获取用户ID，因为在用户登录已经向session中存入了其用户ID
		Integer userId  = (Integer) request.getSession().getAttribute("userid");
		msg.setUserId(userId);  //目前暂时写死
		//实例化消息服务对象
		MessageService  ms = new MessageService(request);
	   String returnXML = ms.updateMessage(msg);
	   
	   System.out.print("xml"+returnXML);
	   out.print(returnXML);
	}

}
