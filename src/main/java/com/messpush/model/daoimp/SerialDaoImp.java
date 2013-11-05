package com.messpush.model.daoimp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Serials;
import com.messpush.model.dao.SerialDao;

public class SerialDaoImp extends HibernateDaoSupport implements SerialDao{

	@Override
	public Serials saveSerial(Serials ser) {
	    this.getHibernateTemplate().save(ser);
		return  this.getSerialByNumber(ser.getSerialNumber());
	}

	@Override
	public Serials updateSerial(Serials ser) {
		this.getHibernateTemplate().update(ser);
		return  this.getSerialByNumber(ser.getSerialNumber());
	}

	@Override
	public List<Serials> selectSerials(String hql,Integer start,Integer count) {
		Session mySession   = getSession();
		 Query query = getSession().createQuery(hql);  
	        query.setFirstResult(start);  
	        query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Serials> serials = query.list();
		mySession.close();
		   this.getSessionFactory().close();
		return serials;
	}

	@Override
	public Serials deleteSerial(Long id) {
		  Serials ser = this.getSerialById(id);
		  this.getHibernateTemplate().delete(ser);
		  
		return  this.getSerialById(id);
	}

	@Override
	public Serials getSerialByNumber(String serialNumber) {
		
		Serials serial = null;
		@SuppressWarnings("unchecked")
		List<Serials> serials = this.getHibernateTemplate().find("from Serials where serialNumber = ?",serialNumber);
		if(serials.size()>0){
			serial = serials.get(0);
		}
		return serial;
	}

	@Override
	public int getCount() {
		String selectCountHql = "select count(*) from Serials";	
		Session mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	    mySession.close();
	    this.getSessionFactory().close();
		return  total;
	}

	@Override
	public Serials getSerialById(Long id) {
		Serials serial = null;
		@SuppressWarnings("unchecked")
		List<Serials> serials = this.getHibernateTemplate().find("from Serials where id = ?",id);
		if(serials.size()>0){
			serial = serials.get(0);
		}
		return serial;
	}

	@Override
	public List<Serials> searchSerials(String keyword, int start, int count) {
		Session mySession = getSession();
		String hql = "from Serials where concat(serialNumber) like :key order by id asc";
		//实现limit功能		
		 Query query = getSession().createQuery(hql);  
		 //设置模糊查询字符串 
		 query.setString("key", "%"+keyword+"%");
       query.setFirstResult(start);  
       query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Serials> sers = query.list();
		mySession.close();
		   this.getSessionFactory().close();
		return sers;
	}

	@Override
	public Serials setSerialIsUsed(Serials ser) {
		//将其使用状态设置为已使用
		ser.setSerialIsUsed("true");
		this.getHibernateTemplate().update(ser);
		//验证更新是否成功
		return this.getSerialById(ser.getId());
	}

}
