var msg;
//设置类别ID
var cateId;

$(document).ready(function() {
	//获取地址中相关参数
	var Request = new GetRequest();
	msg = Request['page'];

	if(msg === undefined) {
		msg = 0;
	}
	cateId = Request['cateid'];
	if(cateId === undefined) {
		cateId = 0;
	}
	//获取权限
	getAuthority('信息管理');
	// 填充设置信息到页面
	getBasicInfo();
	// 设置添加信息dialog

	$('#add_mess').click(function() {
		art.dialog({
			id : 'addMess',
			title : '添加信息',
			lock : true,
			content : document.getElementById('mess_form'),
			okVal : '确定',
			ok : function() {
				//发布信息
				$('#save_message').submit();
				return false;
			},
			cancelVal : '关闭',
			cancel : true
		});

	});
	// 保存信息
	var options = {
		beforeSubmit : validateSaveMess,
		success : successSavedMsg,
		url : '../SaveMessage',
		type : 'post',
		clearForm : true
	};

	$('#save_message').submit(function() {
		$(this).ajaxSubmit(options);
		return false;
	});
	// 搜索消息...............
	var searchMsgOpt = {
		beforeSubmit : validateSearchMess,
		success : successSearchMsg,
		url : '../SearchMessage',
		type : 'post',
		clearForm : false
		// 获取数据后是否清空表单
	};

	// bind to the form's submit event
	$('#search_msg_form').submit(function() {
		$(this).ajaxSubmit(searchMsgOpt);
		return false;
	});
	// 更新消息按钮
	if(msg === undefined) {
		// 通过页面编号获取消息
		getMessageByPage(0, cateId);
	} else {
		getMessageByPage(parseInt(msg), cateId);
	}

	// 更新消息
	var updateMessOpt = {
		beforeSubmit : validateUpdateMess,
		success : successUpdateMess,
		url : '../UpdateMessage', // override for form's
		// 'action' attribute
		type : 'post', // 'get' or 'post', override for form's
		// 'method' attribute
		clearForm : true
	};
	// bind to the form's submit event
	$('#update_message').submit(function() {
		$(this).ajaxSubmit(updateMessOpt);
		return false;
	});
	// 获取所有类别信息，并填入添加消息和更新消息表单中
	// 同时将其添加至类别管理的表格中
	$.ajax({
		url : '../GetAllCategorys',
		success : function(responseText) {
			var json = null;
			if(responseText == null) {
				showDialog('返回数据出错', 2);
			} else {
				json = $.xml2json(responseText);

				var updateSelect = document.getElementById('update_cate');
				var newSelect = document.getElementById('cate');
				//按照类别查看消息，并为其添加事件
				var LookByCate = document.getElementById("look_by_cate");
				//为了兼容IE
				if(LookByCate.addEventListener) {
					//在谷歌浏览器里面是change事件
					LookByCate.addEventListener('change', getMessagesByCate, false);
					LookByCate.addEventListener('onchange', getMessagesByCate, false);
				} else {
					LookByCate.attachEvent('onchange', getMessagesByCate);
				}
				for(var i = 0; i < json.categorys.categoryitem.length; i++) {
					//将所有类别填充到相应的select中
					updateSelect.options[i] = new Option(json.categorys.categoryitem[i].catename, parseInt(json.categorys.categoryitem[i].cateid));
					//新建信息
					newSelect.options[i] = new Option(json.categorys.categoryitem[i].catename, parseInt(json.categorys.categoryitem[i].cateid));
					//根据类别查看信息
					LookByCate.options[i + 1] = new Option(json.categorys.categoryitem[i].catename, parseInt(json.categorys.categoryitem[i].cateid));
				}
				//在类别加载完之后改变类别
				document.getElementById("look_by_cate").value = cateId;
			}
		}
	});
	// 。。。js验证初始化
	$("#save_message").validationEngine();
	$("#update_message").validationEngine();
	$("#search_msg_form").validationEngine();
});
/**
 * 更新
 * 通过类别获取信息
 * @param event
 */
function getMessagesByCate(event) {
	var ev = event || window.event;
	var target = ev.target || ev.srcElement;
	var value = target.value;
	
	window.open('msgManage.html?page=0&cateid=' + value, '_parent');
}

/**
 * 验证表单消息页面表单<br>
 *
 * @returns {Boolean}
 */
