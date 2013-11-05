package com.messpush.controller.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Category;
import com.messpush.model.dao.CategoryDao;
import com.messpush.model.dao.MessageDao;

/**
 * 一个类别相关服务的具体实现<br>
 * 返回xml
 * 
 * @author Junv
 * 
 */
public class CategoryService {
	private HttpServletRequest request= null;

	// 实例化request对象
	public CategoryService(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 保存一个类别,返回xml
	 * 
	 * @param cate
	 * @return String
	 */
	public String saveCategory(Category cate) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 实例化并获取CategoryDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();
		Category retObj = cateDao.saveCate(cate);
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<categorys size='1'>");
			sb.append("<categoryitem>");
			sb.append("<catename>" + retObj.getCateName() + "</catename>");
			sb.append("<catedesc>" + retObj.getCateDesc() + "</catedesc>");
			sb.append("<cateid>" + retObj.getCateId() + "</cateid>");
			sb.append("</categoryitem>");
			sb.append("</categorys>");
		}
		sb.append("</result>");
		return sb.toString();

	}

	/**
	 * 保存一个；类别信息并返回xml
	 * 
	 * @param cate
	 * @return String
	 */
	public String updateCategory(Category cate) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();
		MessageDao messageDao = factory.getMessageDao();
		// 设置更改,并更新
		Category temp = cateDao.getCateById(cate.getCateId());
		temp.setCateName(cate.getCateName());
		temp.setCateDesc(cate.getCateDesc());
		Category retObj = cateDao.updateCate(temp);
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<categorys size='1'>");
			sb.append("<categoryitem>");
			sb.append("<catename>" + retObj.getCateName() + "</catename>");
			sb.append("<catedesc>" + retObj.getCateDesc() + "</catedesc>");
			sb.append("<cateid>" + retObj.getCateId() + "</cateid>");
			sb.append("<count>"
					+ messageDao.getMessageCountByCateId(retObj.getCateId())
					+ "</count>");
			sb.append("</categoryitem>");
			sb.append("</categorys>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过页面编号获取某一个页面的所有页面信息<br>
	 * 页面编号从0开始计数
	 * 
	 * @param page
	 * @return String
	 */
	public String getCategorysByPage(int page) {
		// 获取一个页面所有的信息
		int pageCount = Constant.SINGLEPAGECOUNT;
		String hql = "from Category";
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		// 实例化工厂类，并创建实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();
		MessageDao messageDao = factory.getMessageDao();
		sb.append("<totalcount><value>" + cateDao.getCount()
				+ "</value></totalcount>");
		sb.append("<singlepage><value>" + Constant.SINGLEPAGECOUNT
				+ "</value></singlepage>");
		List<Category> cates = cateDao.selectCates(hql, (page * pageCount - 1),
				pageCount);
		sb.append("<categorys size='" + cates.size() + "'>");
		Iterator<Category> it = cates.iterator();
		while (it.hasNext()) {

			Category cate = it.next();
			int cateId = cate.getCateId();
			sb.append("<categoryitem>");
			sb.append("<catename>" + cate.getCateName() + "</catename>");
			sb.append("<catedesc>" + cate.getCateDesc() + "</catedesc>");
			sb.append("<cateid>" + cateId + "</cateid>");
			// 获取某个类别下消息的数量
			sb.append("<count>" + messageDao.getMessageCountByCateId(cateId)
					+ "</count>");
			sb.append("</categoryitem>");

		}
		sb.append("</categorys>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 获取所有类别信息，并以XML形式返回
	 * 
	 * @return String
	 */
	public String getAllCategorysXML() {
		String hql = "from Category";
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();

		sb.append("<totalcount><value>" + cateDao.getCount()
				+ "</value></totalcount>");
		List<Category> cates = cateDao.selectCates(hql, 0, 500); // 小bug。估计所有类别不会超过500个把
		sb.append("<categorys size='" + cates.size() + "'>");
		Iterator<Category> it = cates.iterator();
		while (it.hasNext()) {
			Category cate = it.next();
			sb.append("<categoryitem>");
			sb.append("<catename>" + cate.getCateName() + "</catename>");
			sb.append("<catedesc>" + cate.getCateDesc() + "</catedesc>");
			sb.append("<cateid>" + cate.getCateId() + "</cateid>");
			sb.append("</categoryitem>");

		}
		sb.append("</categorys>");
		sb.append("</result>");
		return sb.toString();

	}

	public String getCateogrysForFlex() {
		String hql = "from Category";
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' ?><result>");
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();

		List<Category> cates = cateDao.selectCates(hql, 0, 500); // 小bug。估计所有类别不会超过500个把

		Iterator<Category> it = cates.iterator();
		while (it.hasNext()) {
			Category cate = it.next();
			sb.append("<categoryitem>");
			sb.append("<catename>" + cate.getCateName() + "</catename>");
			sb.append("<catedesc>" + cate.getCateDesc() + "</catedesc>");
			sb.append("<cateid>" + cate.getCateId() + "</cateid>");
			sb.append("</categoryitem>");

		}

		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过cateID删除一个类别
	 * 
	 * @param cateId
	 * @return String
	 */
	public String deleteCategory(Integer cateId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();
		Category retObj = cateDao.deleteCate(cateId);
		if (retObj == null) {
			sb.append("<value>true</value>");
		} else {
			sb.append("<value>false</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 获取所有的类别信息，并使用Map<Integer,String >的形式返回
	 * 
	 * @return Map<Integer,String>
	 */
	public Map<Integer, String> getAllCategorys() {

		Map<Integer, String> cates = new HashMap<Integer, String>();
		// 实例化工厂类，并创建对象
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		CategoryDao cateDao = factory.getCategoryDao();
		// 这里使用获取100个类别是设计上的问题。目的是为了获取所有类别
		List<Category> listCates = cateDao.selectCates("from Category", 0, 100);

		Iterator<Category> it = listCates.iterator();
		while (it.hasNext()) {
			Category temp = it.next();
			cates.put(temp.getCateId(), temp.getCateName());
		}
		return cates;

	}

}
