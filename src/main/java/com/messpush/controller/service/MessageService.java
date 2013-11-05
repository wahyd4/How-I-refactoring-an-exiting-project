package com.messpush.controller.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Message;
import com.messpush.model.dao.MessageDao;

/**
 * 对信息相关操作的高级封装，实现xml的返回
 * 
 * @author Junv
 * 
 */
public class MessageService {
	private HttpServletRequest request = null;
	
	public MessageService(HttpServletRequest request){
		this.request = request;
	}
	/**
	 * 保存一条消息的具体实现
	 * 
	 * @param msg
	 * @return String
	 */
	public String saveMessage(Message msg) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 实例化category服务类，获取所有的类别信息,
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		Message retObj = messageDao.saveMessage(msg);
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<messages size='1'>");
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + retObj.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(retObj.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + retObj.getMessage() + "</message>");
			sb.append("<title>" + retObj.getTitle() + "</title>");
			sb.append("<tags>" + retObj.getTags() + "</tags>");
			sb.append("<person>" + retObj.getPreson() + "</person>");
			sb.append("<tel>" + retObj.getTelephone() + "</tel>");
			sb.append("<userid>" + retObj.getUserId() + "</userid>");
			sb.append("<messageid>" + retObj.getMessageId() + "</messageid>");
			sb.append("<time>" + retObj.getTime() + "</time>");
			sb.append("</messageitem>");
			sb.append("</messages>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 更新一条信息的实现,<br>
	 * 重要的是需要保存messageId
	 * 
	 * @param msg
	 * @return String
	 */
	public String updateMessage(Message msg) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 实例化工厂类，并创建实例
				HibernateDaoFactory factory = new HibernateDaoFactory(request);
				MessageDao messageDao = factory.getMessageDao();
		// 变更值
		// 需要注意的是时间和用户应该是不能改变的。
		Message temp = messageDao.getMessageById(msg.getMessageId());
		temp.setCategoryId(msg.getCategoryId());
		temp.setMessage(msg.getMessage());
		temp.setPreson(msg.getPreson());
		temp.setTags(msg.getTags());
		temp.setTelephone(msg.getTelephone());
		// temp.setTime(msg.getTime());
		temp.setTitle(msg.getTitle());
		// temp.setUserId(msg.getUserId());
		// 提交保存
		Message retObj = messageDao.updateMessage(temp);
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过输入需要第几个页面，获取该页面的所有信息
	 * 
	 * @param page
	 * @return String
	 */
	public String getMessagesByPage(int page) {
		// 获取一页显示的数量
		int pageCount = Constant.SINGLEPAGECOUNT;

		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();
      //按照消息ID进行逆排序
		String hql = "from Message order by messageId desc";
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + messageDao.getCount()
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Message> msgs = messageDao.selectMessages(hql, (page * pageCount - 1),
				pageCount);
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 此为提供给客户端的方法，返回若干条数据，<br>
	 * 数据条数已经在数据库定义好<br>
	 * x数据采用按照ID升序
	 * @param page
	 * @return String
	 */
	public String getMessagesByPageASC() {
     
		int firstCount = Constant.FIRSTPUSHCOUNTTOCLIENT;
		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + messageDao.getCount()
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Message> msgs = messageDao.selectMessagesByASC(firstCount);
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}
     /**
      * 通过messageid获取某个messgaeid之后的信息，并以xml格式返回
      * @param messageId
      * @return  String构建好的字符串
      */
	public String getMessagesBehindMsgId(Long messageId) {
		
		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + messageDao.getCount()
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Message> msgs = messageDao.getMessagesBehindMsgId(messageId);
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 通过messageid删除一条信息
	 * 
	 * @param messageId
	 * @return String
	 */
	public String deleteMessage(Long messageId) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
				HibernateDaoFactory factory = new HibernateDaoFactory(request);
				MessageDao messageDao = factory.getMessageDao();
		Message retObj = messageDao.deleteMessage(messageId);
		if (retObj == null) {
			sb.append("<value>true</value>");
		} else {
			sb.append("<value>false</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过输入关键字对数据库中的信息进行搜索<br>
	 * 会同时查询 person、title、message、tel<br>
	 * 
	 * @param keyword
	 *            需要搜索的关键字
	 * @return xml字符串
	 */
	public String getMessageBySearch(String keyword) {
		// 获取搜索时一页显示的数量
		int pageCount = Constant.SINGLESERCHCOUNT;
		int page = 0; // 默认设置只返回一个页面
		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		List<Message> msgs = messageDao.searchMessage(keyword,
				(page * pageCount - 1), pageCount);
		sb.append("<totalcount><value>" + msgs.size()+ "</value></totalcount>");
	    sb.append("<singlepage><value>"+Constant.SINGLESERCHCOUNT+"</value></singlepage>");
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}
	/**
	 * 通过类别ID和页面ID获取信息
	 * @param page 页面编号，从0 开始
	 * @param cateId 类别ID
	 * @return  String xml
	 */
	public String getMessagesByCate(int page,int cateId){
		
		int pageCount = Constant.SINGLEPAGECOUNT;
		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();
      //按照消息ID进行逆排序
		//这里可能导致SQL注入
		String hql = "from Message where cate_id = "+cateId+" order by messageId desc";
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + messageDao.getMessageCountByCateId(cateId)
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Message> msgs = messageDao.selectMessages(hql, (page * pageCount - 1),
				pageCount);
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 获取某个用户发布的信息
	 * @param userId 用户ID
	 * @param page 页面编号
	 * @return  String xml
	 */
	public String getMessagesBySomeone(Integer userId,int  page){
		// 获取一页显示的数量
				int pageCount = Constant.SINGLEPAGECOUNT;

				// 获取所有的类别信息
				CategoryService cs = new CategoryService(request);
				Map<Integer, String> cates = cs.getAllCategorys();
				StringBuilder sb = new StringBuilder();
				sb.append("<result>");
				// 获取消息的中条数
				// 实例化工厂类，并创建实例
				HibernateDaoFactory factory = new HibernateDaoFactory(request);
				MessageDao messageDao = factory.getMessageDao();
				sb.append("<totalcount><value>" + messageDao.getCount()
						+ "</value></totalcount>");
		        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
				List<Message> msgs = messageDao.getMessagesBySomeone(userId, (page * pageCount - 1),
						pageCount);
				sb.append("<messages size='" + msgs.size() + "'>");
				Iterator<Message> it = msgs.iterator();
				while (it.hasNext()) {
					Message msg = it.next();
					sb.append("<messageitem>");
					sb.append("<cate>");
					sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
					sb.append("<catename>" + cates.get(msg.getCategoryId())
							+ "</catename>");
					sb.append("</cate>");
					sb.append("<message>" + msg.getMessage() + "</message>");
					sb.append("<title>" + msg.getTitle() + "</title>");
					sb.append("<tags>" + msg.getTags() + "</tags>");
					sb.append("<person>" + msg.getPreson() + "</person>");
					sb.append("<tel>" + msg.getTelephone() + "</tel>");
					sb.append("<userid>" + msg.getUserId() + "</userid>");
					sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
					sb.append("<time>" + msg.getTime() + "</time>");
					sb.append("</messageitem>");
				}
				sb.append("</messages>");
				sb.append("</result>");
				return sb.toString();
	}
	
	/**
	 * 
	 * @param userId 用户ID
	 * @param categoryId  类别ID
	 * @param page 页面编号
	 * @return  String xml
	 */
	public String getMessagesBySomeoneAndCategory(Integer userId,Integer categoryId,int page){
		// 获取一页显示的数量
		int pageCount = Constant.SINGLEPAGECOUNT;
		// 获取所有的类别信息
		CategoryService cs = new CategoryService(request);
		Map<Integer, String> cates = cs.getAllCategorys();
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 获取消息的中条数
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + messageDao.getCount()
				+ "</value></totalcount>");
        sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Message> msgs = messageDao.getMessageBySomeoneAndCategory(userId, categoryId,(page * pageCount - 1),
				pageCount);
		sb.append("<messages size='" + msgs.size() + "'>");
		Iterator<Message> it = msgs.iterator();
		while (it.hasNext()) {
			Message msg = it.next();
			sb.append("<messageitem>");
			sb.append("<cate>");
			sb.append("<cateid>" + msg.getCategoryId() + "</cateid>");
			sb.append("<catename>" + cates.get(msg.getCategoryId())
					+ "</catename>");
			sb.append("</cate>");
			sb.append("<message>" + msg.getMessage() + "</message>");
			sb.append("<title>" + msg.getTitle() + "</title>");
			sb.append("<tags>" + msg.getTags() + "</tags>");
			sb.append("<person>" + msg.getPreson() + "</person>");
			sb.append("<tel>" + msg.getTelephone() + "</tel>");
			sb.append("<userid>" + msg.getUserId() + "</userid>");
			sb.append("<messageid>" + msg.getMessageId() + "</messageid>");
			sb.append("<time>" + msg.getTime() + "</time>");
			sb.append("</messageitem>");
		}
		sb.append("</messages>");
		sb.append("</result>");
		return sb.toString();
	}

}
