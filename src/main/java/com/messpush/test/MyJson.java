package com.messpush.test;

import com.messpush.tool.JSONException;
import com.messpush.tool.JSONObject;
import com.messpush.tool.XML;

public class MyJson {
	
	/**
	 * 将传入的xml解析为json<br>
	 * 并得到任意你想得到的节点
	 * @param str
	 * @return
	 * @throws JSONException 
	 */
	public static Object getYouWant(String str) throws JSONException{
		
		JSONObject json = XML.toJSONObject(str);
		if(json.length()<=1){
			return "传入的json节点太少";
		}else{
			
		}
		return null;
	}

}
