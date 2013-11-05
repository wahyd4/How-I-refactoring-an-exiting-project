package com.messpush.controller.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Message;
import com.messpush.model.bean.TempMessage;
import com.messpush.model.dao.MessageDao;
import com.messpush.model.dao.TempMessageDao;

/**
 * 暂时信息的相关服务类
 * 
 * @author Junv
 * 
 */
public class TempMessageService {
	HttpServletRequest request = null;
   
	/**
	 * 实例化服务类，需要传入HttpServletRequest
	 * @param request HttpServletRequest
	 */
	public TempMessageService(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 保存一条暂时信息
	 * 
	 * @param msg
	 *            暂时信息对象
	 * @return String xml
	 */
	public String saveTempMessage(TempMessage msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 得到工厂
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		TempMessageDao tempMessageDao = factory.getTempMessageDao();
		TempMessage returnObj = tempMessageDao.saveTempMessage(msg);
		// 如果返回对象为空，则返回失败
		if (returnObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<tempmsgs size='1'>");
			sb.append("<tempmsgitem>");
			sb.append("<id>" + returnObj.getMessageId() + "</id>");
			sb.append("<title>" + returnObj.getTitle() + "</title>");
			sb.append("<msg>" + returnObj.getMessage() + "</msg>");
			sb.append("<person>" + returnObj.getPerson() + "</person>");
			sb.append("<tel>" + returnObj.getTel() + "</tel>");
			sb.append("<time>" + returnObj.getTime() + "</time>");
			sb.append("<serial>" + returnObj.getSerialNumber() + "</serial>");
			sb.append("</tempmsgitem>");
			sb.append("</tempmsgs>");
		}
		sb.append("</result>");
		return sb.toString();
	}

    /**
     * 通过页面编号获取该页面的暂时信息
     *
	 * @param page
	 *            页面编号
	 * @return String
	 */
	public String getTempMessagesByPage(Integer page) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//获取单页数据
			Integer pageCount = Constant.SINGLEPAGECOUNT;
		// 得到工厂
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		TempMessageDao tempMessageDao = factory.getTempMessageDao();
		sb.append("<totalcount><value>" + tempMessageDao.getCount()
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<TempMessage> msgs = tempMessageDao.getTempMessagesByPage( (page * pageCount - 1), pageCount);
		sb.append("<tempmsgs size='"+msgs.size()+"'>");
		//遍历信息
		Iterator<TempMessage> it = msgs.iterator();
		while(it.hasNext()){
			//得到对象
			TempMessage temp = it.next();
			sb.append("<tempmsgitem>");
			sb.append("<id>" + temp.getMessageId() + "</id>");
			sb.append("<title>" + temp.getTitle() + "</title>");
			sb.append("<msg>" + temp.getMessage() + "</msg>");
			sb.append("<person>" + temp.getPerson() + "</person>");
			sb.append("<tel>" + temp.getTel() + "</tel>");
			sb.append("<time>" + temp.getTime() + "</time>");
			sb.append("<serial>" + temp.getSerialNumber() + "</serial>");
			sb.append("</tempmsgitem>");
		}
		sb.append("</tempmsgs>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 删除一条暂时信息
	 * 
	 * @param messageId
	 *            信息ID
	 * @return String xml
	 */
	public String deleteTempMessage(Integer messageId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 得到工厂
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		TempMessageDao tempMessageDao = factory.getTempMessageDao();
		TempMessage returnObj = tempMessageDao.deleteTempMessageById(messageId);
		//如果返回对象为空删除成功
		if(returnObj==null){
			sb.append("<value>true</value>");
		}else{
			sb.append("<value>false</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 审核通过一条暂时信息<br>
	 * 会在暂时信息中删除当前这条信息<br>
	 * 同时会在信息中增加一条信息
	 * 
	 * @param messageId
	 *            信息ID
	 */
	public String doCheckTempMessage(Integer messageId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 得到工厂
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		TempMessageDao tempMessageDao = factory.getTempMessageDao();
		MessageDao messageDao = factory.getMessageDao();
	   //通过信息id获取该信息
		TempMessage target = tempMessageDao.getTempMessageById(messageId);
		//将此条信息添加到正式信息中
	    Message message = new Message();
	   // 将信息填入其中
	    message.setTitle(target.getTitle());
	    message.setMessage(target.getMessage());
	    //时间为当前时间
	  //  message.setTime(new Timestamp(System.currentTimeMillis()));
	    message.setPreson(target.getPerson());
	    message.setTelephone(target.getTel());
	    message.setTags(target.getSerialNumber().toString());
	    //从session中获取用户ID
	    HttpSession session = request.getSession();
	    Integer userId = (Integer) session.getAttribute("userid");
	    //设置用户ID
	    message.setUserId(userId);
	    //类别ID我们会将其存入系统自定义的某个类别
	    message.setCategoryId(22); //设置写死设置为类别ID为22
	    //保存信息
	   Message returnMsg =  messageDao.saveMessage(message);
	    //删除暂时信息中的信息
	   TempMessage returnTempMsg = tempMessageDao.deleteTempMessageById(messageId);
	   //验证操作是否成功
	   if(returnMsg!=null&&returnTempMsg==null){
		   sb.append("<value>true</value>");
	   }else{
		   sb.append("<value>false</value>");
	   }
		sb.append("</result>");
		return sb.toString();
	}

}
