import com.wp.util.quartz.QuartzJobExample;
import com.wp.util.quartz.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/ApplicationContext*.xml")
public class TestByXm {
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    @Test
    public void testQurtz() throws ParseException {
        String string = "2018-06-13 16:50:06";
        String string1 = "2018-06-13 16:55:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(string);
        Date end = sdf.parse(string1);
        String job_name = "动态任务调度";
        System.out.println("【系统启动】开始(每1秒输出一次)...");
        QuartzManager.addStartAndEndJob("wxmtest",JOB_GROUP_NAME,"WXMTEST",TRIGGER_GROUP_NAME,QuartzJobExample.class,start,end
        ,null);
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
