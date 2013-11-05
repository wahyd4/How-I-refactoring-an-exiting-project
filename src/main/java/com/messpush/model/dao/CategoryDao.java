package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.Category;

/**
 * 定义对于信息类别的接口
 * @author Junv
 *
 */
public interface CategoryDao {
	
	/**
	 * 新增并保存类别
	 * @param cate
	 * @return Category
	 */
	public Category saveCate(Category cate);
	
	/**
	 * 编辑并更新类别
	 * @param cate
	 * @return  Category
	 */
	public Category updateCate(Category cate);
	
/**
 * 通过hql获取多个类别信息
 * @param hql
 * @param start
 * @param count
 * @return  List<Category>
 */
	public List<Category> selectCates(String hql,Integer start ,Integer count);
	
	
	
	/**
	 * 删除某个类别。并通过返回的Category数否为空判断删除是否成功。
	 * @param cateId
	 * @return  Category
	 */
	public Category deleteCate(Integer  cateId);
	
	/**
	 * 判断某个类别是否存在，并返回对象<br>
	 * 如果确定某个类型存在也可作为获取单个对象的方法
	 * @param cateName
	 * @return Category
	 */
	public  Category getCateByName (String cateName);
	
	/**
	 * 通过类别ID获取一个类别的相关信息
	 * @param cateId
	 * @return  Category
	 */
	public Category getCateById(Integer cateId);
	
	/**
	 * 获取所有类别数量
	 * @return  int
	 */
	public int getCount();
     
	

	

}
