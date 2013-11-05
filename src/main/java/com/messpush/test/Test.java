package com.messpush.test;


import org.apache.log4j.Logger;

import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.controller.service.CategoryService;
import com.messpush.controller.service.MessageService;
import com.messpush.controller.service.SerialService;
import com.messpush.controller.service.SettingService;
import com.messpush.model.dao.CategoryDao;
import com.messpush.model.dao.MessageDao;

public class Test {
	private static Integer  x = null; 
	
	//static Logger logger = Logger.getLogger(Test.class);
	public static void main(String [] args){
		new Test();
		System.out.println("改变前"+x);
	     x= 10;
	     System.out.println("改变后"+x);
	}
	public  Test(){
		x = 5;
	}
   public  static void x(){
	   System.out.println("fuck.........");
   }

}
