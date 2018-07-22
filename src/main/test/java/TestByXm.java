import com.wp.controller.sendtask.SendPeriodTaskJob;
import com.wp.service.system.user.UserService;
import com.wp.util.PageData;
import com.wp.util.PathUtil;
import com.wp.util.PrintUtil;
import com.wp.util.quartz.QuartzJobExample;
import com.wp.util.quartz.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/ApplicationContext*.xml")
public class TestByXm {
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    @Resource(name="userService")
    private UserService userService;

    @Test
    public void testQurtz() throws ParseException {
        String string = "2018-07-22 18:40:06";
        String string1 = "2018-07-22 18:40:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(string);
        Date end = sdf.parse(string1);
        String job_name = "动态任务调度";
        PageData pd = new PageData();
        pd.put("mission_name","wcnmbd");
        QuartzManager.addStartAndEndJob("wxmtest",JOB_GROUP_NAME,"WXMTEST",TRIGGER_GROUP_NAME, SendPeriodTaskJob.class,start,end
        ,pd);
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPath() {
        String path = "C:/Users/mac/apache-tomcat-8.5.27/webapps/xunjian/uploadFiles/qrImg/挤出线监测4.png";
        int x = path.indexOf("/qrImg");
        System.out.println(path.substring(x,path.length()));
    }

    @Test
    public void testUser() {
        PageData pd = new PageData();
        pd.put("USER_ID","a3f98b8a4a52491fba04e708a1514057");
        try {
            pd = userService.findByUiId(pd);
            PrintUtil.print(pd.getString("USER_ID"));
            PrintUtil.print(pd.getString("NAME"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
