package com.wp.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
/**
 * Created by sun on 2018/1/5.
 */
public class HttpHandler {
    /**
     * 封装发送json消息
     * @param response
     * @param object
     */
    public static void send(HttpServletResponse response, Object object){
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            System.out.println("看看json值"+ JSONArray.fromObject(object));
            out.print(JSONArray.fromObject(object));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}