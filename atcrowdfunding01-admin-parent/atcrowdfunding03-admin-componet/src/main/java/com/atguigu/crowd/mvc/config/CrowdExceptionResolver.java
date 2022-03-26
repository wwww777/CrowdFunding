package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.entity.CrowdUtil;
import com.atguigu.crowd.entity.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于注解的异常处理器类
 * @author Lenovo
 *
 */
// 表示当前类是一个处理异常的类
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView LoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="admin-add";
        return commonCode(exception, request, response,viewName);
    }
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView LoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return commonCode(exception, request, response,viewName);
    }



    //登录失败异常的处理
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-login";
        return commonCode(exception, request, response,viewName);
    }


    //处理空指针异常
    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView resolveNullPointerException(NullPointerException exception,
                                                    HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        return commonCode(exception,request,response,"system-error");
    }

    //处理数学异常,这里如果内部操作相同，跳转页面也相同，其实可以放在上面一个方法中，此处只是为了演示
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView resolveArithmeticException(ArithmeticException exception,
                                                   HttpServletRequest request,HttpServletResponse response) throws IOException {
        return commonCode(exception,request,response,"system-error");

    }

    //整理出的不同异常的可重用代码
    private ModelAndView commonCode(
            //触发的异常，此处借助多态
            Exception exception,
            //客户器端的请求
            HttpServletRequest request,
            //服务端的响应
            HttpServletResponse response,
            //指定普通页面请求时，去的错误页面
            String viewName
    ) throws IOException {
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        if (judgeRequestType){
            //if判断-是json请求
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            //创建Gson对象
            Gson gson = new Gson();
            //将ResultEntity对象转换成json格式
            String json = gson.toJson(failed);
            //通过原生servlet的response传回异常内容
            response.getWriter().write(json);
            //此时只需要返回null（因为是通过json格式返回数据）
            return null;
        } else {
            //if判断-是普通页面请求
            //创建ModelAndView对象
            ModelAndView modelAndView = new ModelAndView();
            //设置触发异常跳转的页面（会自动被视图解析器加上前后缀）
            modelAndView.setViewName(viewName);
            //将异常信息加入
            modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
            //返回设置完成的ModelAndView
            return modelAndView;
        }
    }

}