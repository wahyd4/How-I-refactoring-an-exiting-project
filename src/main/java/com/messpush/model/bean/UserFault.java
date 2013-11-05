package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述用户错误请求，或者错误操作信息的Bean
 * @author Junv
 *
 */
@Entity
@Table(name="usersfault")
public class UserFault {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="fault_id")
	private Long faultId;
	
	@Column(name="serial_id")
	private Long serialId;
	
	@Column(name="fault_desc")
	private String faultDesc;
	
	public UserFault(){
		
	}
	
	public Long getFaultId() {
		return faultId;
	}
	public void setFaultId(Long faultId) {
		this.faultId = faultId;
	}
	public Long getSerialId() {
		return serialId;
	}
	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}
	public String getFaultDesc() {
		return faultDesc;
	}
	public void setFaultDesc(String faultDesc) {
		this.faultDesc = faultDesc;
	}
     
	
}
