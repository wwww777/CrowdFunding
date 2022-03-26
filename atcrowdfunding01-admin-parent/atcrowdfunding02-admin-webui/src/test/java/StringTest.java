import com.atguigu.crowd.entity.CrowdUtil;
import org.junit.Test;

public class StringTest {
    @Test
    public void testMD5(){
        String source="123123";
        String encode= CrowdUtil.md5(source);
        System.out.println(encode);
    }
}
