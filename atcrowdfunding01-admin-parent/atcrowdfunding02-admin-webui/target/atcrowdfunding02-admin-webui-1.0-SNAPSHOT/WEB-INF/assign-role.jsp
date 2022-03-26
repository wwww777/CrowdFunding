<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">
    $(function (){
        $("#toRightBtn").click(function(){
// select 是标签选择器
// :eq(0)表示选择页面上的第一个
// :eq(1)表示选择页面上的第二个
// “>”表示选择子元素
// :selected 表示选择“被选中的”option
// appendTo()能够将 jQuery 对象追加到指定的位置
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        });
        $("#toLeftBtn").click(function(){
            // select 是标签选择器
            // :eq(0)表示选择页面上的第一
            // :eq(1)表示选择页面上的第二个
// “>”表示选择子元素
// :selected 表示选择“被选中的”option
// appendTo()能够将 jQuery 对象追加到指定的位置
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        });
        $("#submitBtn").click(function () {
            $("select:eq(1)>option").prop("selected","selected");
        });
    });


</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="/admin/main/page.html">首页</a></li>
                <li><a href="/admin/page.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="/assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <input type="hidden" value="${param.adminId}" name="adminId">
                        <input type="hidden" value="${param.pageNum}" name="pageNum"/>
                        <input type="hidden" value="${param.keyword}" name="keyword"/>
                        <div class="form-group">
                            <%--@declare id="exampleinputpassword1"--%><label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList }" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList }" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" style="width:100px;margin-top: 20px;margin-left: 230px;" class="btn btn-sm btn-success btn-block">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
</body>
</html>