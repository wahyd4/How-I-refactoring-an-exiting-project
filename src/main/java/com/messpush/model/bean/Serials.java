package com.messpush.model.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 激活码与GUID相关
 * @author Junv
 *
 */
@Entity
@Table(name="serial")
public class Serials {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="serial_num")
	private String serialNumber;
	
	@Column(name="ser_used")
	private  String serialIsUsed;
	
	@Column(name="availible_to")
	private Date availibleTo;
	
	@Column(name="blocked")
	private String bolcked;
	
	public Serials(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String  getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialIsUsed() {
		return serialIsUsed;
	}

	public void setSerialIsUsed(String serialIsUsed) {
		this.serialIsUsed = serialIsUsed;
	}

	public String getBolcked() {
		return bolcked;
	}

	public void setBolcked(String bolcked) {
		this.bolcked = bolcked;
	}

	public Date getAvailibleTo() {
		return availibleTo;
	}

	public void setAvailibleTo(Date availibleTo) {
		this.availibleTo = availibleTo;
	}



    


	
	

}
