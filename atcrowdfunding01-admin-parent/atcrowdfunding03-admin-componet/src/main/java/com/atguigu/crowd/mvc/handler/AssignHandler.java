package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.ResultEntity;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    RoleService roleService;
    AdminService adminService;
    AuthService authService;

    @RequestMapping("assign/to/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId, ModelMap modelMap
    ) {
// 1.查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
// 2.查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
// 3.存入模型（本质上其实是：request.setAttribute("attrName",attrValue);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }
    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword, // 我们允许用户在页面上取消所有已分配角色再提交表单，所以可以不提roleIdList 请求参数
// 设置 required=false 表示这个请求参数不是必须的
@RequestParam(value="roleIdList", required=false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
    @ResponseBody
    @RequestMapping("/assign/get/tree.json")
    public ResultEntity<List<Auth>> getAuthTree(){
        List<Auth> authList = authService.queryAuthList();
        return ResultEntity.successWithData(authList);
    }
    // 获得被勾选的auth信息
    @ResponseBody
    @RequestMapping("/assign/get/checked/auth/id.json")
    public ResultEntity<List<Integer>> getAuthByRoleId(Integer roleId){
        List<Integer> authIdList = authService.getAuthByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }
    @ResponseBody
    @RequestMapping("/assign/do/save/role/auth/relationship.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            // 用一个map接收前端发来的数据
            @RequestBody Map<String,List<Integer>> map ) {
        // 保存更改后的Role与Auth关系
        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }



}