function validateSaveMess() {
	//测试得出没有进行验证的表单默认返回true，因此我们需要&&
	if($("#save_message").validationEngine('validate')) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证更新信息
 */
function validateUpdateMess() {
	//测试得出没有进行验证的表单默认返回true，因此我们需要&&
	if($("#update_message").validationEngine('validate')) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证搜索信息
 */
function validateSearchMess() {
	//测试得出没有进行验证的表单默认返回true，因此我们需要&&
	if($("#search_msg_form").validationEngine('validate')) {
		return true;
	} else {
		return false;
	}
}

/**
 * 通过页面编号和类别ID获取信息
 */
function getMessageByPage(page, cateId) {
	// 通过页面编号获取消息
	$.ajax({
		url : '../GetMessagesByCate?page=' + msg + "&cateid=" + cateId,
		method : 'post',
		success : messTable
	});
}

// 打开网页时获取数据
function messTable(responseText, responseXML) {
	var json = $.xml2json(responseText);
	fillMessageTable(json, true, true);
}

/**
 * 删除一条消息
 */
function deleteMess(event) {
	var ev = event || window.event;
	// 获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	var id = tr.childNodes[1].getAttribute('messageid');
	var res = window.confirm("确定需要删除？");
	if(res) {
		$.ajax({
			url : '../DeleteMessage?messId=' + id,
			success : function(responseText) {
				var json = $.xml2json(responseText);
				if(json.value == "true") {
					showDialog('删除成功', 2);
					document.getElementById('msg_tbody').removeChild(tr);
				} else {
					showDialog('删除失败', 2);
				}
			}
		});
	}
}

// 编辑并更新消息。。。。。。。。。。。。。。
function editMess(event) {
	var ev = event || window.event;
	// 获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	art.dialog({
		id : 'update_mess',
		content : document.getElementById('mess_form_update'),
		lock : true,
		okVal : '确定',
		ok : function() {
			$('#update_message').submit();
			return false;
		},
		cancelVal : '取消',
		cancel : true
	});
	// 向表单中填入值
	document.getElementById("update_cate").value = tr.childNodes[1].getAttribute('cateid');
	document.getElementById("update_message_id").value = tr.childNodes[1].getAttribute('messageid');
	document.getElementById("update_title").value = tr.childNodes[2].innerText;
	document.getElementById("update_person").value = tr.childNodes[4].innerText;
	document.getElementById("update_tel").value = tr.childNodes[5].innerText;
	// 获取内容
	document.getElementById("update_content").value = tr.childNodes[3].getAttribute('fullcontent');
}

// 填充信息的表格
/**
 * 填写是否清除表格 ifDOage 是否执行分页操作
 */
function fillMessageTable(json, cleanTable, ifDoPage) {
	if(json.messages.size == 0) {
		showDialog("没有可供显示的信息，请返回！", 2);
		return;
	}
	var msgTable = document.getElementById("msg_tbody");
	// 清空其子节点
	if(cleanTable == true) {
		while(msgTable.firstChild) {
			msgTable.removeChild(msgTable.firstChild);
		}
	}
	//将messages强制转成数组
	var messages = sureArray(json.messages.messageitem);
	for(var i = 0; i < messages.length; i++) {
		var tr = document.createElement("tr");
		for(var j = 0; j < 9; j++) {
			var td = document.createElement('td');
			tr.appendChild(td);
		}
		tr.childNodes[0].innerHTML = '<input type="checkbox">';
		if(tr.childNodes[0].addEventListener) {
			tr.childNodes[0].childNodes[0].addEventListener("click", handleCheckboxClick, false);
		} else {
			tr.childNodes[0].childNodes[0].attachEvent("click", handleCheckboxClick);
		}
		tr.childNodes[1].innerText = messages[i].cate.catename;
		tr.childNodes[1].setAttribute('class', 'mess_category');
		tr.childNodes[1].setAttribute('cateid', messages[i].cate.cateid);
		tr.childNodes[1].setAttribute('messageid', messages[i].messageid);
		tr.childNodes[2].innerText = messages[i].title;
		tr.childNodes[3].setAttribute('fullcontent', messages[i].message);
		// 使用笨办法截取字符串
		var temp = messages[i].message;
		if(temp.length > 22) {
			temp = temp.substring(0, 20);
			temp += '...';
		}
		var time = messages[i].time.substring(5, 16);
		tr.childNodes[3].innerHTML = temp + "<img src='../images/view.png' title='查看完整内容' onclick = 'showFull(event)'>";
		tr.childNodes[4].innerText = messages[i].person;
		tr.childNodes[5].innerText = messages[i].tel;
		tr.childNodes[6].innerText = time;
		tr.childNodes[6].style.width = "80px";
		tr.childNodes[7].innerHTML = '<a onclick="editMess()"><img src="../images/edit.png"></a>';
		tr.childNodes[8].innerHTML = '<a  onclick="deleteMess();" ><img src="../images/delete.png"></a>';
		msgTable.appendChild(tr);
	}
	// 执行分页
	if(ifDoPage === true) {
		//执行分页加入类别ID
		doPage(1, json, msg, cateId);
	}
}

/**
 * 成功新建并保存一条信息
 */
function successSavedMsg(responseText, responseXML) {
	var json = $.xml2json(responseText);
	if(json.value == 'false') {
		showDialog('保存失败，请联系管理员！', 2);
	} else {
		//添加到table中
		fillMessageTable(json, false, false);
		showDialog('保存成功', 2);
		//关闭对话框
        art.dialog.list['addMess'].close();
	}
}

/**
 * 成功处理消息更新返回
 */
function successUpdateMess(responseText, responseXML) {
	var json = $.xml2json(responseText);
	if(json.value == 'true') {
		showDialog('更新成功', 2);
		//刷新页面
		window.location.reload();
	} else {
		showDialog('更新失败,请稍后再试', 2);
		return;
	}
}

/**
 * 处理搜索结果成功返回
 */
function successSearchMsg(responseText) {
	var json = $.xml2json(responseText);
	fillMessageTable(json, true, true);
	//填充message表格
}