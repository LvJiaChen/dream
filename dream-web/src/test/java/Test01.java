import com.dream.web.application.MainApplication;
import com.dream.common.entity.DreamUser;
import com.dream.common.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  {MainApplication.class})
public class Test01 {

    public static void main(String[] args) {
        Account account=new Account();
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new AddMoneyThread(account,2));
        }
        executorService.shutdown();
        /** 若关闭后所有任务都已完成，则返回true */
        while(!executorService.isTerminated()) {}
        System.out.println("账户余额: " + account.getBalance());
    }


    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<DreamUser> dreamUserList = userMapper.selectList(null);
        //Assert.assertEquals(5, dreamUserList.size());
        dreamUserList.forEach(System.out::println);
    }

}
