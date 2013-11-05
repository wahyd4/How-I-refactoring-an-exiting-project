package com.messpush.model.daoimp;

import java.sql.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.messpush.model.bean.Log;
import com.messpush.model.dao.LogDao;

public class LogDaoImp  extends HibernateDaoSupport implements LogDao{

	@Override
	public List<Log> selectLogsByPage(String hql, Integer start,
			Integer count) {
		Session mySession = getSession();
		 Query query = mySession.createQuery(hql);  	
       query.setFirstResult(start);  
       query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Log> logs = query.list();
		 mySession.close();
		   this.getSessionFactory().close();
		return logs;
	}

	@Override
	public boolean deleteLogsByDate(Date fromDate, Date toDate) {
		String hql ="delete from Log where time>= :from and time<= :to";
		Session mySession = getSession();
		Query query = mySession.createQuery(hql);
		query.setString("from", "%"+fromDate+"%");
		query.setString("to", "%"+toDate+"%");
		query.executeUpdate();
		return false;
	}

	@Override
	public Log checkLogExist(Long logId) {
      @SuppressWarnings("unchecked")
	  List<Log> logs =    this.getHibernateTemplate().find("from Log where logId =?",logId);
      //如果list中有东西返回第一个对象
      if(logs.size()>0){
    	   return logs.get(0);
      }
		return null;
	}

	@Override
	public int getCount() {
		String selectCountHql = "select count(*) from Log";	
		Session mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	    mySession.close();
	    //关闭sessionFactory
	    this.getSessionFactory().close();
		return  total;
		
	}

	

}
