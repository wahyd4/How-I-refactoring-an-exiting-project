window.onload = function() {
	//获取地址中相关参数
	var Request = new GetRequest();
	page = Request['page'];
	//如果页面没有定义，默认获取第一页信息
	if (page === undefined) {
		page = 0;
	}
	//获取权限
	getAuthority('日志管理');
	//获取系统基本信息
	getBasicInfo();
	//获取数据并显示
	getLogsByPage(page);
};
/**
 * 通过页面编号获取日志信息
 * @param page
 */
function getLogsByPage(page) {
	$.ajax({
		url : '../GetLogsByPage?page=' + page,
		success : function(responseText) {
			var json = null;
			if (responseText == null) {
				showDialog('获取数据失败', 3000);
			} else {
				json = $.xml2json(responseText);
				fillLogTable(json, true, true);
			}
		}
	});
}
/**
 * 填充日志table
 * @param json  json对象
 * @param ifCleanTable 是否清除table
 * @param ifDoPage   是否执行分页
 */
function fillLogTable(json, ifCleanTable, ifDoPage) {
	if (json.logs.size == 0) {
		showDialog("没有内容可供显示，请返回！", 3000);
		return;
	}
	var logTable = document.getElementById("log_tbody");
	if (ifCleanTable == true) {
		// 清空其子节点
		while (logTable.firstChild) {
			logTable.removeChild(logTable.firstChild);
		}
	}
	//将日志强制转成数组
	var logs =  sureArray( json.logs.logitem);
	
		for ( var i = 0; i < logs.length; i++) {
			var tr = document.createElement("tr");
			for ( var j = 0; j < 7; j++) {
				var td = document.createElement('td');
				tr.appendChild(td);
			}
			tr.childNodes[0].innerHTML = '<input type="checkbox">';
			if (tr.childNodes[0].addEventListener) {
				tr.childNodes[0].childNodes[0].addEventListener("click",
						handleCheckboxClick, false);
			} else {
				tr.childNodes[0].childNodes[0].attachEvent("click",
						handleCheckboxClick);
			}
			tr.childNodes[1].innerText = logs[i].target;
			tr.childNodes[2].innerText = logs[i].loglevel;
			//如果日志信息为WARN则将其标志为黄色背景
			if(logs[i].loglevel==='WARN'){
				tr.style.background = '#f7be26';
			}else if(logs[i].loglevel==='ERROR'){
				tr.style.background = '#aa1a02';
			}
			//将完整信息放到属性中
			tr.childNodes[3].setAttribute('fullcontent', logs[i].logdesc) ;
			//使用笨办法截取字符串
			var temp = logs[i].logdesc;
			if(temp.length > 80) {
				temp = temp.substring(0, 80);
				temp += '...';
			}		
			tr.childNodes[3].innerHTML = temp+"<img src='../images/view.png' title='查看完整内容' onclick = 'showFull(event)'>";
			//对时间长度进行截取
			var time = logs[i].time.substring(5, 16);
			tr.childNodes[4].innerText = time;
			//添加子节点
			logTable.appendChild(tr);
		}
	//执行分页
	if (ifDoPage === true) {
		//5为日志页面的标记
		doPage(5, json, page);
	}
}
