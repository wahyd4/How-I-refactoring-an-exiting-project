package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.Message;

/**
 * 提供消息相关操作的接口
 * @author Junv
 *
 */
public interface MessageDao {
	
	/**
	 * 新建并保存消息
	 * @param msg
	 * @return  Message
	 */
	public Message saveMessage(Message msg);
	
	/**
	 * 更新消息
	 * @param msg
	 * @return  Message
	 */
	public Message updateMessage(Message msg);
	
   
	/**
	 * 通过hql查询多条信息
	 * @param hql
	 * @param start
	 * @param count
	 * @return   List<Message>
	 */
	public List<Message> selectMessages(String hql,Integer start,Integer count);
	
	/**
	 * 通过分页获取数据，采用时间升序<br>
	 * 需要指明获取最近多少条数据
	 * @return  List<Message>
	 */
	public List<Message> selectMessagesByASC(int count );
	
	/**
	 * 删除某条消息,如果返回对象为空则表示删除成功！
	 * @param messageId
	 * @return Message
	 */
	public Message deleteMessage(Long  messageId);
	
	/**
	 *通过信息的标题查询返回对象某条消息是否存在<br>
	 *或者通过标题获取一条信息
	 * @param title
	 * @return Message
	 */
	public Message getMessageByTitle(String title);
	
	/**
	 * 获取消息表中所有消息的条数
	 * @return int
	 */
	public int getCount();
	
    /**
     *通过messageID来获取一个message对象
     * @param messageId
     * @return
     */
	public Message getMessageById(Long messageId);
	
	/**
	 * 
	 * @param keyword  搜索的关键字
	 * @param start     开始搜索的页面编号
	 * @param count   一次需要搜索的条目数量
	 * @return   List<Message>  一个message集合
	 */
	public List<Message>  searchMessage(String keyword,Integer start,Integer count);
	
	   /**
	    * 通过类别编号获取某个类别下的消息数量
	    * @param cateId
	    * @return  int类型信息数量
	    */
		public int getMessageCountByCateId(Integer cateId);
		
		/**
		 * 获取某个messageid后面所有的信息。默认按照时间升序
		 * @param MessageId
		 * @return  List<Message>
		 */
		public List<Message> getMessagesBehindMsgId(Long MessageId);
		
		/**
		 * 用户某个特定用户发布的信息
		 * @param userid 用户ID
		 * @param start  开始的信息条数
		 * @param count 需要返回的信息数量
		 * @return List<Message>
		 */
		public List<Message> getMessagesBySomeone(Integer userid,Integer start,Integer count);
		
		/**
		 * 
		 * 同时通过用户ID和类别ID获取数据，也包含分页等信息
		 * @param userId 用户ID
		 * @param cateId  类别ID
		 * @param start   开始的位置
		 * @param count 返回信息数量
		 * @return List<Message>
		 */
		public List<Message> getMessageBySomeoneAndCategory(Integer userId,Integer cateId,Integer start,Integer count);
		
}
