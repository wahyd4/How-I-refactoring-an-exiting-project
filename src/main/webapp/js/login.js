$(document).ready(function() { 
	//判断用户是否已经登录
	checkIfLoged();
    var options = { 
     
        success:       showResponse,
        url:       'Login',         // override for form's 'action' attribute 
        type:      'post' , // 'get' or 'post', override for form's 'method' attribute 
        clearForm: true
    }; 
 
  
    $('#login').submit(function() { 
    	//x先执行转MD5操作
	  if(document.getElementById("password").value.length==32){
			
			//将用户密码使用MD5进行加密
		}else{
			//使用passvalue临时存储MD5值
			var passValue = faultylabs.MD5(document.getElementById("password").value); 
			document.getElementById("password").value = passValue;
		}  	
		
        $(this).ajaxSubmit(options);        
        return false; 
    }); 
}); 

 
// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
        var json = $.xml2json(responseText);
							if(json.status==='false'){
								alert('密码错误,请重新输入！');
							}else if(json.status==='none'){
								alert("没有该用户！");
							}else if(json.status==='blocked'){
								alert('该用户已被屏蔽，请联系系统管理员！');
							}else{
								window.open('admin/index.html','_self',false);
							}
} 
/**
 * 判断用户是否已经登录<br>
 * 如果已经登录则跳转至后台首页
 */
function checkIfLoged(){
	$.ajax({
		url : 'CheckIfLoged',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			if (responseText == null) {
				return;
			}
			if(json.value==='true'){
				 window.open('admin/index.html','_parent');
			}
		}
	});
}

