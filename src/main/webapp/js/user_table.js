//定义页面
var page;
$(document).ready(function() {

	//获取地址中相关参数
	var Request = new GetRequest();
	page = Request['page'];

	if(page === undefined) {
		page = 0;
	}
	//获取权限
	getAuthority('用户管理');
	//填充设置信息到页面
	getBasicInfo();
	//获取所有用户类别
	getAllUserGroups();
	//设置添加用户的窗体
	$(".add_user button").click(function() {

		art.dialog({
			title:'添加用户',
			id : 'addUser',
			lock : true,
			content : document.getElementById('user_form'),
			okVal : '确定',
			ok : function() {
				$('#add_user_form').submit();
				return false;
			},
			cancelVal : '取消',
			cancel : true
		});
	});
	// 初始化 ajax form新建用户
	var updateUserOpt = {
		beforeSubmit : validateUpdateUser,
		success : function(responseText) {
			var json = $.xml2json(responseText);
			console.log(json);
			if(json.value === 'true') {
				showDialog("更新成功", 2);
				setInterval(function() {
					window.location.reload();
				}, 1000);
			} else {
				showDialog("更新失败", 2);
			}
		},
		url : '../UpdateUser', // override for form's 'action' attribute
		type : 'post', // 'get' or 'post', override for form's 'method'
		clearForm : true
	};

	// bind to the form's submit event
	$('#update_user_form').submit(function() {
		$(this).ajaxSubmit(updateUserOpt);
		return false;
	});
	var newUserOpt = {
		beforeSubmit : validateAddUser,
		success : successSaveUser,
		url : '../SaveUser',
		type : 'post',
		clearForm : true
	};

	$('#add_user_form').submit(function() {
		$(this).ajaxSubmit(newUserOpt);
		return false;
	});
	//通过页面编号获取数据
	getUsersByPage(page);
	//实例化js验证表单
	$("#add_user_form").validationEngine();
	$("#update_user_form").validationEngine();
});
/**
 * js验证form表单
 */
function validateAddUser() {
	if($("#add_user_form").validationEngine('validate')) {
		return true;
	} else {
		return false;
	}
}

function validateUpdateUser() {
	if($("#update_user_form").validationEngine()) {
		return true;
	} else {
		return false;
	}
}

/**
 * 成功处理添加用户返回
 * @param responseText
 */
function successSaveUser(responseText) {
	var json = $.xml2json(responseText);
	if(json.value === 'false') {
		showDialog('保存用户出现错误,请稍后再试', 2);
		return;
	}
	//填充表格
	fillUserTable(json, false, false);
	//关闭对话框
	art.dialog.list['addUser'].close();
	showDialog('成功添加用户', 2);
}

/**
 * 按照页面获取数据
 */
function getUsersByPage(page) {
	$.ajax({
		url : '../GetUsers?page=' + page,
		method : 'post',
		success : successGetUsersByPage
	});
}

/**
 * 成功处理用户用户信息
 * @param responseText
 */
function successGetUsersByPage(responseText) {
	var json = $.xml2json(responseText);
	//填充table
	fillUserTable(json, true, true);
}

/**
 * 填充用户表格
 * @param json
 * @param cleanTable  是否清除表格
 * @param ifDoPage  是否执行分页
 */
