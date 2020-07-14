<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>商城高并发抢购-商品详情页面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/bootstrap-3.3.0/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/bootstrap-3.3.0/css/bootstrap-theme.min.css" type="text/css">
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <input id="killId" value="${detail.id}" type="hidden"/>
        <div class="panel-heading">
            <h1>商品名称：${detail.itemName}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">剩余数量：${detail.total}</h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">开始时间：<fmt:formatDate value="${detail.startTime}" pattern='yyyy-MM-dd HH:mm:ss'/></h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">结束时间：<fmt:formatDate value="${detail.endTime}" pattern='yyyy-MM-dd HH:mm:ss'/></h2>
        </div>
    </div>

    <td>
        <c:choose>
            <c:when test="${detail.canKill==1}">
                <a class="btn btn-info" style="font-size: 20px" onclick="executeKill()">抢购</a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-info" style="font-size: 20px">哈哈~商品已抢购完毕或者不在抢购时间段哦!</a>
            </c:otherwise>
        </c:choose>
    </td>
</div>

</body>
<script src="${ctx}/static/plugins/jquery.js"></script>
<script src="${ctx}/static/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plugins/jquery.cookie.min.js"></script>
<script src="${ctx}/static/plugins/jquery.countdown.min.js"></script>

<%--<script src="${ctx}/static/script/kill.js"></script>--%>
<link rel="stylesheet" href="${ctx}/static/css/detail.css" type="text/css">
<script type="text/javascript">

    function executeKill() {
        var killId=$("#killId").val();
        $.ajax({
            type: "POST",
            url: "${ctx}/excute",
            data: {"killId":killId, "userId":10},
            success: function(res){
                console.log(res);
                if (res.code==0) {
                    // console.log(res.msg);
                    window.location.href="${ctx}/success?orderNo="+res.orderNo;
                }else{
                    // console.log(res.msg);
                   window.location.href="${ctx}/fail?msg="+res.msg
                }
            },
            error: function (message) {
                alert("提交数据失败！");
                return;
            }
        });
    }

    function getJsonData() {

        var data = {
            "itemId":killId,
            "userId":10
        };
        // var data = {
        //     "itemId":killId
        // };
        return data;
    }
</script>

</html>
















