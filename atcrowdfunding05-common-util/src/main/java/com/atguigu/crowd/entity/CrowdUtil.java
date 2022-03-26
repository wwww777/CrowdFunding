package com.atguigu.crowd.entity;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginFailedException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 判断当前请求是否为 Ajax 请求
     *
     * @param request * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
// 1.获取请求消息头信息
        String acceptInformation = request.getHeader("Accept");
        String xRequestInformation = request.getHeader("X-Requested-With");
        // 2.检查并返回
        return
                (
                        acceptInformation != null
                                &&
                                acceptInformation.length() > 0
                                &&
                                acceptInformation.contains("application/json")
                )
                        ||
                        (
                                xRequestInformation != null
                                        &&
                                        xRequestInformation.length() > 0
                                        &&
                                        xRequestInformation.equals("XMLHttpRequest")
                        );
    }

    /**
     * 此方法是用于给字符串进行md5加密的工具方法
     * @return 进行md5加密后的结果
     * @param source 传入要加密的内容
     */
    public static String md5(String source){
        if(source==null||source.length()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try{
        //表示算法名
        String algorithm="md5";
        //得到MessageDigest对象，设置加密方式为md5
        MessageDigest messageDigest=MessageDigest.getInstance(algorithm);
        //将获得的明文字符串转换为字节数组
        byte[] input = source.getBytes();

        //对转换得到的字节数组进行md5加密
        byte[] output = messageDigest.digest(input);

        //设置BigInteger的signum
        //signum : -1表示负数、0表示零、1表示正数
        int signum = 1;

        //将字节数组转换成Big Integer
        BigInteger bigInteger = new BigInteger(signum,output);

        //设置将bigInteger的值按照16进制转换成字符串，最后全部转换成大写，得到最后的加密结果
        int radix = 16;
        String encoded = bigInteger.toString(radix).toUpperCase();

        //返回加密后的字符串
        return encoded;
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    //触发异常则返回null
        return null;
    }
}

