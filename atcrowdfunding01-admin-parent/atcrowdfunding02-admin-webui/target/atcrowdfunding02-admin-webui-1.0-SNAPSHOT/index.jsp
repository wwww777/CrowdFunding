<%--
  Created by IntelliJ IDEA.
  User: 43483
  Date: 2021/12/21
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--&lt;%&ndash; 一、无base标签：&ndash;%&gt;--%>
<%--<a href="${pageContext.request.contextPath}/test/ssm.html">测试页面</a>--%>

<%-- 二、有base标签：--%>
<head>
  <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
      <script type="text/javascript" src="layer/layer.js" ></script>
  <script>
        $(function () {
            //btn1 向服务器发送数据
            //此方式可以在浏览器看到发送的请求体是Form Data(表单数据)
            $("#btn1").click(function () {
                $.ajax({
                    url: "send/array/one.html",         //请求目标资源地址
                    type: "post",                 //请求方式
                    data: {
                        "array":[5,8,12]
                    },                    //发送的请求参数
                    dataType: "text",                   //表示如何对待服务器返回的数据
                    success: function (response) {
                        alert(response);
                    },
                    error: function (response) {
                        alert(response);
                    }

                });
            });
            //btn2
            //此方式可以在浏览器看到发送的请求体是Request Payload(请求负载)
            $("#btn2").click(function () {
                //准备要发送的数据
                var array=[5,8,12];
                //必须先将目标转换成JSON字符串
                var arrayStr = JSON.stringify(array);
                $.ajax({
                    url: "send/array/two.json",
                    type: "post",
                    data: arrayStr,
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",  //告诉服务器端当前请求的请求体是JSON格式
                    success: function (response) {
                        console.log(response);
                    },
                    error: function (response) {
                        console.log(response);
                    }

                });
            });
          $("#btn5").click(function () {
            layer.msg("test layer");
          });
        });


    </script>
</head>
<body>
<a href="test/ssm.html">测试页面</a>
<br/>
<button id="btn1">Test Ajax One</button>
<br/><br/>
<button id="btn2">Test Ajax Two</button>
<button id="btn5">Test layer</button>
</body>
</html>
