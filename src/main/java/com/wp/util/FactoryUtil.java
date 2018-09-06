package com.wp.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class FactoryUtil {
    public static String getFactoryId() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.FACTORY_ID);
    }

    public static String getWorkshopId() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.WORKSHOP_ID);
    }
    public static String getTeamId() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.TEAM_ID);
    }

    public static String getLoginUserName() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.SESSION_USERNAME);
    }

    public static String getLoginName() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (String) session.getAttribute(Const.SESSION_NAME);
    }
}
