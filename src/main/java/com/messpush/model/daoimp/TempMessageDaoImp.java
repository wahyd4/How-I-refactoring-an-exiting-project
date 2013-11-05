package com.messpush.model.daoimp;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Message;
import com.messpush.model.bean.TempMessage;
import com.messpush.model.dao.TempMessageDao;

public class TempMessageDaoImp extends HibernateDaoSupport implements TempMessageDao{

	@Override
	public TempMessage getTempMessageById(Integer messageId) {
		@SuppressWarnings("unchecked")
		List<TempMessage> msgs = this.getHibernateTemplate().find("from TempMessage where messageId = ?",messageId);
		if(msgs.size()>0){
			return msgs.get(0);
		}
		return null;
	}

	@Override
	public TempMessage deleteTempMessageById(Integer messageId) {
		TempMessage target = this.getTempMessageById(messageId);
		this.getHibernateTemplate().delete(target);
		return this.getTempMessageById(messageId);
	}

	@Override
	public TempMessage saveTempMessage(TempMessage tempMess) {
		this.getHibernateTemplate().save(tempMess);
		return this.getTempMessageByTime(tempMess.getTime());
	}

	@Override
	public TempMessage getTempMessageByTime(Timestamp time) {
		@SuppressWarnings("unchecked")
		List<TempMessage> msgs = this.getHibernateTemplate().find("from TempMessage where time = ?",time);
		if(msgs.size()>0){
			return msgs.get(0);
		}
		return null;
	}

	@Override
	public List<TempMessage> getTempMessagesByPage(int start, int count) {
		//实现limit功能
				Session mySession = getSession();
				 Query query = mySession.createQuery("from  TempMessage  order by messageId desc");  	
		        query.setFirstResult(start);  
		        query.setMaxResults(count);  
				@SuppressWarnings("unchecked")
				List<TempMessage> msgs = query.list();
				 mySession.close();
				   this.getSessionFactory().close();
				return msgs;
	}

	@Override
	public Integer getCount() {
		String selectCountHql = "select count(*) from TempMessage";	
		//获取count操作
		Session  mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	   mySession.close();
	   this.getSessionFactory().close();
		return  total;
	}


}
