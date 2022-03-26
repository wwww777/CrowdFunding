// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage(){
    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();
    // 2.填充表格
    fillTableBody(pageInfo);
}
function getPageInfoRemote(){
    var ajaxResult=$.ajax({
        "url":"role/get/page.json",
        "type":"post",
        "data":{
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async":false,
        "dataType":"json"
    })
    console.log(ajaxResult);
    var statusCode = ajaxResult.status;
    if(statusCode != 200) {
        layer.msg("失败！响应状态码="+statusCode+" 说明信息="+ajaxResult.statusText);
        return null;
    }
    // 如果响应状态码是 200，说明请求处理成功，获取 pageInfo
    var resultEntity = ajaxResult.responseJSON;
    // 从 resultEntity 中获取 result 属性
    var result = resultEntity.result;
    // 判断 result 是否成功
    if(result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    // 确认 result 为成功后获取 pageInfo
    var pageInfo = resultEntity.data;
// 返回 pageInfo
    return pageInfo;
}
// 填充表格
function fillTableBody(pageInfo) {
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    if(pageInfo==null || pageInfo==undefined||pageInfo.list == null||pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据!</td></tr>");
        return;
    }
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input type='checkbox' id='"+roleId+"' class='itemBox'/></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button type='button' id='"+roleId+"' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>"
        var pencilBtn =
            "<button type='button' id='"+roleId+"' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn =
            "<button type='button' id='"+roleId+"' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }
    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
// 获取总记录数
    var totalRecord = pageInfo.total;
// 声明相关属性
    var properties = { "num_edge_entries": 3, "num_display_entries": 5, "callback": paginationCallBack, "items_per_page": pageInfo.pageSize, "current_page": pageInfo.pageNum - 1, "prev_text": "上一页", "next_text": "下一页"
    }
// 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}
// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
// 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generatePage();
// 取消页码超链接的默认行为
    return false;
}
// 打开确认删除的模态框
function showConfirmModal(roleArray){
    // 显示模态框
    $("#confirmRoleModal").modal("show");

    // 清除旧的模态框中的数据
    $("#confirmList").empty();

    // 创建一个全局变量数组，用于存放要删除的roleId
    window.roleIdArray = [];

    // 填充数据
    for (var i = 0; i < roleArray.length; i++){

        var roleId = roleArray[i].id;

        // 将当前遍历到的roleId放入全局变量
        window.roleIdArray.push(roleId);

        var roleName = roleArray[i].name;

        $("#confirmList").append(roleName+"<br/>");
    }

}
// 生成权限信息的树形结构
function generateAuthTree(){

    var ajaxReturn = $.ajax({
        url: "assign/get/tree.json",
        type: "post",
        async: false,
        dataType: "json"
    });

    if (ajaxReturn.status != 200){
        layer.msg("请求出错！错误码："+ ajaxReturn.status + "错误信息：" + ajaxReturn.statusText);
        return ;
    }

    var resultEntity = ajaxReturn.responseJSON;

    if (resultEntity.result == "FAILED"){
        layer.msg("操作失败！"+resultEntity.message);
    }

    if (resultEntity.result == "SUCCESS"){
        var authList = resultEntity.data;
        // 将服务端查询到的list交给zTree自己组装
        var setting = {
            data: {
                // 开启简单JSON功能
                simpleData: {
                    enable: true,
                    // 通过pIdKey属性设置父节点的属性名，而不使用默认的pId
                    pIdKey: "categoryId"
                },
                key: {
                    // 设置在前端显示的节点名是查询到的title，而不是使用默认的name
                    name:"title"
                },
            },

            check: {
                enable:true
            }
        };

        // 生成树形结构信息
        $.fn.zTree.init($("#authTreeDemo"), setting, authList);

        // 设置节点默认是展开的
        // 1 得到zTreeObj
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
        // 2 设置默认展开
        zTreeObj.expandAll(true);

        // ----------回显部分----------

        // 回显（勾选）后端查出的匹配的权限
        ajaxReturn = $.ajax({
            url: "assign/get/checked/auth/id.json",
            type: "post",
            dataType: "json",
            async: false,
            data:{
                "roleId":window.roleId
            }
        });

        if (ajaxReturn.status != 200){
            layer.msg("请求出错！错误码："+ ajaxReturn.status + "错误信息：" + ajaxReturn.statusText);
            return ;
        }

        resultEntity = ajaxReturn.responseJSON;

        if (resultEntity.result == "FAILED"){
            layer.msg("操作失败！"+resultEntity.message);
        }

        if (resultEntity.result == "SUCCESS"){
            var authIdArray = resultEntity.data;

            // 遍历得到的autoId的数组
            // 根据authIdArray勾选对应的节点
            for (var i = 0; i < authIdArray.length; i++){
                var authId = authIdArray[i];

                // 通过id得到treeNode
                var treeNode = zTreeObj.getNodeByParam("id",authId);

                // checked设置为true，表示勾选节点
                var checked = true;

                // checkTypeFlag设置为false，表示不联动勾选，
                // 即父节点的子节点未完全勾选时不改变父节点的勾选状态
                // 否则会出现bug：前端只要选了一个子节点，传到后端后，下次再调用时，发现前端那个子节点的所有兄弟节点也被勾选了，
                // 因为在子节点勾选时，父节点也被勾选了，之后前端显示时，联动勾选，导致全部子节点被勾选
                var checkTypeFlag = false;

                // zTreeObj的checkNode方法 执行勾选操作
                zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
            }
        }

    }
}