package com.messpush.model.daoimp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Message;
import com.messpush.model.dao.MessageDao;

public class MessageDaoImp extends HibernateDaoSupport implements  MessageDao {

	@Override
	public Message saveMessage(Message msg) {
		
		this.getHibernateTemplate().save(msg);
		//通过验证新保存的对象是否存在来判断是否保存成功
		return this.getMessageByTitle(msg.getTitle());
	}

	@Override
	public Message updateMessage(Message msg) {
		this.getHibernateTemplate().update(msg);
		
		return this.getMessageById(msg.getMessageId());
	}

	@Override
	public List<Message> selectMessages(String hql,Integer start,Integer count) {
		//实现limit功能
		Session mySession = getSession();
		 Query query = mySession.createQuery(hql);  	
        query.setFirstResult(start);  
        query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Message> msgs = query.list();
		 mySession.close();
		   this.getSessionFactory().close();
		return msgs;
	}

	@Override
	public Message deleteMessage(Long messageId) {
		//通过messageId得到该Message对象
		Message msg = this.getMessageById(messageId);
		this.getHibernateTemplate().delete(msg);
		
		return this.getMessageById(messageId);
	}

	@Override
	public Message getMessageByTitle(String title) {
		
		//通过标题得到该Message对象
		Message msg = null ;
		@SuppressWarnings("unchecked")
		List<Message> msgs=  this.getHibernateTemplate().find("from Message where title=?",title);
		if(msgs.size()>0){
			msg = msgs.get(0);
		}
		
		return msg;
	}
	
	@Override
	public Message getMessageById(Long messageId) {
		//通过标题得到该Message对象
		Message msg = null ;
		@SuppressWarnings("unchecked")
		List<Message> msgs=  this.getHibernateTemplate().find("from Message where messageId=?",messageId);
		if(msgs.size()>0){
			msg = msgs.get(0);
		}
		
		return msg;
	}
    
	@Override
	public int getCount() {
		String selectCountHql = "select count(*) from Message";	
		//获取count操作
		Session  mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	   mySession.close();
	   this.getSessionFactory().close();
		return  total;
	}

	@Override
	public List<Message> searchMessage(String keyword, Integer start,
			Integer count) {
		Session mySession = getSession();
		String hql = "from Message where concat(person,telephone,title,message) like :key order by time desc";
		//实现limit功能		
		 Query query = mySession.createQuery(hql);  
		
		 //设置模糊查询字符串 
		 query.setString("key", "%"+keyword+"%");
       query.setFirstResult(start);  
       query.setMaxResults(count);  
       
		@SuppressWarnings("unchecked")
		List<Message> msgs = query.list();
	    mySession.close();
	    this.getSessionFactory().close();
		return msgs;
	}
     
	@Override
	public int  getMessageCountByCateId(Integer cateId) throws HibernateException {
		int count = 0;
		String hql = "select count(*) from Message where cate_id = :cateid";
		//获取某个类别下信息的数量
		Session  mySession = getSession();
		Query query = mySession.createQuery(hql);
		//设置属性
		query.setInteger("cateid", cateId);
		 count = ((Long) query.uniqueResult()).intValue();
		 //关闭session,如果不关闭session将只执行8次。
		mySession.close();
		   this.getSessionFactory().close();
		return count;
	}

	
	@Override
	public List<Message> selectMessagesByASC(int count) {
		Session mySession = getSession();
		String sql = "select * from ( select * from msg order by time desc limit  :key ) as msg order by msg_id asc";
		Query query = mySession.createSQLQuery(sql);
         query.setInteger("key",count);
	List<Message> msgs = new ArrayList<Message>();
	    @SuppressWarnings("unchecked")
		List<Object> temp =  query.list();
	   Iterator<Object>  it = temp.iterator();
	   //这里需要自己将返回结果构造成为Message
	   while(it.hasNext()){
		   Object[] obj = (Object[]) it.next();
		   Message msg = new Message();
				msg.setMessageId(Long.parseLong(obj[0].toString()));
				msg.setTitle(obj[1].toString());
				msg.setMessage(obj[2].toString());
				msg.setCategoryId(Integer.parseInt(obj[3].toString()));
			//	msg.setTags(obj[4].toString());
				msg.setTags("null");
				msg.setUserId(Integer.parseInt(obj[5].toString()));
				msg.setPreson(obj[6].toString());
				msg.setTelephone(obj[7].toString());
				msg.setTime(Timestamp.valueOf(obj[8].toString()));
				msgs.add(msg);
	   }
	   
	   mySession.close();
	   this.getSessionFactory().close();
		return msgs;
	}

	@Override
	public List<Message> getMessagesBehindMsgId(Long MessageId) {
		
		String hql = "from Message where messageId >? order by messageId desc";
		@SuppressWarnings("unchecked")
		List<Message> msgs = this.getHibernateTemplate().find(hql, MessageId);
	   return msgs;
	}

	@Override
	public List<Message> getMessagesBySomeone(Integer userId, Integer start,
			Integer count) {
		//实现limit功能
				Session mySession = getSession();
				 Query query = mySession.createQuery("from Message where userId = :key order by messageId desc");  	
			query.setInteger("key", userId);
		        query.setFirstResult(start);  
		        query.setMaxResults(count);  
				@SuppressWarnings("unchecked")
				List<Message> msgs = query.list();
				 mySession.close();
				   this.getSessionFactory().close();
				return msgs;
	}

	@Override
	public List<Message> getMessageBySomeoneAndCategory(Integer userId, Integer cateId,
			Integer start, Integer count) {
		//实现limit功能
		Session mySession = getSession();
		 Query query = mySession.createQuery("from Message where userId = :usrId  and categoryId= :cateId order by messageId desc");  	
	    query.setInteger("usrId", userId);
	    query.setInteger("cateId", cateId);
        query.setFirstResult(start);  
        query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Message> msgs = query.list();
		 mySession.close();
		   this.getSessionFactory().close();
		return msgs;	}


}
