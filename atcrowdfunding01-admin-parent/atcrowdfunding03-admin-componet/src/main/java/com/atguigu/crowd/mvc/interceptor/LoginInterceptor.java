package com.atguigu.crowd.mvc.interceptor;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.AccessForbiddenException;
import com.atguigu.crowd.entity.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        Admin admin= (Admin) session.getAttribute(CrowdConstant.LOGIN_ADMIN_NAME);
        if(admin==null){
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        return true;
    }
}
