package com.messpush.model.daoimp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Category;
import com.messpush.model.dao.CategoryDao;

public class CategoryDaoImp extends HibernateDaoSupport implements CategoryDao {

	@Override
	public Category saveCate(Category cate) {

		this.getHibernateTemplate().save(cate);
		return this.getCateByName(cate.getCateName());
		
	  
	}

	@Override
	public Category updateCate(Category cate) {

		this.getHibernateTemplate().update(cate);
		return this.getCateByName(cate.getCateName());
	}

	@Override
	public List<Category> selectCates(String hql,Integer start,Integer count) {
		Session mySession = getSession();
		 Query query = mySession.createQuery(hql);  
	        query.setFirstResult(start);  
	        query.setMaxResults(count);  
		@SuppressWarnings("unchecked")
		List<Category> cates = query.list();
		mySession.close();
		//888888888888888888888888888888
		this.getSessionFactory().close();
		return cates;
	}

	@Override
	public Category deleteCate(Integer  cateId)throws DataAccessException  {
		// 获取类别信息
		Category cate = this.getCateById(cateId);
   
    	   this.getHibernateTemplate().delete(cate);
    

		return this.getCateById(cateId);
	}

	@Override
	public Category getCateByName(String cateName) {

		Category cate = null;
		@SuppressWarnings("unchecked")
		List<Category> cates = this.getHibernateTemplate().find(
				"from  Category where cateName=?",cateName);
		// 如果获取到类别信息
		if (cates.size() > 0) {
			cate = cates.get(0); // 取第一个，通常也是唯一一个
		}
		return cate;
	}



	@Override
	public Category getCateById(Integer cateId) {
		Category cate = null;
		@SuppressWarnings("unchecked")
		List<Category> cates = this.getHibernateTemplate().find("from  Category where cateId =? ",cateId);
		// 如果获取到类别信息
		if (cates.size() > 0) {
			cate = cates.get(0); // 取第一个，通常也是唯一一个
		}
		return cate;
	}

	@Override
	public int getCount() {
		String selectCountHql = "select count(*) from Category";	
		Session mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	    mySession.close();
	    this.getSessionFactory().close();
		return  total;
	}



}
