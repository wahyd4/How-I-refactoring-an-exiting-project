package com.messpush.model.dao;

import java.sql.Date;
import java.util.List;

import com.messpush.model.bean.Log;

/**
 * 定义日志信息的相关接口
 * @author Junv
 *
 */
public interface LogDao {
		
    /**
     * 通过hql和页面相关信息获取日志信息
     * @param hql
     * @param start
     * @param count
     * @return List<Log>
     */
	public List<Log> selectLogsByPage(String hql,Integer start,Integer count);
	/**
	 * 通过时间返回删除日志信息
	 * @param fromDate
	 * @param toDate
	 * @return 如果删除成功返回true
	 */
	public boolean deleteLogsByDate(Date fromDate,Date toDate);
	
	/**
	 * 通过返回的Log对象是否为空来判断某个日志是否存在。
	 * @param logId
	 * @return  Log
	 */
	public Log checkLogExist(Long logId);
	
	
	/**
	 * 获取日志表中的记录条数
	 * @return  int
	 */
	public int getCount();

}
