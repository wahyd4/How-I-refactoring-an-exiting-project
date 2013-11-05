

/**
 * 获取设置信息
 */
function getBasicInfo(){
		$.ajax({
		url : '../GetApplicationInfo',
		success : function(responseText) {
			if(responseText == null) {
				 showDialog('获取数据失败',3000);
				 return ;
			} 
			//转换json对象
		var	json = $.xml2json(responseText);
		//填充系统基本信息
			fillBasicInfo(json);
		//填充返回的用户基本信息，并添加相关事件
			fillUserInfoAndAddHandler(json);
		}
	});
}

/**
 * 填充用户信息和添加相关事件
 */
function fillUserInfoAndAddHandler(json){
	document.getElementById('show_username').innerText = json.username;
	var out = document.getElementById("logout");
	var changePass = document.getElementById("change_password");
	//添加退出处理事件
	if(out.addEventListener){
		out.addEventListener('click',logout,false);
	}else{
		out.attachEvent('click',logout);
	}
   //处理改变密码事件
	if(changePass.addEventListener){
		changePass.addEventListener('click',changePassword,false);
	}else{
		changePass.attachEvent('click',changePassword);
	}
}
/**
 * 处理改变密码事件
 */
function changePassword(){
	//打开改变密码
	window.open('change_pass.html','_blank', "height=300, width=650, toolbar=no, menubar=no, scrollbars=no,top=150,left=300, resizable=no, location=no, status=no");
}
/**
 * 处理退出事件
 */
function logout(){
		$.ajax({
		url : '../Logout',
		success : function(responseText) {
			//转换json对象
		var	json = $.xml2json(responseText);
		     if(json.value==='true'){
		     	 showDialog('成功退出',3000);
		     	 window.open('../login.html','_parent');
		     	 return;
		     }
		   //如果没有成功，其他都显示失败
		      showDialog('呜，出现问题了！',3000);
		      return;
		}
	});
}
/**
 * 将系统基本信息填充到页面中
 */
function fillBasicInfo(json){

	document.title =  json.result.setting.appname +"—"+document.title; //改变标题
	document.getElementById("showlogo").setAttribute('src',json.result.setting.logo); //设置logo信息
	document.getElementById('showcopyright').innerText= json.result.setting.copyright ;  //设置版权信息
	document.getElementById('showicp').innerText= json.result.setting.icp ; //设置ICP备案信息
	
	
}

//处理点击checkbox事件
function handleCheckboxClick(event) {

	var tr = event.target.parentNode.parentNode;

	if(event.target.checked) {
		addClass("selected_tr", tr);
	} else {
		removeClass("selected_tr", tr);
	}

}

/**
 * 封装一个jqueryUI dialog信息
 * 参数为内容和关闭的时间<br>
 * 如果timeout设置为0,将长期显示
 */
function showDialog(content, timeout) {
	//如果该节点存在，则删除
	var target = document.getElementById("empty_dialog");
	if(target!=null &&target!=undefined){
			target.parentNode.removeChild(target);
	}
	target = document.createElement("div");
	document.body.appendChild(target);
	target.style.display = "none";
	target.innerText = content;
/*
	$(target).dialog({
		modal : true,
		show : 'slide',
		hide : 'slide'
	});*/
    var closeTime = null;
    if(timeout==0){
    	closeTime = null;
    }else{
    	  closeTime = timeout;
    }
	art.dialog({
		lock:true,
		content:target,
		time:closeTime
	});

}

/**
 * 显示日志的完整内容
 * @param event
 */
function showFull(event){
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var parent = target.parentNode || target.parentElement;
	showDialog(parent.getAttribute('fullcontent'), 0);
}

/*
 * #########################
 * 下面是权限相关的js
 * ##########################
 */
/**
 * 获取该用户权限信息
 * @param currentPageName  当前页面名称
 */
function getAuthority(currentPageName) {
	$.ajax({
		method : 'get',
		url : '../GetUserAuthority',
		success : function(responseText) {
			//转换json对象
			var json = $.xml2json(responseText);
			//执行权限操作
			doAuthority(json, currentPageName);
		}
	});
}

/**
 * 根据权限生成页面
 * @param json  数据
 * @param currentPageName  当前页面名称
 */
function doAuthority(json, currentPageName) {

	//将权限列表强制转成数组
	var authoritys = sureArray(json.authoritys.authorityitem);
	//获取菜单对象
	var menu = document.getElementById('menu');
	for ( var i = 0; i < authoritys.length; i++) {
		//如果某个权限的parent属性为0，表明其实一级权限
		if (authoritys[i].parent === '0') {
			var li = document.createElement('li');
			var a = document.createElement('a');
			//设置网址
			a.setAttribute('href', authoritys[i].authaction);
			//设置target属性
			a.setAttribute('target', '_self');
			//设置链接title属性
			a.setAttribute('title', authoritys[i].authname);
			//判断是否设置当前class属性
			if (authoritys[i].authname === currentPageName) {
				a.setAttribute('class', "current");
			}
			//设置innertext
				a.innerText = authoritys[i].authname;
			//兼容firefox
				a.textContent = authoritys[i].authname;
			//将a添加到li中
			li.appendChild(a);
			//将菜单添加到ul中
			menu.appendChild(li);
		}
	}
}
/*
 * ##############################
 * 下面是一些工具js
 * ##############################
 */
/**
 * 获取URL参数
 * 实例
 * var Request = new GetRequest();
 * var id = Request['serverid'];// 获取url中的id的值
 * @return
 */
function GetRequest() {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

/**
 * 向一个节点添加class属性
 * 
 * @param className
 * @param node
 * @returns {String}
 */
function addClass(className, node) {

	return node.className += " " + className;

}
/**
 * 向一个节点移除class属性
 * 
 * @param className
 * @param node
 */
function removeClass(className, node) {
	var tmpClassName = node.className;
	node.className = null; // 清除类名
	node.className = tmpClassName.split(
			new RegExp(" " + className + "|" + className + " " + "|" + "^"
					+ className + "$", "ig")).join("");
}

/**
 * 将对象强制转成数组
 * @param arr
 * @returns array
 */
function sureArray(arr){
	//判断对象是否是数组
	if (!Object.prototype.toString.call(arr) === '[object Array]') {
		var arrs = new Array();
		arrs.push(arr);
		arr = arrs; 	
	}
	return arr;
}