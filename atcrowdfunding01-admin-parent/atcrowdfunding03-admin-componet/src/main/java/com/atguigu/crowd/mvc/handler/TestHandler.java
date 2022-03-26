package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.entity.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(TestHandler.class);


    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") Integer[] array) {
        for (Integer num : array
        ) {
            System.out.println("num:" + num);
        }
        return "Success";
    }

    @ResponseBody
    @RequestMapping("/send/array/two.json")
    public ResultEntity<Integer[]> testReceiveArrayTwo(@RequestBody Integer[] array) {
        for (Integer num : array
        ) {
            System.out.println("num:" + num);
        }
        ResultEntity<Integer[]> resultEntity = ResultEntity.successWithData(array);
        return resultEntity;
    }

    @RequestMapping("/test/ssm.html")
    public String testSSM(Model model) {
        //Admin admin = adminService.queryAdmin(1);
        List<Admin> admins = adminService.getAll();
        model.addAttribute("adminList", admins);
        return "target";
    }
}