function fillUserTable(json, cleanTable, ifDoPage) {

	if(json.users.size === 0) {
	   showDialog("没有可供显示的信息",2);	
		return;
	}
	var userTable = document.getElementById("user_tbody");
	if(userTable == true) {
		// 清空其子节点
		while(userTable.firstChild) {
			userTable.removeChild(userTable.firstChild);
		}
	}
	//将json.user强制转成一个数组
	var users = sureArray(json.users.useritem);
	for(var i = 0; i < users.length; i++) {
		var tr = document.createElement("tr");
		for(var j = 0; j < 10; j++) {
			var td = document.createElement('td');
			tr.appendChild(td);
		}
		tr.childNodes[0].innerHTML = '<input type="checkbox">';
		if(tr.childNodes[0].addEventListener) {
			tr.childNodes[0].childNodes[0].addEventListener("click", handleCheckboxClick, false);
		} else {
			tr.childNodes[0].childNodes[0].attachEvent("click", handleCheckboxClick);
		}
		//设置用户ID
		tr.childNodes[0].setAttribute('userid', users[i].userid);
		tr.childNodes[1].innerText = users[i].username;
		tr.childNodes[2].setAttribute('groupid', users[i].usergroupid);
		tr.childNodes[2].innerText = users[i].usergroupname;
		tr.childNodes[3].innerText = users[i].realname;
		tr.childNodes[4].innerText = users[i].tel;
		tr.childNodes[5].innerText = users[i].mobilephone;
		tr.childNodes[6].innerText = users[i].email;
		tr.childNodes[7].setAttribute('isblocked', users[i].isblocked);
		if(users[i].isblocked == "false") {
			tr.childNodes[7].innerText = "否";
			tr.childNodes[7].textContent = "否";
		} else {
			tr.childNodes[7].innerText = "是";
			tr.childNodes[7].textContent = "是";
		}
		tr.childNodes[8].innerHTML = '<a onclick="editUser()"><img src="../images/edit.png"></a>';
		tr.childNodes[9].innerHTML = '<a  onclick="deleteUser();" ><img src="../images/delete.png"></a>';
		userTable.appendChild(tr);
	}
	if(ifDoPage === true) {
		doPage(3, json, page);
	}
}

/**
 * 编辑用户
 * @param event
 */
function editUser(event) {
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	//打开对话框
	art.dialog({
		title:'编辑用户',
		id : 'updateUser',
		content : document.getElementById('update_user'),
		lock : true,
		okVal : '确定',
		ok : function() {
			$('#update_user_form').submit();
			return false;
		},
		cancelVal : '取消',
		cancel : true
	});
	//向修改用户中填入信息
	document.getElementById('userid').value = tr.childNodes[0].getAttribute('userid');
	document.getElementById('update_groupid').value = tr.childNodes[2].getAttribute('groupid');
	document.getElementById('update_real_name').value = tr.childNodes[3].innerText;
	document.getElementById('update_tel').value = tr.childNodes[4].innerText;
	document.getElementById('update_phone').value = tr.childNodes[5].innerText;
	document.getElementById('update_email').value = tr.childNodes[6].innerText;
	document.getElementById('update_isblocked').value = tr.childNodes[7].getAttribute('isblocked');
}

/**
 * 删除一个用户
 */
function deleteUser(event) {
	var res = window.confirm("你确认要删除？");
	if(res === false) {
		return;
	}
	//可以保证兼容IE浏览器
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	var userid = tr.childNodes[0].getAttribute('userid');
	//执行删除操作
	$.ajax({
		url : '../DeleteUser?userid=' + userid,
		method : 'post',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			if(json.value === 'false') {
				showDialog('删除失败!', 2);
				return;
			}
			showDialog('删除成功', 2);
			//清除该节点
			document.getElementById("user_tbody").removeChild(tr);
		}
	});
}

/**
 * 获取所有用户类别信息，并显示到页面上
 */
function getAllUserGroups() {
	$.ajax({
		url : '../getAllUserGroups',
		method : 'get',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			var groups = sureArray(json.groups.groupitem);
			var groupsSelect = document.getElementById('groupid');
			var updateGroupsSelect = document.getElementById('update_groupid');
			for(var i = 0; i < groups.length; i++) {
				//向新建用户填充select
				groupsSelect.options[i] = new Option(groups[i].groupname, groups[i].groupid);
				//向编辑用户填充select
				updateGroupsSelect.options[i] = new Option(groups[i].groupname, groups[i].groupid);
			}
		}
	});
}