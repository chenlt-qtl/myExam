import com.exam.BootApplication;
import com.exam.dao.impl.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BootApplication.class)
class BootApplicationTests1 {

    //1. 注入你要测试的对象
    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        //2. 执行要测试的对象的方法
        bookDao.save();
    }

}
