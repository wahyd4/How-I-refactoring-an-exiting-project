var page; //定义当前的页面编号

$(document).ready(function() {
	//获取地址中相关参数
	var Request = new GetRequest();
	page = Request['page'];
	//如果页面没有定义，默认获取第一页信息
    if(page ===undefined){
    	page =0;
    }
  //获取权限
	getAuthority('类别管理');
	// 设置新建消息类别的dialog
	$(".add_cate").click(function() {
	
		art.dialog({
			id:'newCate',
			lock:true,
			content: document.getElementById("new_cate_form"),
			okVal : '确定',
			ok : function() {
				//添加一个类别
				$('#new_cate').submit();
				return false;
			},
			cancelVal : '关闭',
			cancel : true
		});
	});
	
	// 初始化 ajax form新建类别表单
	var newCateOpt = {
		beforeSubmit:validateNewCate,
		success : successSaveCate,
		url : '../SaveCategory', 
		type : 'post', 
		clearForm : true
	};

	// bind to the form's submit event
	$('#new_cate').submit(function() {
		$(this).ajaxSubmit(newCateOpt);
		return false;
	});
	
		// 初始化 ajax form修改类别表单
	var updateCateOpt = {
        beforeSubmit:validateUpdateCate,
		success : successUpdateCate,
		url : '../UpdateCategory', 
		type : 'post', 
	
		clearForm : true
	};

	// bind to the form's submit event
	$('#update_cate').submit(function() {
		$(this).ajaxSubmit(updateCateOpt);
		return false;
	});
	
	//填充设置信息到页面
	getBasicInfo();
	
	//通过分页获取类别信息
	getCategorysByPage(page);
	//实例化js验证
	 $("#new_cate").validationEngine();
	 $("#update_cate").validationEngine(); 
});
/**
 * 处理form表单验证
 */
function validateNewCate(){
	//js验证正确返回true
	if($("#new_cate").validationEngine('validate')){
		return true;
	}else{
		return false;
	}
}

/**
 * 处理form表单验证
 */
function validateUpdateCate(){
	//js验证正确返回true
	if($("#update_cate").validationEngine('validate')){
		return true;
	}else{
		return false;
	}
}
/**
 * 成功更新一个类别
 * @param responseText
 */
function successUpdateCate (responseText) {
       var json = $.xml2json(responseText);
       if(json.value==='false'){
       	 showDialog('更新失败，请联系管理员',2);
       }
       
      showDialog('更新成功',2);
      window.location.reload();
}

/**
 * 通过页面编号获取数据
 * @param page
 */
function getCategorysByPage(page){
	$.ajax({
		url : '../GetCategorys?page='+page,
		success : function(responseText) {
			var json = null;
			if(responseText == null) {
				 showDialog('获取数据失败',2);
			} else {
				 json = $.xml2json(responseText);
				fillCateTable(json, true,true);
			}
		}
	});
}

/**
 *  创建类别信息表格，并将相关信息加入其中
 * @param json  传入json对象
 * @param cleanTable 是否清除表格
 * @param ifDoPage 是否执行分页
 */
function fillCateTable(json, cleanTable,ifDoPage) {
   
	if (json.categorys.size == 0) {
	     showDialog("没有可供显示的信息！", 2);
	}
	var cateTable = document.getElementById("cate_tbody");
	if (cleanTable == true) {
		// 清空其子节点
		while (cateTable.firstChild) {
			cateTable.removeChild(cateTable.firstChild);
		}
	}
  //强制将类别转成array数组
	var categorys = sureArray(json.categorys.categoryitem);
		for ( var i = 0; i < categorys.length; i++) {
			var tr = document.createElement("tr");
			for ( var j = 0; j < 7; j++) {
				var td = document.createElement('td');
				tr.appendChild(td);
			}
			tr.childNodes[0].innerHTML = '<input type="checkbox">';
			  if(tr.childNodes[0].addEventListener){
		    	tr.childNodes[0].childNodes[0].addEventListener("click", handleCheckboxClick, false);
		    }else{
		    	tr.childNodes[0].childNodes[0].attachEvent("click",handleCheckboxClick);
		    }
			tr.childNodes[1].innerText =categorys[i].cateid;
			tr.childNodes[2].innerText =categorys[i].catename;
			tr.childNodes[3].innerText = categorys[i].catedesc;
			tr.childNodes[4].innerText = categorys[i].count;
			
			var cateid = categorys[i].cateid;
			tr.childNodes[5].innerHTML = '<a onclick="editCate()"><img src="../images/edit.png"></a>';
			tr.childNodes[6].innerHTML = '<a  onclick="deleteCate(' + i + ','
					+ cateid + ');" ><img src="../images/delete.png"></a>';
			cateTable.appendChild(tr);
		}
	//执行分页
	if(ifDoPage===true){
		doPage(4, json, page);
	}
}

// 删除一条类别信息
function deleteCate(i, cateId) {

	var res = window.confirm("您确定需要删除一个类别吗？");
	if (res) {
		$.ajax({
			url : '../DeleteCategory?cate_id=' + cateId,
			method : 'post',
			success : function(responseText) {

				var json = $.xml2json(responseText);
				if (json.value == 'true') {
					var cateTable = document.getElementById("cate_tbody");
					cateTable.removeChild(cateTable.childNodes[i]);
					showDialog('删除成功',2);
				} else {
					showDialog('删除失败',2);
				}
			}
		});
	}

}
/**
 * 编辑一个类别信息
 */
function editCate(event) {
	var ev = event || window.event;
	//获取父节点
	var target = ev.target || ev.srcElement;
	var tr = target.parentNode.parentNode.parentNode || target.parentElement.parentElement.parentElement;

	
	art.dialog({
		id: 'updateCate',
		content:document.getElementById('update_cate_form'),
		lock:true,
			okVal : '确定',
		ok : function() {
				$('#update_cate').submit();
			return false;
		},
		cancelVal : '取消',
		cancel : true
	});
	document.getElementById("update_cate_id").value = tr.childNodes[1].innerText;
	document.getElementById("update_cate_name").value = tr.childNodes[2].innerText;
	document.getElementById("update_cate_desc").value = tr.childNodes[3].innerText;

}

// 成功保存一个消息类别
function successSaveCate(responseText, responseXML) {
	var json = $.xml2json(responseText);
	if (json.value === 'false') {
	 showDialog('新建失败',2);
		return ;
	}
	art.dialog.list['newCate'].close();
	showDialog('新建成功！',2);
	fillCateTable(json, false,false);
}
