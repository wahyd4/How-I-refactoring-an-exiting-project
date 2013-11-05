$(document).ready(function(){
	//获取权限
	getAuthority('系统设置');
	//实例化更新权限按钮
	$('#authority_update_submit').click(function(){
		//在提交更新权限之前，需要将更新后的值添加到隐藏域中
		var newAuthoritys = document.getElementById('update_auth_lists');
		var temp=""; //用来存入权限数据
		//笨办法
		for(var i=0;i<100;i++){
			if(document.getElementById(i)){
				if(document.getElementById(i).checked===true){
					 temp  += i+",";
				}
			}
		}
		//进行赋值
		newAuthoritys.value  = temp;
		console.log("temp"+temp);
    	$('#update_authority').submit();
		return false;   //返回false防止二次执行
	});
	
	$(".update_setting").click(function(){
		$('#update_setting_form').submit();
		return false;   //返回false防止二次执行
	});
	// 初始化 ajax form修改设置表单
	var updateSettingOpt = {
       beforeSubmit:validate,
		success : successUpdateSetting,
		url : '../UpdateSetting', // override for form's 'action' attribute
		type : 'post', // 'get' or 'post', override for form's 'method'
		clearForm : true
	};
	// bind to the form's submit event
	$('#update_setting_form').submit(function() {
		$(this).ajaxSubmit(updateSettingOpt);
		return false;
	});
	
	// 初始化 ajax form更新某个用户组的权限表单
	var updateAuthorityOpt = {
       beforeSubmit:validate,
		success : function (responseText){
			var json = $.xml2json(responseText);
			if(json.value==='true'){
				showDialog("更新权限成功！", 3);
				return;
			}
			showDialog("更新权限失败！", 3);
		},
		url : '../UpdateAuthority', 
		type : 'post', 
		clearForm : true
	};
	// bind to the form's submit event
	$('#update_authority').submit(function() {
		$(this).ajaxSubmit(updateAuthorityOpt);
		return false;
	});
	//获取系统常规系统
	getBasicInfo();
	//获取设置信息
	getSettingInfoAndFill();
	//初始化js验证
	$("#update_setting_form").validationEngine();
	
	//向显示权限页面添加事件
	var showmore = document.getElementById('show_authority');
	if(showmore.addEventListener){
		showmore.addEventListener('click', showAuthority, false);
	}else{
		showmore.attachEvent('click', showAuthority);
	}
});
/**
 * 验证js
 */
function validate(){
	if($("#update_setting_form").validationEngine('validate')){
		return true;
	}else{
		return false;
	}
}
//成功更新信息
function successUpdateSetting(responseText){
	var json = $.xml2json(responseText);
	if(json.value ==='true'){
		showDialog('更新成功！',3);
		fillSetting(json);
	}else{
		showDialog("更新失败，请稍后再试", 3);
	}
}

/**
 * 获取设置信息并填充表格
 */
function getSettingInfoAndFill(){
	$.ajax({
		url : '../GetSetting',
		success : function(responseText) {
			var json = null;
			if(responseText == null) {
				 showDialog('获取数据失败',2);
			} else {
				 json = $.xml2json(responseText);
				 //填充设置信息
				 fillSetting(json);
			}
		}
	});
}

function fillSetting(json){
	document.getElementById("setting_id").value=json.setting.id;
	document.getElementById("app_name").value = json.setting.appname;
	document.getElementById("logo").value = json.setting.logo;
	document.getElementById("icp").value = json.setting.icp;
	document.getElementById("copyright").value = json.setting.copyright;
	document.getElementById("singlepage_count").value = json.setting.singlepagecount;
	document.getElementById("default_free_day").value = json.setting.defaultfreeday;
	document.getElementById("search_result").value = json.setting.searchcount;
	document.getElementById("first_push_to_client_count").value = json.setting.firstpushtoclientcount;
}

/**
 * 显示权限设置区域信息
 * 并且获取权限相关数据
 */
