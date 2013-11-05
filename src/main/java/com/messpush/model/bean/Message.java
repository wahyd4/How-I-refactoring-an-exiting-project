package com.messpush.model.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 所发布消息的bean
 * 
 * @author Junv
 * 
 */
@Entity
@Table(name = "msg")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "msg_id")
	private Long messageId;
	
	@Column(name = "cate_id")
	private int categoryId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "msg")
	private String message;
	
	@Column(name = "tags")
	private String tags;
	
	@Column(name = "person")
	private String preson;
	
	@Column(name = "tel")
	private String telephone;
	
	@Column(name = "time")
	private Timestamp time;

	public Message() {

	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPreson() {
		return preson;
	}

	public void setPreson(String preson) {
		this.preson = preson;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
