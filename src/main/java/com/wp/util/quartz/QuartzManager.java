package com.wp.util.quartz;

import javax.annotation.PostConstruct;

import com.wp.util.PageData;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzManager {

    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	//private static SchedulerFactoryBean schedulerFactory;

	//@Autowired
	//private SchedulerFactoryBean schedulerFactory1;
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

   /* @PostConstruct
    public void beforeInit() {
    	QuartzManager.schedulerFactory = schedulerFactory1;
    }*/

    /**
     * @Description: 添加一个定时任务
     *
     * @param jobName 任务名
     * @param jobGroupName  任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass  任务
     * @param cron   时间设置，参考quartz说明文档
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass, String cron, PageData pageData) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            //给添加的job传递数据
            jobDetail.getJobDataMap().put("pageData", pageData);
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addStartAndEndJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass,
                                         Date startTime,Date endTime, PageData pageData) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            //给添加的job传递数据
            jobDetail.getJobDataMap().put("pageData", pageData);
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startAt(startTime);
            triggerBuilder.endAt(endTime);
            // 创建Trigger对象
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/*    *//**
     * @Description: 修改一个任务的触发时间
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     *//*
    public static void modifyJob(String jobName,
            String jobGroupName, String triggerName, String triggerGroupName, String cron, QueryFormat queryFormat) {
        try {
            *//*Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 方式一 ：调用 rescheduleJob 开始
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);*//*
                //}

            *//** 方式二：先删除，然后在创建一个新的Job  *//*
        	Scheduler sched = schedulerFactory.getScheduler();
            JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            PrintUtils.print("jobDetail description  is " + jobDetail.getDescription());
            PrintUtils.print("jobDetail toString is " + jobDetail.toString());
            Class<? extends Job> jobClass = jobDetail.getJobClass();
            removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron, queryFormat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    *//**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     *//*
    public static void removeJob(String jobName, String jobGroupName,
            String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            Trigger.TriggerState state = sched.getTriggerState(triggerKey);

            PrintUtils.print("trigger state is " + state.name());

            sched.pauseTrigger(triggerKey);// 停止触发器
            boolean a = sched.unscheduleJob(triggerKey);// 移除触发器
            if (a) {
                System.out.println("移除触发器 success!");
            } else {
                System.out.println("移除触发器 error");
            }
            boolean b = sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            if (b) {
                System.out.println("delete job success!");
            } else {
                System.out.println("delete job error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    *//**
     * @Description:启动所有定时任务
     *//*
    public static void startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    *//**
     * @Description:关闭所有定时任务
     *//*
    public static void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } */

    public static void main(String[] args) {
        String job_name = "动态任务调度";
        System.out.println("【系统启动】开始(每1秒输出一次)...");
       // QuartzManager.addJob("wxmtest",JOB_GROUP_NAME,"WXMTEST",TRIGGER_GROUP_NAME,QuartzJobExample.class,"*/10 * * * * ?");
    }

}
