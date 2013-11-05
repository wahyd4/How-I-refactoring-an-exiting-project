/**
 * 获取URL参数
 * 实例
 * var Request = new GetRequest();
 * var id = Request['serverid'];// 获取url中的id的值
 * @return
 */
function GetRequest() {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

/**
 * 向一个节点添加class属性
 * 
 * @param className
 * @param node
 * @returns {String}
 */
function addClass(className, node) {

	return node.className += " " + className;

}
/**
 * 向一个节点移除class属性
 * 
 * @param className
 * @param node
 */
function removeClass(className, node) {
	var tmpClassName = node.className;
	node.className = null; // 清除类名
	node.className = tmpClassName.split(
			new RegExp(" " + className + "|" + className + " " + "|" + "^"
					+ className + "$", "ig")).join("");
}

/**
 * 将对象强制转成数组
 * @param arr
 * @returns array
 */
function sureArray(arr){
	if (!$.isArray(arr)) {
		var arrs = new Array();
		arrs.push(arr);
		arr = arrs; 	
	}
	return arr;
}