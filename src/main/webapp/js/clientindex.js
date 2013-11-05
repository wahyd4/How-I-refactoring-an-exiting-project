//用于设置最大的信息Id
var curentMax = 0;

// 设置一个用户检测用户是否登录的变量
var isLogedIn = false;
//存储激活码
var serialNum = null;
//默认设置三分钟
var refreshTime = 1000 * 60 * 3;

//处理是否是否频繁点击手动刷新
var checkRefeshIsAvailable = 0;
$(document).ready(function() {
	

	//从cookie中读取值
	serialNum = getCookie('serial');
	//看是否需要执行登录
	ifDoLogin('我们将保存登录信息cookie');

	$("#refresh_home").click(function() {
		refreshByHand();
	});
	// 绑定事件
	$('#add_mess').click(function() {
		art.dialog({
			id : 'newMess',
			lock : true,
			background : '#000000', // 背景色
			opacity : 0.85,
			title : '请输入信息内容',
			content : document.getElementById('new_mess')
		});
	});
	// 初始化 ajax form登录表单
	var loginOpt = {
		success : function(responseText) {
			var json = $.xml2json(responseText);
			//如果为true表明登录成功
			if(json.ser.state === 'true') {
				//向cookie中写入值
				fuckCookie(serialNum);
				isLogedIn = true;
				//关闭该dialog
				art.dialog.list['loginDialog'].close();
				// 获取数据
				refreshByHand();
				//设置定时器
				setInterval(refreshByHand, refreshTime);
			} else {
				//说明之前存入的激活码是假的，需要将其置为null
				serialNum = null;
				alert(json.ser.desc);
			}
		},
		url : '../ClientLogin', 
		type : 'post', 
		clearForm : true
	};
// bind to the form's submit event
	$('#do_login_form').submit(function() {
		var serial = document.getElementById("serial").value;

		if(serial !== "" && serial.length === 10) {
			//暂且当做此时的激活码是真实的
			serialNum = serial;
			$(this).ajaxSubmit(loginOpt);
		} else {
			alert('请输入正确激活码！');
		}
		return false;
	});

	
	// 初始化 ajax form提交待审核信息
	var newMessOpt = {
		success : function(responseText) {
			var json = $.xml2json(responseText);
			//如果为true表明登录成功
			if(json.value=== 'true') {	    
				//关闭该dialog
				art.dialog.list['newMess'].close();
				art.dialog({
					content:'提交信息成功，审核后可见！',
					time:3
				});
			} else {
				art.dialog({
					content:'提交信息失败！',
					time:3
				});
			}
		},
		url : '../ClientSaveTempMessage', 
		type : 'post', 
		clearForm : true
	};
	
		// bind to the form's submit event
	$('#new_mess_form').submit(function() {
		//赋值
		document.getElementById("new_mess_serial").value=serialNum;
        var title = document.getElementById("title").value;
        var content = document.getElementById("content").value;
		if(title!==""&&content!==null&&title.length>=4&&content.length>=7) {
			
			$(this).ajaxSubmit(newMessOpt);
		} else {
			art.dialog({
				content:'请正确填写表单,标题长度大于4，内容长度大于7'
			});
		}
		return false;
	});
	
});
function refreshByHand() {
	//获取当前时间
		var now = new Date().getTime();
	//判断用户是否频繁刷新
	if(checkRefeshIsAvailable!==0){
		//如果间隔时间小于1分钟，则提示手动刷新太频繁
		if((now-checkRefeshIsAvailable)<1000*60){
			art.dialog({
				lock:true,
				content:'您刷新太频繁了，歇歇吧！',
				time:2
			});
			return ; //退出
		}
	}
	//更新checkRefeshIsAvailable的值
	checkRefeshIsAvailable = now;
	$.ajax({
		url : "../ClientGetMessages?msgid=" + curentMax + "&serial=" + serialNum,
		type : "post",
		crossDomain : true,
		success : function(responseContent) {
			var json = $.xml2json(responseContent);
			//说明需要重新登录
			if(json.value === 'false') {
				//清空cookie
				deleteCookie("serial");
				//清除Interval
				clearInterval(refreshByHand);
				//显示警告信息，等要求登录
				ifDoLogin(json.state);

			} else {
				var display_infor = document.getElementById("display_information");
				/**
				 * 遍历json中所有的节点 将节点动态添加到ul中
				 */
				var messages = sureArray(json.messages.messageitem);
				//显示滑动提示信息
				art.dialog.notice({
				    title: '更新内容',
				    width: 160,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
				    content: '此次更新了'+json.messages.size+'条消息。',
				    icon: 'succeed',
				    time: 3
				});		
				//如果返回消息条数为0，则返回
				if(json.messages.size == '0') {
					return;
				}
				for(var i = 0; i < messages.length; i++) {
					var liNode = document.createElement("li");
					// liNode.innerHTML = "<a rel='external'
					// data-transition='slide' > " + messages[i].title +
					// "</a>";
					var a = document.createElement("a");
					a.setAttribute('rel', 'external');
					a.setAttribute('data-transition', 'slide');
					a.setAttribute('message', messages[i].message);
					a.setAttribute('catename', messages[i].cate.catename);
					a.setAttribute('person', messages[i].person);
					a.setAttribute('tel', messages[i].tel);
					a.setAttribute('time', messages[i].time);
					a.innerText = messages[i].title;
					liNode.appendChild(a);
					/** 将得到的信息添加到 ul 的第一个位置 */
					display_infor.insertBefore(liNode, display_infor.firstChild);
				}
				// 设置最大ID
				curentMax = messages[messages.length - 1].messageid;
				//更新视图
				$(display_infor).listview('refresh');
				// 将div随着鼠标移动
				$("ul li").each(function(index) {
					$(this).mouseover(function(event) {
						/** 得到当前鼠标的位置 */
						var pageX = event.pageX;
						var pageY = event.pageY;

						var information = document.getElementById("information");
						information.innerHTML = "";
						/** 动态改变 div 的位置 */
						information.style.display = "block";
						information.style.left = pageX + 20 + "px";
						information.style.top = pageY + "px";
						/** 得到对应li的主题信息 */
						var span = document.createElement("span");
						// 获取信息的内容
						var p = document.createElement("p");
						p.setAttribute("id", "content");
						span.innerHTML = "<span class='item'>联系人: </span>" + event.target.getAttribute('person') + "<br>" + "<span class='item'>电话:</span>" + event.target.getAttribute('tel') + "<br><span class='item'>类别:</span>" + event.target.getAttribute('catename') + "<br><span class='item'>内容:</span>" + event.target.getAttribute('message') + "<br><span class='item'>时间：</span>" + event.target.getAttribute('time') + "<br>";
						information.appendChild(span);

						if(pageX < 30) {

							information.style.display = "none";
						}

					});
				});
				/**设置当鼠标离开ul的时候 将div设置为不可见*/
				$("ul").mouseout(function() {
					var information = document.getElementById("information");
					information.style.display = "none";
				});
				$("li").mouseout(function() {
					var information = document.getElementById("information");
					information.style.display = "none";
				});
			}
		}
	});
}

