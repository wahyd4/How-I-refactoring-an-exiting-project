package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="setting")
public class Setting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="app_name")
	private String appName;
	@Column(name="icp")
	private String icp;
	@Column(name="copyright")
    private String copyright;
   @Column(name="logo")
    private String logo;
	@Column(name="singlepage_count")
    private int singlepageCount;
	@Column(name="default_free_day")
    private int defaultFreeDay;
	@Column(name="search_count")
    private int searchCount;
	@Column(name="first_push_to_client_count")
    private int firstPushToClientCount;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getIcp() {
		return icp;
	}
	public void setIcp(String icp) {
		this.icp = icp;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getSinglepageCount() {
		return singlepageCount;
	}
	public void setSinglepageCount(int singlepageCount) {
		this.singlepageCount = singlepageCount;
	}
	public int getDefaultFreeDay() {
		return defaultFreeDay;
	}
	public void setDefaultFreeDay(int defaultFreeDay) {
		this.defaultFreeDay = defaultFreeDay;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getFirstPushToClientCount() {
		return firstPushToClientCount;
	}
	public void setFirstPushToClientCount(int firstPushToClientCount) {
		this.firstPushToClientCount = firstPushToClientCount;
	}
    
    
}
