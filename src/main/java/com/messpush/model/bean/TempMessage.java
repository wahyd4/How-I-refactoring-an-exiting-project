package com.messpush.model.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用于存储一般用户发布信息的bean，这些信息需要审核，才能显示
 * 
 * @author Junv
 * 
 */
@Entity
@Table(name = "temp_msg")
public class TempMessage {
	@Id
	@Column(name = "msg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer messageId;
	@Column(name = "serial")
	private String serialNumber;
	@Column(name = "msg")
	private String message;
	@Column(name = "person")
	private String person;
	@Column(name = "tel")
	private String tel;
	@Column(name = "title")
	private String title;
	@Column(name = "time")
	private Timestamp time;

	public TempMessage() {

	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
