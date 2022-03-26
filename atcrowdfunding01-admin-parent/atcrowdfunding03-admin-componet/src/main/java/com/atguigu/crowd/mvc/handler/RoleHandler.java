package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.ResultEntity;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/get/page.json")
    public ResultEntity<PageInfo<Role>> getRolePage(@RequestParam(value = "keyword",defaultValue="") String keyword,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize
                                                    ){
        PageInfo<Role> pageInfo=roleService.getPageInfo(keyword,pageNum,pageSize);
        return ResultEntity.successWithData(pageInfo);
    }

    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("role/do/remove.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdArray){
        roleService.removeById(roleIdArray);
        return ResultEntity.successWithoutData();
    }


}
