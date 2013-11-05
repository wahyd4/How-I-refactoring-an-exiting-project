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