function showAuthority(){
	
	var authorityContainer = document.getElementById('authority_container');
	var showmore = document.getElementById('show_authority');
	if(authorityContainer.style.display==='block'){
		$(authorityContainer).hide('slow');
		showmore.innerText = '显示权限配置';
		return ;
	}
	//当权限部分没有显示的时候执行下列代码
	//为用户组选择添加change事件
	var tempGroup = document.getElementById('update_auth_group_select');
	if(tempGroup.addEventListener){
		tempGroup.addEventListener('change', loadGroupAuth, false);
	}else{
		tempGroup.attachEvent('change', loadGroupAuth);
	}
		$(authorityContainer).show('slow');
		showmore.innerText = '隐藏权限设置';
		//获取所有权限信息
		$.ajax({
			url : '../GetAllAuthoritys',
			method: 'get',
			success :function(responseText){
				var json = $.xml2json(responseText);
				//获取权限显示部分form
				var AuthorityCon = document.getElementById('auth_con_inner');
				//在显示权限之前，首先判断里面是否有子节点，如果有则不添加
				if(AuthorityCon.childNodes&&AuthorityCon.childNodes.length>1){
					return;
				}
				var authoritys = sureArray(json.authoritys.authorityitem);
				var count = parseInt(0);
				for(var i=0;i<authoritys.length;i++){
					//当权限为一级权限或者抽象权限时，创建一级目录
					if(authoritys[i].parent==='0'||authoritys[i].parent ==='-1'){
						//每显示一个顶级权限 则加1
						count++;
						var checkbox =document.createElement('div');
						checkbox.setAttribute('class','auth_checkbox');
						checkbox.innerHTML = '<input type="checkbox" id="'+authoritys[i].authid+'"value="'+authoritys[i].authid+'">'+authoritys[i].authname;
						//添加到form表单中
						AuthorityCon.appendChild(checkbox);
						
					}		
				}
			}
		});
	
		//获取所有用户类别，并填充
		$.ajax({
			url : '../getAllUserGroups',
			method : 'get',
			success : function (responseText){
				var json  = $.xml2json(responseText);
				var groups = sureArray(json.groups.groupitem);
				var groupsSelect = document.getElementById('update_auth_group_select');
				for(var i=0;i<groups.length;i++){
					//向更新权限填充select
					groupsSelect.options[i] = new Option(groups[i].groupname,groups[i].groupid);
				}	
				//等加载完了再执行；
				loadGroupAuth();
			}
		});
	
}
/**
 * 加载一个小组的权限
 */
function loadGroupAuth(){
	//获取当前默认的用户组ID
	var groupsSelect = document.getElementById('update_auth_group_select');
  //获取该用户组的权限信息
	$.ajax({
		method : 'get',
		url : '../GetAuthorityByGroupId?groupid='+groupsSelect.value,
		success : function(responseText) {
			//转换json对象
			var json = $.xml2json(responseText);
			console.log(json);
			//防止如果不是第一次执行，可能已经勾选了权限，因此需要首先清除所有勾选的信息
			//这里采用笨办法清除，执行1-100的循环，去id为数字的节点，且将所有存在的节点checked属性设置为false
			for(var length=0;length<100;length++){
				var temp;
				if( temp = document.getElementById(length)){
					temp.checked = false;
				}
			}
			//填充数据到网页相应部分
			//填充用户组和权限对应的表记录ID号
			document.getElementById("group_to_auth_id").value = json.authoritys.id;
			console.log("权限ID"+json.authoritys.id);
			//填充组id
		document.getElementById('update_auth_group_select').value =json.groupinfo.groupid;
		//填充权限
		var ownAuthoritys=sureArray(json.authoritys.authorityitem);
		for(var i=0;i<ownAuthoritys.length;i++){
			//当权限为顶级权限或者抽象权限时，显示
		    if(ownAuthoritys[i].parent==='0'||ownAuthoritys[i].parent==='-1'){
		    	console.log("进来");
		    	document.getElementById(ownAuthoritys[i].authid).checked = true;
		    }	
		}
		}
	});

}