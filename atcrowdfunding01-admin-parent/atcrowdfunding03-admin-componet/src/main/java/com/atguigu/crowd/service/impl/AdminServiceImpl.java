package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.entity.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void saveAdmin(Admin admin) {
        // 生成当前系统时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
// 针对登录密码进行加密
        String source = admin.getUserPswd();
        String encoded = CrowdUtil.md5(source);
        admin.setUserPswd(encoded);
        try {
            adminMapper.insert(admin);
        }catch(Exception e){
            e.printStackTrace();
            // 检测当前捕获的异常对象，如果是 DuplicateKeyException 类型说明是账号重复
            if(e instanceof DuplicateKeyException) {
                // 抛出自定义的 LoginAcctAlreadyInUseException 异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByUsername(String username, String password) {
        // 1、根据登陆账号查询Admin对象

        // 创建AdminExample对象
        AdminExample adminExample=new AdminExample();
        // 在Criteria对象中封装查询的条件
        AdminExample.Criteria criteria=adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        // 调用AdminMapper的方法来查询
        List<Admin> admins=adminMapper.selectByExample(adminExample);

        // 2、判断Admin对象是否为null或数据库数据不正常
        if(admins==null||admins.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size()>1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 3、如果Admin对象为null 则抛出异常
        Admin admin=admins.get(0);
        // 4、如果Admin对象不为null，则取出Admin对象的密码
        if(admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 5、对表单提交的密码进行md5加密
        String userPswDB=admin.getUserPswd();
        String userPswdForm= CrowdUtil.md5(password);
        // 6、对比两个密码
        if(!Objects.equals(userPswDB,userPswdForm)){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 因为两个密码都是对象，使用字符串的equals方法，如果存在空的密码则会触发空指针异常
        // 因此选用Objects.equals方法

        // 7、比对结果一致，返回admin对象
        return admin;
    }
    /**
     * @param keyword 关键字
     * @param pageNum 当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用PageHelper的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 调用Mapper接口的对应方法
        List<Admin> admins=adminMapper.selectAdminByKeyword(keyword);
        // 为了方便页面的使用，把Admin的List封装成PageInfo（放别得到页码等数据）
        PageInfo<Admin> pageInfo=new PageInfo<>(admins);
        // 返回得到的pageInfo对象
        return pageInfo;
    }

    @Override
    public void removeById(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException();
            }
        }

    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 先清除旧的对应inner_admin_role表中对应admin_id的数据
        adminMapper.clearOldRelationship(adminId);
        // 如果roleIdList非空，则将该list保存到数据库表中，且admin_id=adminId
        if (roleIdList != null && roleIdList.size() > 0){
            adminMapper.saveAdminRoleRelationship(adminId,roleIdList);
        }
        // roleIdList为空，则清空后不做操作
    }


}
