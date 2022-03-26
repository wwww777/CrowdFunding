<%--
  Created by IntelliJ IDEA.
  User: Administrator_
  Date: 2020/8/15
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>

<%--引入pagination的css--%>
<link href="css/pagination.css" rel="stylesheet" />
<%--引入基于jquery的paginationjs--%>
<script type="text/javascript" src="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/crowd/my-role.js"></script>
<script type="text/javascript">
    $(function() {
// 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = ""
// 2.调用执行分页的函数，显示分页效果
        generatePage();
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        })
        // 4.点击新增按钮打开模态框
        $("#addrolebtn").click(function () {
            $("#addRoleModal").modal("show")

        })
        // 5.给新增模态框中的保存按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {
            // ①获取用户在文本框中输入的角色名称
            // #addModal 表示找到整个模态框
            // 空格表示在后代元素中继续查找
            // [name=roleName]表示匹配 name 属性等于 roleName 的元素
            var roleName = $.trim($("#addRoleModal [name=roleName]").val());
            // ②发送 Ajax 请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {"name": roleName},
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result=="SUCCESS") {
                        layer.msg("操作成功！");// 将页码定位到最后一页
                        window.pageNum = 99999999;// 重新加载分页数据
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                }, "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
// 关闭模态框
            $("#addRoleModal").modal("hide");
// 清理模态框
            $("#addRoleModal [name=roleName]").val("");
        })

        //6.(有问题，没响应 不知道哪儿错了)
        // 点击更新按钮打开模态框，并回显
        // 使用 jQuery 对象的 on()函数可以解决上面问题
// ①首先找到所有“动态生成”的元素所附着的“静态”元素
// ②on()函数的第一个参数是事件类型
// ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
// ③on()函数的第三个参数是事件的响应函数

        $("#rolePageBody").on("click",".pencilBtn",function (){
            $("#editRoleModal").modal("show");
            // 获取表格中当前行中的角色名称
            var roleName = $(this).parent().prev().text();
// 获取当前角色的 id
// 依据是：var pencilBtn = "<button id='"+roleId+"' ……这段代码中我们把 roleId 设置到id 属性了
// 为了让执行更新的按钮能够获取到 roleId 的值，把它放在全局变量上
            window.roleId = this.id;
// 使用 roleName 的值设置模态框中的文本框
            $("#editRoleModal [name=roleName]").val(roleName);
        })


        //7 给新增模态框中的更新按钮绑定单击响应函数
        $("#updateRoleBtn").click(function () {
            var roleName=$.trim($("#editRoleModal [name=roleName]").val())
            // ①获取用户在文本框中输入的角色名称
            // #addModal 表示找到整个模态框
            // 空格表示在后代元素中继续查找
            // [name=roleName]表示匹配 name 属性等于 roleName 的元素
            // ②发送 Ajax 请求
            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {"id":window.roleId,
                    "name": roleName},
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result=="SUCCESS") {
                        layer.msg("操作成功！");// 将页码定位到最后一页
                        window.pageNum = window.pageNum;// 重新加载分页数据
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                }, "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
// 关闭模态框
            $("#editRoleModal").modal("hide");
        })
        //8 给单条删除绑定单击事件
        $("#rolePageBody").on("click",".removeBtn",function () {

            // 通过X按钮删除时，只有一个角色，因此只需要建一个特殊的数组，存放单个对象即可
            var roleArray = [{
                "id": this.id,
                "name": $(this).parent().prev().text()
            }]

            // 调用显示模态框函数，传入roleArray
            showConfirmModal(roleArray);

        });

        // 为 “确认删除” 按钮绑定单击事件
        $("#confirmRoleBtn").click(function () {
            // 将全局变量中的id数组转换为json字符串格式
            var arrayStr = JSON.stringify(window.roleIdArray);

            $.ajax({
                url: "role/do/remove.json",
                type: "post",
                data: arrayStr,									// 将转换后的数据传给后端
                dataType: "json",
                contentType: "application/json;charset=UTF-8",	// 表明发送json格式数据
                success:function (response) {
                    if (response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if (response.result == "FAILED")
                        layer.msg("操作失败"+response.message)
                },
                error:function (response) {
                    layer.msg("statusCode="+response.status + " message="+response.statusText);
                }
            });

            // 关闭模态框
            $("#confirmRoleModal").modal("hide");
        });
        // 单击全选框时，使下面的内容全选/全不选
        $("#summaryBox").click(function () {
            // 获取当前状态（是否被选中）
            var currentStatus = this.checked;

            $(".itemBox").prop("checked",currentStatus);

        });
        // 由下面的选择框，改变全选框的勾选状态
        $("#rolePageBody").on("click",".itemBox",function () {

            // 获取当前已被选中的itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            // 获取当前的所有的itemBox数量
            var currentBoxCount = $(".itemBox").length;

            $("#summaryBox").prop("checked",checkedBoxCount == currentBoxCount);
        });
        // 给多选删除按钮绑定单击事件
        $("#batchRemoveBtn").click(function (){

            // 创建一个数组对象，用来存放后面获得的角色对象
            var roleArray = [];

            // 遍历被勾选的内容
            $(".itemBox:checked").each(function () {
                // 通过this引用当前遍历得到的多选框的id
                var roleId = this.id;

                // 通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();

                roleArray.push({
                    "id":roleId,
                    "name":roleName
                });
            });

            // 判断roleArray的长度是否为0
            if (roleArray.length == 0){
                layer.msg("请至少选择一个来删除");
                return ;
            }

            // 显示确认框
            showConfirmModal(roleArray);
        });
        // 给分配权限的按钮添加单击响应函数，打开分配模态框
        $("#rolePageTBody").on("click",".checkBtn",function () {

            // 将当前按钮的id放入全局变量
            window.roleId = this.id;
            // 打开模态框
            $("#assignModal").modal("show");
            // 生成权限信息
            generateAuthTree();
        });
        // 给分配权限的模态框中的提交按钮设置单击响应函数
        $("#assignBtn").click(function () {
            // 声明一个数组，用来存放被勾选的auth的id
            var authIdArray = [];

            // 拿到zTreeObj
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            // 通过getCheckedNodes方法拿到被选中的option信息
            var authArray = zTreeObj.getCheckedNodes();

            for (var i = 0; i < authArray.length; i++) {
                // 从被选中的auth中遍历得到每一个auth的id
                var authId = authArray[i].id;
                // 通过push方法将得到的id存入authIdArray
                authIdArray.push(authId);
            }
            var requestBody = {
                // 为了后端取值方便，两个数据都用数组格式存放，后端统一用List<Integer>获取
                "roleId":[window.roleId],
                "authIdList":authIdArray
            }
            requestBody = JSON.stringify(requestBody);

            $.ajax({
                url: "assign/do/save/role/auth/relationship.json",
                type: "post",
                data: requestBody,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response) {
                    if (response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                    }
                    if (response.result == "FAILED"){
                        layer.msg("操作失败！提示信息："+ response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + "  " + response.statusText);
                }
            });

            // 关闭模态框
            $("#assignModal").modal("hide");
        });






    });


</script>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" action="admin/page.html" method="post" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" name="keyword" type="text" placeholder="请输入查询条件"/>
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>

                    <button type="button" id="addrolebtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus">新增</i></button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--在最后引入模态框--%>
<%@include file="/WEB-INF/model-role-add.jsp"%>
<%@include file="/WEB-INF/model-role-edit.jsp"%>
<%@include file="/WEB-INF/model-role-confirm.jsp"%>
<%@include file="/WEB-INF/model-assign.jsp"%>
</body>
</html>
</body>
</html>


