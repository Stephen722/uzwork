<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
    Welcome<br/><input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        //websocket = new WebSocket("ws://" + window.location.host + "/Web/uzchat/NEKO/5b618f0a670ad820457d87852a0ea1fb91b84876ad0452bf223a8e0fbc67fa301c0906f432f77562");
       websocket = new WebSocket("ws://" + window.location.host + "/Web/uzchat/陶轶/ec7af4c2d6883a3e0c87ab05b64ff1727fc81c736c07ef00223a8e0fbc67fa301c0906f432f77562");
    }
    else {
        alert('当前浏览器 Not support websocket');
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
    		var jsonData = JSON.parse(event.data);
        setMessageInnerHTML(jsonData.sendTime+" - "+jsonData.sendUsername+"("+jsonData.sendUserId+") says: "+jsonData.message);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //var toUserId = "7fc81c736c07ef00";
    //var toUsername = "陶轶";
    var toUserId = "91b84876ad0452bf";
    var toUsername = "NEKO";
    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        setMessageInnerHTML("I say:"+message);
        document.getElementById('text').value = "";
        websocket.send(JSON.stringify({
        	  toUserId: toUserId,
        	  toUsername: toUsername,
        	  message: message
        	}));
        
    }
</script>
</html>