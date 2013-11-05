var ser;

$(document).ready(function() {

	//获取地址中相关参数
	var Request = new GetRequest();
	ser = Request['page'];

	if(ser === undefined) {
		ser = 0;
	}
	//获取权限
	getAuthority('激活码管理');
	//填充设置信息到页面
	getBasicInfo();

	// 初始化更新激活码信息ajaxform
	var serUpdateOpt = {

		success : function(responseText) {
			var json = $.xml2json(responseText);
			if(json.value == "true") {
				alert('更新成功');
			} else {
				alert('更新失败');
			}
		},
		url : '../UpdateSerial',
		type : 'post',
		clearForm : true
	};

	// bind to the form's submit event
	$('#update_serial_form').submit(function() {
		$(this).ajaxSubmit(serUpdateOpt);
		return false;
	});
	//设置添加激活码dialog
	$(".add_serials button").click(function() {

		art.dialog({
			id : 'addSerial',
			content : document.getElementById('serials_form'),
			lock : true,
			okVal : '确定',
			ok : function() {
				$('#get_serial').submit();
				return false;
			},
			cancelVal : '取消',
			cancel : true
		});
	});
	//获取激活码.............................
	var serOpt = {
		beforeSubmit : validateGetSerial,
		success : successGetSerial,
		url : '../GetSerial',
		type : 'post',
		clearForm : true
	};

	// bind to the form's submit event
	$('#get_serial').submit(function() {
		$(this).ajaxSubmit(serOpt);
		return false;
	});
	//搜索激活码
	var searchSerials = {
		beforeSubmit : validateSearchSerial,
		success : successSearchSerials,
		url : '../SearchSerial',
		type : 'post',
		clearForm : false
	};

	$('#search_ser_form').submit(function() {
		$(this).ajaxSubmit(searchSerials);
		return false;
	});
	// 通过或页面编号获取激活码信息
	getSerialsByPage(parseInt(ser));
	//验证表单,初始化
	$("#get_serial").validationEngine();
	$("#search_ser_form").validationEngine();
});
//通过页面编号获取激活码
function successGetSerialsByPage(responseText) {
	var json = $.xml2json(responseText);
	fillSerialTable(json, true, true);
}

/**
 * 验证激活码页面的表单
 * @returns {Boolean}
 */
function validateGetSerial() {
	if($("#get_serial").validationEngine('validate')) {
		//验证成功，添加loading图标
		$('.serial_loading').css('display', 'block');
		return true;
	} else {
		return false;
	}
}

function validateSearchSerial() {
	if($("#search_ser_form").validationEngine('validate')) {
		return true;
	} else {
		return false;
	}
}

/**
 * 更新一个激活码
 */
function editSerial(i, id) {

	var targetTr = document.getElementById('serial_tbody').childNodes[i];
	document.getElementById('serial_id').value = id;
	document.getElementById('serial_is_used').value = targetTr.childNodes[2].getAttribute('state');
	document.getElementById('serial_blocked').value = targetTr.childNodes[4].getAttribute('state');
	var x = targetTr.childNodes[3].childNodes[0];
	document.getElementById('serial_avail_to').value = x.value;

	// 提交
	$('#update_serial_form').submit();

}

/**
 * 处理成功获取激活码返回
 */
function successGetSerial(responseText, responseXML) {
	var json = $.xml2json(responseText);
	if(json.status == 'false') {
		alert('出现错误');

	} else {

		$('.serial_loading').css('display', 'none');
		//修饰css
		var ul = document.createElement("ul");
		if(json.serials.size == 1) {

			var li = document.createElement("li");
			li.innerText = json.serials.serialitem.serialnumber;
			ul.appendChild(li);
		} else {
			for(var i = 0; i < json.serials.size; i++) {
				var li = document.createElement("li");
				li.innerText = json.serials.serialitem[i].serialnumber;
				ul.appendChild(li);
			}
		}

		$(ul).insertAfter('#count');
		//将激活码信息添加到表格中
		fillSerialTable(json, false, false);
	}
}

/**
 * 处理搜索激活码的成功返回
 */
function successSearchSerials(responseText) {
	var json = $.xml2json(responseText);
	fillSerialTable(json, true, true);
}

/**
 * 按照页面获取数据
 */
function getSerialsByPage(page) {
	$.ajax({
		url : '../GetSerials?page=' + page,
		method : 'post',
		success : successGetSerialsByPage
	});
}

//想serial表格中填入数据
/**
 * cleanTable  是否清空原表格内容
 * ifDoPage  是否执行分页数据
 */
function fillSerialTable(json, cleanTable, ifDoPage) {
	if(json.serials.size == 0) {
		showDialog("没有可供显示的信息！",2);
		return;
	}
	var cateTable = document.getElementById("serial_tbody");
	// 清空其子节点
	if(cleanTable == true) {
		while(cateTable.firstChild !== null) {
			cateTable.removeChild(cateTable.firstChild);
		}
	}
	var count = 0;
	//将激活码强制转成数组对象
	var serials = sureArray(json.serials.serialitem);

	for(var i = 0; i < parseInt(json.serials.size); i++) {
		count++;
		var tr = document.createElement("tr");
		for(var j = 0; j < 8; j++) {
			var td = document.createElement('td');
			tr.appendChild(td);
		}
		tr.childNodes[0].innerHTML = '<input type="checkbox">';
		if(tr.childNodes[0].addEventListener) {
			tr.childNodes[0].childNodes[0].addEventListener("click", handleCheckboxClick, false);
		} else {
			tr.childNodes[0].childNodes[0].attachEvent("click", handleCheckboxClick);
		}

		tr.childNodes[1].innerText = serials[i].serialnumber;

		if(serials[i].used == "false") {
			$(tr.childNodes[2]).iphoneSwitch("off", {
				switch_on_container_path : '../images/iphone_switch_container_on.png'
			});
			tr.childNodes[2].setAttribute('state', 'false');
		} else {
			$(tr.childNodes[2]).iphoneSwitch("on", {
				switch_on_container_path : '../images/iphone_switch_container_off.png'
			});
			tr.childNodes[2].setAttribute('state', 'true');
		}
		// 实例化一个datepicker
		tr.childNodes[3].innerHTML = '<input type="text" id="date' + i + '" value="' + serials[i].availto + '">';
		// 实例化iphone switch bar
		if(serials[i].blocked == "false") {
			$(tr.childNodes[4]).iphoneSwitch("off", {
				switch_on_container_path : '../images/iphone_switch_container_on.png'
			});

			tr.childNodes[4].setAttribute('state', 'false');
		} else {
			$(tr.childNodes[4]).iphoneSwitch("on", {
				switch_on_container_path : '../images/iphone_switch_container_off.png'
			});
			tr.childNodes[4].setAttribute('state', 'true');
		}
		var id = serials[i].id;
		tr.childNodes[5].innerHTML = '<a onclick="editSerial(' + i + ',' + id + ')"><img src="../images/save.png"></a>';
		cateTable.appendChild(tr);

	}
	// 添加datepickerUI和相关事件
	for(var x = 0; x < count; x++) {
		$("#date" + x).datepicker({
			dateFormat : 'yy-mm-dd',
			autoSize : true
		});
	}
	//执行分页
	if(ser === undefined) {
		ser = parseInt(0);
	}
	if(ifDoPage === true) {
		doPage(2, json, ser);
	}

}