/**
 *
 *向服务器发送请求判断用户是否已经使用激活码登录
 * 判断用户是否已经登录，并作出相应反应
 * @param  mess 显示需要提示的信息
 */
function ifDoLogin(mess) {

	$.ajax({
		url : '../ClientCheckIfLoged',
		success : function(responseText) {
			var json = $.xml2json(responseText);
			//说明没有登录，则弹出登陆框
			if(json.value === 'false') {
				//如果没有登录，则弹出登陆框
				//登录状态为没有登录
				isLogedIn = false;
				//判断是否有提示信息显示
				if(mess !== undefined) {
					document.getElementById("warn_mess").innerText = mess;
				}
			
					art.dialog({
						id : 'loginDialog',
						lock : true,
						background : '#000000', // 背景色
						content : document.getElementById('login'),
						cancel : function() {
							if(isLogedIn === true) {
								return true;
							}
							return false;
						}
					});
					//如果sessionzh中保存的有激活码
					if(getCookie("serial") != null) {
					//将激活码填入其中
					document.getElementById("serial").value = getCookie("serial");
					//执行ajax提交
					$('#do_login_form').submit();
				} 
			} else {
				//如果已经登录则直接获取信息，添加定时器Interval
				// 获取数据
				refreshByHand();
				//设置定时器
				setInterval(refreshByHand, refreshTime);
			}
		}
	});
}

/**
 * 向客户端写入cookie
 * name
 * value
 * path
 * domain
 *
 */
function setCookie(name, value, expires, path, domain, secure) {
	var today = new Date();
	if(expires) {
		expires = 1000 * 60 * 60 * 24 * 365;
	}
	var expires_date = new Date(today.getTime() + (expires));
	document.cookie = name + '=' + escape(value) + ((expires ) ? ';expires=' + expires_date.toGMTString() : '' ) + //expires.toGMTString()
	((path ) ? ';path=' + path : '' ) + ((domain ) ? ';domain=' + domain : '' ) + ((secure ) ? ';secure' : '' );
}

/**
 * 从cookie中读取值
 */
function getCookie(name) {
	var start = document.cookie.indexOf(name + "=");
	var len = start + name.length + 1;
	if((!start ) && (name != document.cookie.substring(0, name.length) )) {
		return null;
	}
	if(start == -1)
		return null;
	var end = document.cookie.indexOf(';', len);
	if(end == -1)
		end = document.cookie.length;
	return unescape(document.cookie.substring(len, end));
}

/**
 * 删除cookie
 */
function deleteCookie(name, path, domain) {
	if(getCookie(name))
		document.cookie = name + '=' + ((path ) ? ';path=' + path : '') + ((domain ) ? ';domain=' + domain : '' ) + ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
}

/**
 * 写入cookie
 */
function fuckCookie(serial) {
	var expireDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 356).toGMTString();
	document.cookie = "serial=" + serial + ";expires=" + expireDate;
}

/**
 * 提示信息，类似于网吧催费那种滑动通知
 * @param options
 * @returns
 */
artDialog.notice = function (options) {
    var opt = options || {},
        api, aConfig, hide, wrap, top,
        duration = 800;
        
    var config = {
        id: 'Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        resize: false,
        follow: null,
        lock: false,
        init: function(here){
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;
            
            wrap.css('top', hide + 'px')
                .animate({top: top + 'px'}, duration, function () {
                    opt.init && opt.init.call(api, here);
                });
        },
        close: function(here){
            wrap.animate({top: hide + 'px'}, duration, function () {
                opt.close && opt.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });
            
            return false;
        }
    };	
    
    for (var i in opt) {
        if (config[i] === undefined) config[i] = opt[i];
    };
    
    return artDialog(config);
};