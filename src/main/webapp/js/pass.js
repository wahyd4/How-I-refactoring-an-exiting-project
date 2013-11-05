window.onload = function (){
	//初始化
	 $("#change_pass_form").validationEngine();
	 
		// 初始化 ajax form修改类别表单
		var changePassOpt = {
	        beforeSubmit:validateForm,
			success : successChangePassword,
			url : '../ChangePassword', // override for form's 'action' attribute
			type : 'post', // 'get' or 'post', override for form's 'method'
			// attribute
			clearForm : true
		};

		
		$('#change_pass_form').submit(function() {
			 //对激活码进行	MD5加密
			 var orgin = document.getElementById("orginal_pass").value;
			 var newPass = document.getElementById("new_pass").value;
			 var rePass = document.getElementById("re_pass").value;
			 //进行赋值
			 document.getElementById("orginal_pass").value=faultylabs.MD5(orgin);
			 document.getElementById("new_pass").value =faultylabs.MD5(newPass); 
			 document.getElementById("re_pass").value  = faultylabs.MD5(rePass); 
			 console.log(document.getElementById("orginal_pass").value);
			 console.log(document.getElementById("new_pass").value);
			$(this).ajaxSubmit(changePassOpt);
			return false;
		});
};

/**
 * 成功改变密码
 */
function successChangePassword(responseText){
	var json = $.xml2json(responseText);
	if(json.value==='true'){
		showDialog("更改密码成功,请关闭该窗体", 0);
	 
	}else{
		showDialog("更改密码失败,原密码输入错误！", 3000);
	}
}
/**
 * 验证表单，验证成功返回true
 */
function validateForm(){
	
	 var orgin = document.getElementById("orginal_pass").value;
	 var newPass = document.getElementById("new_pass").value;
	 //控制原密码和新密码不一样
	 if(orgin ==newPass){
		document.getElementById("orginal_pass").value = null;
		document.getElementById("new_pass").value =null;
		document.getElementById("re_pass").value = null;
		alert("新密码和原密码不能一致");
		 return false;
	 }
	 if($("#change_pass_form").validationEngine('validate')){
		 return true;
	 }else{
		 return false;
	 }
}