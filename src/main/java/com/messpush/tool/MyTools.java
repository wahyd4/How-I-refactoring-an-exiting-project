package com.messpush.tool;

import java.util.Random;

/**
 * 相关操作的工具类
 * 
 * @author Junv
 * 
 */
public class MyTools {

	/**
	 * 生成用户返回的警告页面信息
	 * 
	 * @param path
	 *            系统根目录
	 * @param content
	 *            需要显示的内容
	 * @param second
	 *            需要停留消息的秒数
	 * @return String
	 */
	public static String getAlertMessage(String path, String content, int second) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv='refresh' content='3;url=" + path
				+ "/login.html'>");
		sb.append("<link rel='stylesheet' type='text/css' href='" + path
				+ "/css/login.css'>");
		sb.append("</head>");
		sb.append("<body><form id='login'><fieldset id='inputs' style='font-size:18px;'>"
				+ content
				+ ","
				+ second
				+ "秒后你将跳转到登录页面</form></fieldset></body>");
		sb.append("</html>");

		return sb.toString();

	}

	/**
	 * 生成任意长度随机字符串<br>
	 * 系统默认生成长度为10
	 * @param length
	 * @return  String
	 */
	public static String getRandomString(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

}
