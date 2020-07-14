<%--
  Created by IntelliJ IDEA.
  User: steadyjack
  Date: 2019/5/20
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>商城高并发抢购-抢购结果页</title>

</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h1 align="center">抢购成功-您好，您已成功抢购到商品: <strong style="color: red">${info.itemName}</strong> ，复制该链接并在浏览器采用新的页面打开，即可查看抢购详情：<a  href="${pageContext.request.contextPath}/record/detail/${info.code}" >商品详情</a>，并请您在1个小时内完成订单的支付，超时将失效该订单哦！祝你生活愉快！</h1>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/static/plugins/jquery.js"></script>
<script src="${ctx}/static/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plugins/jquery.cookie.min.js"></script>
<script src="${ctx}/static/plugins/jquery.countdown.min.js"></script>
</html>
















