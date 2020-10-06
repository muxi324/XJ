package com.wp.util;

import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HttpOutUtil {
    /** 日志 **/
    private static final Logger LOGGER = (Logger) LoggerFactory
            .getLogger(HttpOutUtil.class);

    /**
     * 向客户端输出数据。
     *
     * @param httpServletResponse
     *            响应对象
     * @param resJsonString
     */
    public static void outData(HttpServletResponse httpServletResponse,
                               String resJsonString) {
        PrintWriter writer = null;
        try {
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
            writer = httpServletResponse.getWriter();
            writer.print(resJsonString);
            writer.flush();
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
