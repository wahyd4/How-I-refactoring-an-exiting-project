/**
 * 对数据进行分页，需要传入获取信息类型变量<br>
 * 传入json对象<br>
 * 当前所在页面<br>
 * 如果是信息页面需要传入类别ID cateId
 */
function doPage(type,json, currentPage,cateId) {
	//如果没有输入类别ID，则类别ID为0，即显示所有信息
	if(cateId ===null ||cateId ===undefined){
		cateId = parseInt(0);
	}
	// 所有数据
	var totalcount = json.totalcount.value;
	// 单页面条数
	var singlepage = json.singlepage.value;
	//显示分页信息div
	var target = null;
	//定义总页面
	var totalPage = null;
	//需要连接的页面
	var html = null;
	//定义前一页面
	var prev;
	//根据页面不同需要执行的方法
	switch (type) {
		case 1: 	
			// 清除分页div的子节点
			target = document.getElementById("page_div_msg");
			html =  'msgManage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		
		case 2: 
			// 清除分页div的子节点
			target = document.getElementById("page_div_ser");
			html =  'serManage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		
		case 3: 
			// 清除分页div的子节点
			target = document.getElementById("page_div_usr");
			html =  'usrmsgManage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		
		case 4: 
			// 清除分页div的子节点
			target = document.getElementById("page_div_cate");
			html =  'cateManage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		
		case 5: 
			// 清除分页div的子节点
			target = document.getElementById("page_div_log");
			html =  'logManage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		case 6:
			// 清除分页div的子节点
			target = document.getElementById("page_div_tempmsg");
			html =  'tempMessage.html';
			while(target.firstChild) {
				target.removeChild(target.firstChild);
			}
			break;
		

	}
	
	while(target.firstChild){
		target.removeChild(target.firstChild);
	}

	//处理分页逻辑
	//说明输入参数有错，立即返回
	if(Math.ceil(totalcount / singlepage) <= currentPage) {
		return;
	} 
      totalPage = Math.ceil(totalcount/singlepage);
      //设置前一页面
	if(currentPage == 0) {
		prev = document.createElement("span");
		prev.innerText ='<';
		prev.setAttribute('class', 'disabled');
      
	} else {
		prev = document.createElement('a');
		prev.innerText ='<';
		prev.setAttribute('href',html+'?page='+(parseInt(currentPage)-parseInt(1))+"&cateid="+cateId);
		
	}
	target.appendChild(prev);
	//设置前几个页面的数字列表显示方式，最多显示前5个页面
	/*分两种情况，如果当前页面大于等于5则显示完
	 * 否则显示第一页为止 
	 */
	 if(currentPage>4){
	 	//需要显示前5也
	 	for(var i = (currentPage-4);i<currentPage;i++){
	 		var temp = document.createElement('a');
			temp.innerText  =(parseInt(i) +parseInt(1));
		    temp.setAttribute('href', html+'?page='+i+"&cateid="+cateId);
			target.appendChild(temp); 		
	 	}
	 }else{
	 	//显示到第一页(编号为0)
	 	for(var i=0;i<currentPage;i++){
	 		var temp = document.createElement('a');
			temp.innerText  =(parseInt(i) + parseInt(1));
		    temp.setAttribute('href', html+'?page='+i+"&cateid="+cateId);
			target.appendChild(temp); 	
	 	}
	 }
	
  //设置当前页面
	var current = document.createElement('span');
	current.setAttribute('class', 'current');
	current.innerText = (parseInt(currentPage) + parseInt(1));
	//由于系统设计时以0开始，所以需要加1
	target.appendChild(current);
	//当所有页面编号大于现有页面5时，将不会显示完
	if((totalPage - currentPage) > 6) {
		for(var i = (parseInt(currentPage) + parseInt(1)); i < (parseInt(currentPage) + parseInt(7)); i++) {
			var temp = document.createElement('a');
			temp.innerText  =(parseInt(i) + parseInt(1));
		    temp.setAttribute('href', html+'?page='+i+"&cateid="+cateId);
			target.appendChild(temp);
		}
		
		var  next = document.createElement('a');
		next.innerHTML = '>';
		next.setAttribute('href',html+'?page='+(parseInt(currentPage)+parseInt(1))+"&cateid="+cateId);
		target.appendChild(next);
	} else {
	
		
		for(var j = (parseInt(currentPage) + parseInt(1)); j < totalPage; j++) {
		
			var temp = document.createElement('a');
			temp.innerText  =(parseInt(j) + parseInt(1));
		   temp.setAttribute('href', html+'?page='+j+"&cateid="+cateId);
			target.appendChild(temp);
		}
		if((parseInt(currentPage)+parseInt(1))==totalPage){
			var  next = document.createElement('span');
		next.innerHTML = '>';
		next.setAttribute('class','disabled');
		target.appendChild(next);
			
		}else{
			var  next = document.createElement('a');
		next.innerHTML = '>';
			next.setAttribute('href',html+'?page='+(parseInt(currentPage)+parseInt(1))+"&cateid="+cateId);
		target.appendChild(next);
		}
		
	}
	
}