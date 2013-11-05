package com.messpush.model.daoimp;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.User;
import com.messpush.model.dao.UserDao;

public class UserDaoImp  extends HibernateDaoSupport implements UserDao{

	@Override
	public User saveUser(User user) {
		
		this.getHibernateTemplate().saveOrUpdate(user);
		return this.getUserByName(user.getUsername());
	}

	@Override
	public User updateUser(User user) {
		/*//由于更改用户没有更改用户名和密码，因为我们需要自己将用户名和密码加入其中
		User temp = this.getUserById(user.getUserId());
		user.setUsername(temp.getUsername());
		user.setPassword(temp.getPassword());*/
		//更新用户
		this.getHibernateTemplate().update(user);
		
		return this.getUserById(user.getUserId());
	}

	
	@Override
	public List<User> selectUsers(String hql,Integer start,Integer count) {
         
		 Session mySession = getSession();
		 Query query = mySession.createQuery(hql);  
         query.setFirstResult(start);  
         query.setMaxResults(count);  
         @SuppressWarnings("unchecked")
     	List<User> users  = query.list();
         mySession.close();
         this.getSessionFactory().close();
		return users;
	}

	@Override
	public User deleteUser(Integer userId) {
		User user = this.getUserById(userId);
		this.getHibernateTemplate().delete(user);
		//检查是否删除成功
		return this.getUserById(userId);
	}

	@Override
	public User getUserByName(String username) {
		
		User user = null;
		@SuppressWarnings("unchecked")
		List<User> users = this.getHibernateTemplate().find("from User where username=?",username);
		if(users.size()>0){
			user = users.get(0);
		}
		return user;
	}

	@Override
	public int getCount( ) {
		
		String selectCountHql = "select count(*) from User";	
		Session mySession = getSession();
		int total = ((Long)mySession.createQuery(selectCountHql).uniqueResult()).intValue();
	    mySession.close();
	    this.getSessionFactory().close();
		return  total;
	}

	@Override
	public User getUserById(Integer userId) {
		User user = null;
		@SuppressWarnings("unchecked")
		List<User> users = this.getHibernateTemplate().find("from User where userId=?",userId);
		if(users.size()>0){
			user = users.get(0);
		}
		return user;
	}

	@Override
	public User changePassword(Integer userId,String newPassword) {
		//获取被改密码的用户对象
		 User user = this.getUserById(userId);
		 user .setPassword(newPassword);
		 //执行更新数据库
		 this.getHibernateTemplate().update(user);
		 //检验修改是否成功
		return this.getUserById(userId);
	}

	
}
