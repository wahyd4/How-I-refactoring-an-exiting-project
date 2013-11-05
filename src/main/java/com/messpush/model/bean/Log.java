package com.messpush.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 日志信息的Bean
 * @author Junv
 *
 */
@Entity
@Table(name="log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="log_id")
	private Long logId;
	
	@Column(name="target")
	private String target;
	
	@Column(name="log_level")
	private String logLevel;
	
	@Column(name="log_desc")
	private String logDesc;
	
	@Column(name="time")
	private Date time;
	
	public Log(){
		
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
}
