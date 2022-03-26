import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdSpringTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Test
    public void testTx(){
        Admin admin=new Admin(null,"tm","123123","汤姆","tom@qq.com",null);
        adminService.saveAdmin(admin);
    }
    @Test
    public void testSaveAdminMulti(){
        for (int i=0;i<352;i++){
            adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd"+i, "userName"+i, "email"+i+"@qq.com", null));
        }
    }
    @Test
    public void testSaveRoleMulti(){
        for (int i=0;i<352;i++){
            roleMapper.insert(new Role(null,"rolename"+i));
        }
    }

    @Test
    public void testDataSource() throws SQLException {
        Connection connection=dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testInsertAdmin(){
        Admin admin=new Admin(null,"jm","123123","汤姆","tom@qq.com",null);
        int count=adminMapper.insert(admin);
        System.out.println(count);
    }
    @Test
    public void test03(){
        //获取Logger对象，这里传入的Class就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdSpringTest.class);
        //等级 DEBUG < INFO < WARN < ERROR
        logger.debug("I am DEBUG!!!");

        logger.info("I am INFO!!!");

        logger.warn("I am WARN!!!");

        logger.error("I am ERROR!!!");

    }
    @Controller
    public class TestHandler {

        @Autowired
        AdminService adminService;

        @RequestMapping("/test/ssm.html")
        public String testSSM(Model model){
            //Admin admin = adminService.queryAdmin(1);
            List<Admin> admins = adminService.getAll();
            model.addAttribute("admins", admins);
            return "target";
        }
    }

}
