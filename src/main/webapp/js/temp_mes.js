var page;
window.onload = function(){
	//获取权限
	getAuthority('审核信息');
	//填充设置信息到页面
	getBasicInfo();
	
	//获取页面编号
	var Request = new GetRequest();
	page= Request['page'];
	if (page === undefined) {
		page = 0;
	}
	//通过页面编号获取信息
	getTempMessByPage(page);
};

/**
 * 通过页面编号获取信息
 * @param page
 */
function getTempMessByPage(page){
	// 通过页面编号获取消息
	$.ajax({
		url : '../GetTempMessage?page=' + page,
		method : 'get',
		success : fillTempMessageTable
	});
}

/**
 * 填充表格
 * @param responseText
 */
function fillTempMessageTable(responseText){
	var json = $.xml2json(responseText);
	//如果没有数据则返回
	if(json.tempmsgs.size==='0'){
		showDialog("没有可供显示的信息，请返回！", 3);
		return ;
	}
	var tempMsgs = sureArray(json.tempmsgs.tempmsgitem);
	var tempMsgBody = document.getElementById('temp_msg_tbody');
	for ( var i = 0; i <tempMsgs.length; i++) {
		var tr = document.createElement("tr");
		for ( var j = 0; j < 9; j++) {
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
		tr.childNodes[0].setAttribute('id',tempMsgs[i].id);
		tr.childNodes[1].innerText =tempMsgs[i].serial;
		tr.childNodes[2].innerText =tempMsgs[i].title;
		tr.childNodes[3].setAttribute('fullcontent',
				tempMsgs[i].msg);
		// 使用笨办法截取字符串
		var temp = tempMsgs[i].msg;
		if (temp.length > 22) {
			temp = temp.substring(0, 20);
			temp += '...';
		}
		var time =tempMsgs[i].time.substring(5, 16);
		tr.childNodes[3].innerHTML = temp
				+ "<img src='../images/view.png' title='查看完整内容' onclick = 'showFull(event)'>";
		tr.childNodes[4].innerText =tempMsgs[i].person;
		tr.childNodes[5].innerText =tempMsgs[i].tel;
		tr.childNodes[6].innerText = time;
		tr.childNodes[6].style.width = "80px";
		tr.childNodes[7].innerHTML = '<a onclick="checkTempMess()"><img src="../images/check.png"></a>';
		tr.childNodes[8].innerHTML = '<a  onclick="deleteTempMess();" ><img src="../images/delete.png"></a>';
		tempMsgBody.appendChild(tr);
	}
	doPage(6, json, page);
}

//处理审核信息事件
function checkTempMess(event){
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	var tempMsgBody = document.getElementById('temp_msg_tbody');
	var id = tr.childNodes[0].getAttribute('id');
	$.ajax({
		url : '../CheckTempMessage?id=' + id,
		method : 'post',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			if (json.value == 'true') {
			  //移除已经审核的节点
				tempMsgBody.removeChild(tr);
				showDialog('审核成功',3);
			} else {
				showDialog('审核失败',3);
			}
		}
	});
}

function deleteTempMess(event){
	var res = window.confirm("确认删除该信息？");
	//如果取消直接返回
	if(!res){
		return;
	}
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;
	var tempMsgBody = document.getElementById('temp_msg_tbody');
	var id = tr.childNodes[0].getAttribute('id');
	$.ajax({
		url : '../DeleteTempMessage?id=' + id,
		method : 'post',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			if (json.value == 'true') {
			  //移除已经审核的节点
				tempMsgBody.removeChild(tr);
				showDialog('删除成功',3);
			} else {
				showDialog('删除失败',3);
			}
		}
	});
}