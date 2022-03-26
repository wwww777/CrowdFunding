package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    AdminService adminService;
    //登录操作的handler
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("login-user") String username,@RequestParam("login-pwd") String password, HttpSession session){
        //通过service层方法得到Admin对象
        Admin admin=adminService.getAdminByUsername(username,password);
        //将Admin对象放入Session域
        session.setAttribute(CrowdConstant.LOGIN_ADMIN_NAME, admin);
        //重定向到登录完成后的主页面（重定向防止重复提交表单，增加不必要的数据库访问）
        return "redirect:/admin/main/page.html";
    }
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
// 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/login/page.html";
    }
    @RequestMapping("/admin/page.html")
    public String getAdminPage(@RequestParam(value = "keyword",defaultValue="") String keyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                               ModelMap modelMap){
        PageInfo<Admin> pageInfo=adminService.getPageInfo(keyword,pageNum,pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }
    @RequestMapping("/admin/page/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("adminId") Integer adminId,@PathVariable("pageNum") Integer pageNum,@PathVariable("keyword") String keyword){

        adminService.removeById(adminId);
        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
    @RequestMapping("/admin/to/edit/page/{adminId}/{pageNum}/{keyword}.html")
    public String updateAdmin( @PathVariable("adminId") Integer adminId,
                               @PathVariable("pageNum") Integer pageNum,
                               @PathVariable("keyword") String keyword,ModelMap modelMap){
        // 1.根据 id（主键）查询待更新的
        Admin admin=adminService.getAdminById(adminId);
        // 2.将 Admin 对象存入模型
        modelMap.addAttribute("admin",admin);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("keyword", keyword);
        return "admin-edit";
    }
    @RequestMapping("/admin/to/edit/page.html")
    public String saveAdmin(@RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword,Admin admin){
        adminService.updateAdmin(admin);
        if(keyword.length()==0){
            return "redirect:/admin/page.html?pageNum="+pageNum;
        }
        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/do/add.html")
    public String addAdmin(Admin admin){
           //为什么此处可以对应传入admin实体？
        adminService.saveAdmin(admin);
        return "redirect:/admin/page.html?pageNum="+Integer.MAX_VALUE;
    }


}
