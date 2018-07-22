package com.wp.controller.sendtask;

import com.wp.util.JDBCUtils;
import com.wp.util.PageData;
import com.wp.util.SendMessage;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendPeriodTaskJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "-----------send period job! ");
        try {
            PageData pd = (PageData) context.getMergedJobDataMap().get("pageData");
            // 调用JDBCUtils 插入数据
            conn = JDBCUtils.getConnection();
            String sql = "insert into mission" +
                    "(mission_name,set_id,mission_type,mission_level,mission_source,period_start_time,period_end_time,time_dev,mission_condition,team,worker_name,worker_phone,set_name,mission_addition,send_time,cron,event,factory_id)"
                    +"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            st= conn.prepareStatement(sql);
            st.setString(1, pd.getString("mission_name"));
            st.setString(2, pd.getString("set_id"));
            st.setString(3, pd.getString("mission_type"));
            st.setString(4, pd.getString("mission_level"));
            st.setString(5, pd.getString("mission_source"));
            st.setString(6, pd.getString("period_start_time"));
            st.setString(7, pd.getString("period_end_time"));
            int timedev = 0;
            if (StringUtils.isNotEmpty(pd.getString("time_dev"))) {
                timedev = Integer.parseInt(pd.getString("time_dev"));
            }
            st.setInt(8, timedev );
            st.setString(9, pd.getString("mission_condition"));
            st.setString(10, pd.getString("team"));
            st.setString(11, pd.getString("worker_name"));
            st.setString(12, pd.getString("worker_phone"));
            st.setString(13, pd.getString("set_name"));
            st.setString(14, pd.getString("mission_addtion"));
            st.setString(15, pd.getString("send_time"));
            st.setString(16, pd.getString("cron"));
            st.setString(17, pd.getString("event"));
            st.setString(18,pd.getString("factory_id"));
            int i = st.executeUpdate();
            if(i==1) {
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"数据添加成功！");
            }else {
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"数据添加失败！");
            }
            String phonenumber = pd.getString("worker_phone");   //发送短信提醒
            String Content = "您有一条新任务，请注意查收。";
            SendMessage.sendMessage(phonenumber, Content);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.colseResource(conn, st, rs);
        }
    }
}
