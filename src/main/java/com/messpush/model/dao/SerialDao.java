package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.Serials;

/**
 * 提供激活码Bean处理的相关接口
 * @author Junv
 *
 */
public interface SerialDao {
	
	/**
	 * 新建并保存一个激活码
	 * @param sers
	 * @return   Serials
	 */
	public Serials saveSerial(Serials ser);
	
	/**
	 * 更新一个激活码
	 * @param ser
	 * @return  Serials
	 */
	public Serials updateSerial(Serials ser);
	
    /**
     * 获取多个序列号
     * @param hql
     * @param start
     * @param count
     * @return  List<Serials>
     */
	public List<Serials> selectSerials(String hql,Integer start ,Integer count);
	
	/**
	 * 删除某条激活码消息,如果返回对象为空则表明删除成功
	 * @param serialNumber
	 * @return  Serials
	 */
	public  Serials deleteSerial(Long id);
	
	/**
	 * 通过serialId判断serial是否存在<br>
	 * 同样也可以作为获取一个serial的方法
	 * @param serialNumber
	 * @return  Serials
	 */
	public  Serials getSerialByNumber(String  serialNumber);
	/**
	 * 获取激活码表中所有记录的条数
	 * @return int
	 */
	public int getCount();
	/**
	 * 通过激活码的id获取获取体格激活码的相关信息
	 * @param id
	 * @return  Serials
	 */
	public Serials getSerialById(Long id);
	
	/**
	 * 通过关键字搜索激活码，并且需要限定开始的页面数<br>
	 * 以及需要返回的结果数量
	 * @param keyword
	 * @param start
	 * @param count
	 * @return  List<Serials>
	 */
	public List<Serials> searchSerials(String keyword,int start,int count);
	
	/**
	 * 设置未使用过的激活码的状态为已使用
	 * @param ser
	 */
	public Serials setSerialIsUsed(Serials ser);

}
