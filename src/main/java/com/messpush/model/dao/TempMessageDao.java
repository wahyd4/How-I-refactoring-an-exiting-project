package com.messpush.model.dao;

import java.sql.Timestamp;
import java.util.List;

import com.messpush.model.bean.TempMessage;

/**
 * 用户处理暂时信息的相关接口
 * @author Junv
 *
 */
public interface TempMessageDao {
	
	/**
	 * 通过信息ID获取一个信息对象<br>
	 * 也可以用于验证某信息是否存在
	 * @param messageId
	 * @return  TempMessage
	 */
	public TempMessage getTempMessageById(Integer messageId);
	
	/**
	 * 通过信息ID删除一条信息<br>
	 * 如果返回对象为null则删除成功
	 * @param MessageId
	 * @return  TempMessage
	 */
	public  TempMessage deleteTempMessageById(Integer MessageId);
	
	/**
	 * 保存一条信息<br>
	 * 如果返回对象不为空则保存成功
	 * @param tempMess
	 * @return  TempMessage
	 */
	public  TempMessage saveTempMessage(TempMessage tempMess);
	
	/**
	 * 通过发布信息的时间获取一条信息
	 * @param time
	 * @return  TempMessage
	 */
	public TempMessage getTempMessageByTime(Timestamp time);
	
	/**
	 * 通过多条暂时信息
	 * @param start
	 * @param count
	 * @return  List<TempMessage>
	 */
	public List<TempMessage> getTempMessagesByPage(int start,int count);
	
   /**
    * 获取暂时信息的数量
    * @return  Integer
    */
	public Integer getCount();

}
